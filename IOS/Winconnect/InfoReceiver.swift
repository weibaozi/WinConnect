import Network
import Combine
import Foundation

class TCPServer: ObservableObject {
    @Published var cpuUsage: Float = 0.0
    @Published var ramUsage: Float = 0.0
    
    private let queue = DispatchQueue(label: "TCPServer")
    private var listener: NWListener?
    
    func start(port: UInt16) {
        let parameters = NWParameters.tcp
        guard let nwPort = NWEndpoint.Port(rawValue: port) else {
            print("Invalid port number.")
            return
        }

        do {
            print("connecting")
            listener = try NWListener(using: parameters, on: nwPort)
        } catch {
            print("Failed to initialize listener: \(error)")
            return
        }
        
        listener?.stateUpdateHandler = { state in
            switch state {
            case .ready:
                print("Listener ready")
            case .failed(let error):
                print("Listener failed with error: \(error)")
            default:
                break
            }
        }

        listener?.newConnectionHandler = { newConnection in
            print("New connection established")
            newConnection.stateUpdateHandler = { state in
                switch state {
                case .ready:
                    print("Connection ready")
                    self.receive(on: newConnection)
                case .failed(let error):
                    print("Connection failed with error: \(error)")
                default:
                    break
                }
            }
            newConnection.start(queue: self.queue)
        }
        
        listener?.start(queue: queue)
    }

    func stop() {
        listener?.cancel()
        listener = nil
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
                print("Updated CPU usage: \(cpu), RAM usage: \(ram)")
            }
        } else {
            print("Failed to parse message: \(message)")
        }
    }

    private func receive(on connection: NWConnection) {
        connection.receive(minimumIncompleteLength: 1, maximumLength: 65536) { [weak self] (data, context, isComplete, error) in
            if let data = data, let message = String(data: data, encoding: .utf8) {
                self?.updateUsages(from: message)
            }

            if let error = error {
                print("Failed to receive data: \(error)")
                // Close connection and cleanup if required
                connection.cancel()
            } else if !isComplete {
                // If connection has not been closed by the client, continue to receive more data
                self?.receive(on: connection)
            }
        }
    }
}
