import SwiftUI
import Dispatch
import UIKit
import Foundation


let tcpClient = TCPClient(serverIP: "127.0.0.1") // Replace with your server IP address
var sendFlag = true

struct ContentView: View {
    
    @State var isConnected: Int = 0
    @State var portNumber: UInt16 = 51234
    
    var body: some View {
        TabView {
            AppsPage(isConnected: $isConnected, portNumber: $portNumber)
                .tabItem {
                    Image(systemName: "square.grid.2x2")
                    Text("Apps")
                }
            
            PCStatusPage(isConnected: $isConnected, portNumber: $portNumber)
                .tabItem {
                    Image(systemName: "desktopcomputer")
                    Text("PC Status")
                }
            
            MediaControlsPage()
                .tabItem {
                    Image(systemName: "music.note.list")
                    Text("Media Controls")
                }
        }
    }
}


struct AppsPage: View {
    @Binding var isConnected: Int
    @State private var message: String = ""
    @State private var showConnectionError = false
    @State var ipAddress: String = ""
    @Binding var portNumber: UInt16
    
    
    let columns: [GridItem] = Array(repeating: .init(.flexible()), count: 3)
    
    let numberFormatter: NumberFormatter = {
        let formatter = NumberFormatter()
        formatter.numberStyle = .none
        formatter.groupingSeparator = ""
        return formatter
    }()
    
    var body: some View {
        VStack(spacing: 20) {
            Text(connectionStatusText())
                .font(.title)
            
            LazyVGrid(columns: columns, spacing: 16) {
                ForEach(1...9, id: \.self) { index in
                    Button(action: {
                        let message = "startapp \(index)"
                        print(message)
                        DispatchQueue.global(qos: .userInitiated).async {
                            tcpClient.sendCommand(message)
                        }
                    }) {
                        Text("App \(index)")
                            .font(.title)
                            .foregroundColor(.white)
                            .padding()
                            .background(Color.blue)
                            .cornerRadius(10)
                    }
                }
            }
            .padding()
        
        
            VStack(spacing: 30) {
                HStack(spacing: 20){
                    HStack {
                        TextField("PC IP", text: $ipAddress)
                            .keyboardType(.default)
                            .autocapitalization(.none)
                            .disableAutocorrection(true)
                            .padding()
                            .overlay(
                                Rectangle()
                                    .frame(height: 2)
                                    .padding(.top, 35)
                                    .foregroundColor(ipAddress.isEmpty ? Color(.systemGray3) : Color(.systemBlue)),
                                alignment: .bottom
                            )
                        if !ipAddress.isEmpty {
                            Button(action: {
                                self.ipAddress = ""
                            }) {
                                Image(systemName: "multiply.circle.fill")
                                    .foregroundColor(.gray)
                                    .padding()
                            }
                        }
                    }
                    .frame(width: 200)
                    VStack(spacing: 60){
                        HStack {
                            TextField("PC Port", value: $portNumber, formatter: numberFormatter)
                                .keyboardType(.numberPad)
                                .padding()
                                .overlay(
                                    Rectangle()
                                        .frame(height: 2)
                                        .padding(.top, 35)
                                        .foregroundColor(Color(.systemBlue)),
                                    alignment: .bottom
                                )
                        }
                        .frame(width: 120)
                    }
                    
                }
                    
                    if isConnected == 0{
                        Button(action: {
                            connect()
                        }) {
                            Text("Connect")
                                .font(.title)
                                .foregroundColor(.white)
                                .padding()
                                .background(Color.green)
                                .cornerRadius(10)
                        }
                        
                    } else {
                        Button(action: {
                            disconnect()
                        }) {
                            Text("Disconnect")
                                .font(.title)
                                .foregroundColor(.white)
                                .padding()
                                .background(Color.red)
                                .cornerRadius(10)
                        }
                }
            }
        }
        .alert(isPresented: $showConnectionError) {
            Alert(
                title: Text("Connection Failed"),
                message: Text("Failed to connect to the server. Please check your network status."),
                dismissButton: .default(Text("OK"))
            )
        }
        .onAppear() {
            sendFlag = false
        }
    }

    
    func connectionStatusText() -> String {
        if isConnected == 2 {
            return "Connecting..."
        } else if isConnected == 1 {
            return "Connected"
        } else {
            return "Disconnected"
        }
    }
    
    func sendMessage(_ message: String) {
        tcpClient.sendCommand(message)
    }
    
    func connect() {
        isConnected = 2
        if tcpClient.run(IP: ipAddress, port: portNumber) {
            isConnected = 1
        } else {
            showConnectionError = true
            isConnected = 0
        }
    }
    
