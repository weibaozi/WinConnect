package com.cse115a.winconnect.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cse115a.winconnect.databinding.FragmentHomeBinding
import android.widget.Button
import android.widget.ImageButton
import com.cse115a.winconnect.R
import com.cse115a.winconnect.SharedVars
import com.cse115a.winconnect.TCPConnect
import com.cse115a.winconnect.startServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Home button actions
        val portnum: TextView = binding.portnum
        val ipaddr: TextView = binding.ipaddr
        val connectButton: Button = binding.connectButton
        val textHome: TextView = binding.textHome

        val listenButton: Button = binding.listenButton
        val stopButton: Button = binding.stopButton
        val receivMsg: TextView = binding.receivMsg

        val app1: ImageButton = binding.app1
        val app2: ImageButton = binding.app2
        val app3: ImageButton = binding.app3
        val app4: ImageButton = binding.app4
        val app5: ImageButton = binding.app5
        val app6: ImageButton = binding.app6
        val app7: ImageButton = binding.app7
        val app8: ImageButton = binding.app8
        val app9: ImageButton = binding.app9

        var customappName: TextView = binding.customApp
        var chromeURL: TextView = binding.chromeURL


        fun TCPReceive(ipAddressString: String, portNumber: Int) {
            Thread {
                try {


                    val ipAddress: InetAddress = InetAddress.getByName(ipAddressString)
                    receivMsg.text = "after ip"
                    receivMsg.text = "$ipAddressString, $portNumber"
                    val socket = Socket(ipAddress, portNumber)
                    receivMsg.text = "after socket"
                    // Additional code to handle the connection or perform any desired actions
                    val inputStream = socket.getInputStream()
                    receivMsg.text = "input stream: $inputStream"
                    val buffer = ByteArray(1024) // Choose an appropriate buffer size

                    var bytesRead: Int
                    val message = String(buffer, 0, 2048)
                    receivMsg.text = message
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        val message = String(buffer, 0, bytesRead)
                        // Process the received message as desired
                        receivMsg.text = message
                        println("Received message: $message")
                    }

                    inputStream.close()
                    socket.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle any exceptions that occur during the connection process
                }
            }.start()
        }

        fun startServer(ipAddress: String, portNumber: Int){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    receivMsg.text = "before socket"
                    val serverSocket = ServerSocket(portNumber)
                    println("Server started. Listening on $ipAddress:$portNumber")
                    receivMsg.text = "Server started. Listening on $ipAddress:$portNumber"
                    while (true) {
                        val clientSocket = serverSocket.accept()
                        Log.d("message", "Client connected: ${clientSocket.inetAddress.hostAddress}:${clientSocket.port}")
                        receivMsg.text = "Client connected: ${clientSocket.inetAddress.hostAddress}:${clientSocket.port}"

                        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                        var message: String? = reader.readLine()
                        while (message != null) {
                            println("Received message: $message")
                            // Process the message here as per your requirements
                            // Read the next message
                            message = reader.readLine()
                            receivMsg.text = message
                        }
                        // Close the client socket when done
                        clientSocket.close()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        listenButton.setOnClickListener {
            // If port number string isnt given, "" -> int will give error
            try {
                SharedVars.portNumber = portnum.text.toString().toInt()

            } catch (e: NumberFormatException) {
                // Marked port as 1 to indicate blank port num
                SharedVars.portNumber = 1
            }
            SharedVars.ipAddressString = ipaddr.text.toString()
            // check valid ip address and port number
            if (SharedVars.portNumber == 1 || SharedVars.ipAddressString == "") {
                receivMsg.text = "Receiv: Invalid IP or Port"
                return@setOnClickListener
            }

            receivMsg.text = "Receiv: before start server"
            TCPReceive(SharedVars.ipAddressString, SharedVars.portNumber)
            receivMsg.text = SharedVars.msgStr

 //            else {
//                // java.net.ConnectException show that failed to connect lel
//                receivMsg.text = "Receiv: before start server"
//                startServer(SharedVars.ipAddressString, SharedVars.portNumber)
//                receivMsg.text = SharedVars.msgStr
//            }

        }

        stopButton.setOnClickListener {
            receivMsg.text = "clicked on stop button"
            Log.d("Success_test", "stop button")
        }

        connectButton.setOnClickListener {
            // If port number string isnt given, "" -> int will give error
            try {
                SharedVars.portNumber = portnum.text.toString().toInt()

            } catch (e: NumberFormatException) {
                // Marked port as 1 to indicate blank port num
                SharedVars.portNumber = 1
            }

            SharedVars.ipAddressString = ipaddr.text.toString()
            // check valid ip address and port number
            if (SharedVars.portNumber == 1 || SharedVars.ipAddressString == "") {
               textHome.text = "Invalid IP or Port"
               return@setOnClickListener
            } else {
                // java.net.ConnectException show that failed to connect lel
                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "Connected")
                textHome.text = "Connected"
            }
            

        }

        app1.setOnClickListener {

            val chromeurl = chromeURL.text.toString()
            if (chromeurl != "") {
                textView.text = "Launched Google Chrome w/ URL"
                val x = chromeURL.text
                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startweb $x")
            } else {
                textView.text = "Launched Google Chrome"
                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Google Chrome")
            }

//            textView.text = "Launched Google Chrome"
//            try {
//                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Google Chrome")
//            } catch (e: java.net.ConnectException) {
//
//                textHome.text = "Unable to launch google chrome bruh"
//            }

        }

        app2.setOnClickListener {
            textView.text = "Launched Microsoft PowerPoint"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startweb https://docs.google.com/presentation/d/1BqXqLnPD2BgGzuHK9pziotPJBZbsAgi7sfMKMPOL1qc/edit?usp=sharing")
        }

        app3.setOnClickListener {
            textView.text = "Launched Visual Studio Code"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Visual Studio Code")
        }

        app4.setOnClickListener {
            textView.text = "Launched League of Legends"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Riot Client")
        }

        app5.setOnClickListener {
            textView.text = "Launched Minecraft"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Minecraft")
        }

        app6.setOnClickListener {
            textView.text = "Launched Soundcloud"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startweb https://soundcloud.com/austin-yen-56/likes")
        }

        app7.setOnClickListener {
            textView.text = "Launched FL Studio"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp FL Studio 21")
        }

        app8.setOnClickListener {
            textView.text = "Launched Calculator"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp calc")
        }

        app9.setOnClickListener {
            val appname = customappName.text.toString()
            if (appname != "") {
                textView.text = "Launched App " + customappName.text
                val x = customappName.text
                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp $x")
            } else {
                textView.text = "Invalid Custom App Name"
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        print("app ended")
    }
}