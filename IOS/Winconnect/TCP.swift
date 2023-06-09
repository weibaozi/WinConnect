import Foundation
import Network
import Combine

let PORT: UInt32 = 51234

class TCPClient {
    let serverIP: String
    var connection: NWConnection?
    var inputStream: InputStream?
    var outputStream: OutputStream?
    @Published var cpuUsage: Float = 0.0
    @Published var ramUsage: Float = 0.0
    var shouldReceive: Bool = true

    init(serverIP: String) {
        self.serverIP = serverIP
    }
    
    func setReciveFlag(){
        shouldReceive = true
    }
    
    func run(IP: String, port: UInt16) -> Bool {
        let connection = NWConnection(host: NWEndpoint.Host(IP), port: NWEndpoint.Port(rawValue: UInt16(port))!, using: .tcp)

        connection.stateUpdateHandler = { newState in
            switch newState {
            case .ready:
                print("Connected to the receiver")
                self.receive(on: connection)
            case .failed(let error):
                print("Connection failed with error: \(error)")
                self.connection = nil
            default:
                break
            }
        }

        connection.start(queue: .global())

        self.connection = connection
        return true
    }

    func sendCommand(_ command: String) {
        let data = command.data(using: .utf8)!
        connection?.send(content: data, completion: .contentProcessed { error in
            if let error = error {
                print("Send failed with error: \(error)")
            } else {
                print("Data sent")
            }
        })
    }
    
    func startReceiving() {
            guard let inputStream = self.inputStream else {
                print("Input stream is not available")
                return
            }

            while inputStream.hasBytesAvailable {
                var buffer = [UInt8](repeating: 0, count: 4096)
                let bytesRead = inputStream.read(&buffer, maxLength: buffer.count)
                if bytesRead < 0 {
                    if let error = inputStream.streamError {
                        print("Read failed with error: \(error)")
                    }
                    // Cleanup and close if required
                    self.closeConnection()
                    return
                } else if bytesRead == 0 {
                    print("Connection closed by the server")
                    // Cleanup and close if required
                    self.closeConnection()
                    return
                } else {
                    // Successfully read from stream
                    if let message = String(bytes: buffer, encoding: .utf8) {
                        print("test")
                        print("Received message: \(message)")
                        self.updateUsages(from: message)
                    }
                }
            }
    }
    
    private func updateUsages(from message: String) {
        // Parse and update the values
        let dataParts = message.split(separator: ",")
        if dataParts.count >= 2,
           let cpu = Float(dataParts[0]),
           let ram = Float(dataParts[1]) {
            DispatchQueue.main.async {
                self.cpuUsage = cpu
                self.ramUsage = ram
                print("test")
                print("Updated CPU usage: \(self.cpuUsage), RAM usage: \(self.ramUsage)")
            }
        } else {
            print("Failed to parse message: \(message)")
        }
    }

    private func receive(on connection: NWConnection?) {
        connection?.receive(minimumIncompleteLength: 1, maximumLength: 65536) { [weak self] (data, context, isComplete, error) in
            guard let strongSelf = self else { return }
            if let data = data, let message = String(data: data, encoding: .utf8) {
                strongSelf.updateUsages(from: message)
            }

            if let error = error {
                print("Failed to receive data: \(error)")
                connection?.cancel()
            }
            // else if !isComplete && strongSelf.shouldReceive { // Removed this line
            //     strongSelf.receive(on: connection) // And this line
            // }
        }
    }

    
    func stopReceiving() {
        self.shouldReceive = false
    }

    func closeConnection() {
        connection?.cancel()
        connection = nil
        print("Connection closed")
    }
}

func sendToServer(message: String) {
    let client = TCPClient(serverIP: "10.0.0.89") // Replace with your server IP address
    client.sendCommand(message)
}
