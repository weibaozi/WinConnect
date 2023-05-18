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

import java.net.*
import java.io.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.Socket
import java.net.ServerSocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


fun TCPConnect(ipAddressString: String, portNumber: Int, message: String) {
     GlobalScope.launch(Dispatchers.IO) {
         try {
             val ipAddress: InetAddress = InetAddress.getByName(ipAddressString)
             val socket = Socket(ipAddress, portNumber)

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

fun TCPReceive(ipAddressString: String, portNumber: Int) {
    GlobalScope.launch(Dispatchers.IO) {
        // Create ab server socket to bind
        val serverSocket = ServerSocket(1235)
        SharedVars.message = "yay"

        while (true) {
            SharedVars.message = "yay1"
            val clientSocket = serverSocket.accept()
            SharedVars.message = "yay2"

            clientSocket.use { client ->
                while (true) {
                    val data = client.getInputStream().bufferedReader().readLine()
                    if (data == null) {
                        break
                    }
                    val text = data.toString()
                    if (text.trim() == "exit") {
                        client.close()
                        break
                    }
                    val ack = "ACK\n".toByteArray()
                    client.getOutputStream().write(ack)
                    SharedVars.message = text
                }
            }

            // Create a BufferedReader to read messages from the socket
//            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//            val writer = PrintWriter(socket.getOutputStream(), true)
//
//            // Read a line from the socket
//            SharedVars.message = reader.readLine()
//
//            // Write ack
//            writer.println("ACK")
//
//            // Close client socket
//            reader.close()
//            writer.close()
        }

    }
}

// Global vars
class SharedVars {
    companion object {
        var ipAddressString: String = "10.0.0.154"
        var portNumber: Int = 1234
        var message: String = "blah"
        val executor: ExecutorService = Executors.newSingleThreadExecutor()
        var flag: Int = 0
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