    func disconnect() {
        tcpClient.closeConnection()
        isConnected = 0
    }
}

struct PCStatusPage: View {
    @Binding var isConnected: Int
    @ObservedObject var receiver = TCPServer()
    @Binding var portNumber: UInt16

    var body: some View {
        VStack(spacing: 60) {
            Text("PC Status")
                .font(.title)
                .padding()


            VStack {
                Text("CPU Usage: \(String(format: "%.1f", receiver.cpuUsage))%")
                ProgressView(value: Double(receiver.cpuUsage) / 100.0)
                    .progressViewStyle(LinearProgressViewStyle(tint: .blue))
            }
            .padding()

            VStack {
                Text("RAM Usage: \(String(format: "%.1f", receiver.ramUsage))%")
                ProgressView(value: Double(receiver.ramUsage) / 100.0)
                    .progressViewStyle(LinearProgressViewStyle(tint: .green))
            }
            .padding()
        }
        .onAppear {
            if isConnected == 1 {
                DispatchQueue.global(qos: .background).async {
                    receiver.start(port: portNumber)
                }
            }
            else {
                DispatchQueue.main.async {
                    receiver.stop()
                    receiver.cpuUsage = 0
                    receiver.ramUsage = 0
                }
            }
        }
    }
}



//MediaControlsPage
//There are five buttons on the Media Control page
//previous, play/pause, next, volume down, volume down
struct MediaControlsPage: View {
    var body: some View {
        VStack(spacing: 5) {
            Text("Media Controls")
                .font(.largeTitle)
                .padding(.bottom, 40)
            
            HStack(spacing: 15) {
                Button(action: {
                    tcpClient.sendCommand("media prev")
                    print("previous tapped")
                }) {
                    Image(systemName: "backward.end.fill")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 45)
                        .foregroundColor(.black)
                        .padding(20)
                }
                .overlay(Circle().stroke(Color.black, lineWidth: 3))
                
                Button(action: {
                    tcpClient.sendCommand("media play")
                    print("Play/Pause tapped")
                }) {
                    Image(systemName: "playpause.fill")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 70)
                        .foregroundColor(.black)
                        .padding(30)
                }
                .overlay(Circle().stroke(Color.black, lineWidth: 3))
                
                Button(action: {
                    tcpClient.sendCommand("media next")
                    print("Next tapped")
                }) {
                    Image(systemName: "forward.end.fill")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 45)
                        .foregroundColor(.black)
                        .padding(20)
                }
                .overlay(Circle().stroke(Color.black, lineWidth: 3))
            }
            .padding(.bottom, 40)
            VStack(spacing: 80){
                HStack(spacing: 40) {
                    Button(action: {
                        tcpClient.sendCommand("volume down")
                        print("Volume down tapped")
                    }) {
                        Image(systemName: "speaker.wave.1.fill")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 50)
                            .foregroundColor(.black)
                    }
                    
                    Button(action: {
                        tcpClient.sendCommand("volume up")
                        print("Volume up tapped")
                    }) {
                        Image(systemName: "speaker.wave.3.fill")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 60)
                            .foregroundColor(.black)
                    }
                }
                
                HStack(spacing: 40) {
                    Button(action: {
                        tcpClient.sendCommand("arrow left")
                        print("Left tapped")
                    }) {
                        Image(systemName: "arrow.left")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 50)
                            .foregroundColor(.black)
                            .padding(10)
                    }
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.black, lineWidth: 3))
                    
                    VStack(spacing: 40) {
                        Button(action: {
                            tcpClient.sendCommand("arrow up")
                            print("Up tapped")
                        }) {
                            Image(systemName: "arrow.up")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 50)
                                .foregroundColor(.black)
                                .padding(10)
                        }
                        .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.black, lineWidth: 3))
                        
                        Button(action: {
                            tcpClient.sendCommand("arrow down")
                            print("Down tapped")
                        }) {
                            Image(systemName: "arrow.down")
                                .resizable()
                                .scaledToFit()
                                .frame(width: 50)
                                .foregroundColor(.black)
                                .padding(10)
                        }
                        .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.black, lineWidth: 3))
                    }
                    
                    Button(action: {
                        tcpClient.sendCommand("arrow right")
                        print("Right tapped")
                    }) {
                        Image(systemName: "arrow.right")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 50)
                            .foregroundColor(.black)
                            .padding(10)
                    }
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.black, lineWidth: 3))
                }
            }
        }
        .onAppear(){
            sendFlag = false
        }
    }
}


struct CircleButton<Content: View>: View {
    var action: () -> Void
    var content: () -> Content
    
    var body: some View {
        Button(action: action) {
            content()
        }
    }
}



struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
