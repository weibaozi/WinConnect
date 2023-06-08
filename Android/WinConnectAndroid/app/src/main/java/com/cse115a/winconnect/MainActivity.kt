package com.cse115a.winconnect

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cse115a.winconnect.databinding.ActivityMainBinding

import android.widget.Button
import android.widget.TextView

import java.net.InetAddress
import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.ServerSocket
import java.util.Scanner
import android.content.Context
import android.net.wifi.WifiManager



// Global vars
class SharedVars {
    companion object {
        var ipAddressString: String = "10.0.0.217"
        var portNumber: Int = 51235
        var msgStr: String = ""
    }
}
//lateinit var socket: Socket
fun TCPConnect(ipAddressString: String, portNumber: Int, message: String) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val ipAddress: InetAddress = InetAddress.getByName(ipAddressString)
            val socket = Socket(ipAddress, portNumber)
            //socket = Socket(ipAddress, portNumber)

            // Additional code to handle the connection or perform any desired actions
            val outputStream = socket.getOutputStream()

            outputStream.write(message.toByteArray())
            outputStream.flush()
            outputStream.close()

            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle any exceptions that occur during the connection process
        }
    }
}

//fun TCPConnectTest(ipAddressString: String, portNumber: Int, message: String) {
//     GlobalScope.launch(Dispatchers.IO) {
//         Log.d("Server response", "here")
//         try {
//             val ipAddress: InetAddress = InetAddress.getByName(ipAddressString)
//             val socket = Socket(ipAddress, portNumber)
//
//             // socket = Socket(ipAddress, portNumber)
//
//             // Additional code to handle the connection or perform any desired actions
//             val outputStream = socket.getOutputStream()
//
//             Log.d("Server response", "after reader")
//
//
//             outputStream.write(message.toByteArray())
//
//             val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//             val response = reader.readLine()
//             Log.d("Server response", response)
//             outputStream.flush()
//             outputStream.close()
//             reader.close()
//
//
//
//             socket.close()
//         } catch (e: Exception) {
//             e.printStackTrace()
//             // Handle any exceptions that occur during the connection process
//         }
//     }
//}

fun startServer(ipAddress: String, portNumber: Int){

    GlobalScope.launch(Dispatchers.IO) {
//        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
//
//        while (networkInterfaces.hasMoreElements()) {
//            val networkInterface = networkInterfaces.nextElement()
//            val inetAddresses = networkInterface.inetAddresses
//
//            while (inetAddresses.hasMoreElements()) {
//                val inetAddress = inetAddresses.nextElement()
//
//                if (!inetAddress.isLoopbackAddress && inetAddress is InetAddress) {
//                    inetAddress.hostAddress?.let { Log.d("test", it) }
//
//                }
//            }
//        }

        try {
            SharedVars.msgStr = "before socket"

            val serverSocket = ServerSocket(portNumber)
            println("Server started. Listening on $ipAddress:$portNumber")
            SharedVars.msgStr = "Server started. Listening on $ipAddress:$portNumber"


            while (true) {

                val clientSocket = serverSocket.accept()
                Log.d("message", "Client connected: ${clientSocket.inetAddress.hostAddress}:${clientSocket.port}")
                SharedVars.msgStr = "Client connected: ${clientSocket.inetAddress.hostAddress}:${clientSocket.port}"

                val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                var message: String? = reader.readLine()
                while (message != null) {
                    println("Received message: $message")

                    // Process the message here as per your requirements
                    // Read the next message
                    message = reader.readLine()
                    SharedVars.msgStr = message

                }

                // Close the client socket when done
                clientSocket.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val listenButton = findViewById<Button>(R.id.listenButton)
//        val stopButton = findViewById<Button>(R.id.stopButton)
//        val receivMsg = findViewById<TextView>(R.id.receivMsg)
//        val portnum = findViewById<TextView>(R.id.portnum)
//        val ipAddressString = findViewById<TextView>(R.id.ipaddr)
//        val texthome = findViewById<TextView>(R.id.textHome)
//
//        listenButton.setOnClickListener {
//            SharedVars.portNumber = portnum.text.toString().toInt()
//            SharedVars.ipAddressString = ipAddressString.text.toString()
//
//            Log.d("test", "${SharedVars.portNumber}")
//            startServer(SharedVars.ipAddressString, SharedVars.portNumber)
//            //startServer("10.0.0.217", 51234)
//
//            receivMsg.text = SharedVars.msgStr
//            Log.d("success_test", "clicked button")
//
//
//        }



//        val portnum = findViewById<TextView>(R.id.portnum)
//        val connect_button = findViewById<Button>(R.id.connectButton)
//        val ipaddr = findViewById<TextView>(R.id.ipaddr)
//        val text_home = findViewById<TextView>(R.id.textHome)
//
//        val app1 = findViewById<Button>(R.id.app1)
//        connect_button.setOnClickListener {
//
//            text_home.text = "Connected"
//            //text_home.text = portnum.text.toString().toInt().javaClass.simpleName
//
//            //ipAddressString = ipaddr.text.toString()
//            //portNumber = portnum.text.toString().toInt()
//            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "lmaoxdd")
//
//        }
//
//        app1.setOnClickListener {
//            text_home.text = "Launched App 1"
//            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "Launched App 1")
//
//        }

    }
}