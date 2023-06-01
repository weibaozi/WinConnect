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
import java.net.InetAddress
import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import java.nio.charset.Charset
import kotlinx.coroutines.withContext
import java.util.Scanner


// Global vars
class SharedVars {
    companion object {
        var ipAddressString: String = "10.0.0.217"
        var portNumber: Int = 51234
    }
}
lateinit var socket: Socket

fun TCPConnect(ipAddressString: String, portNumber: Int, message: String) {
     GlobalScope.launch(Dispatchers.IO) {
         try {
             val ipAddress: InetAddress = InetAddress.getByName(ipAddressString)
             //val socket = Socket(ipAddress, portNumber)
             socket = Socket(ipAddress, portNumber)

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

        val listenButton = findViewById<Button>(R.id.listenButton)
        val stopButton = findViewById<Button>(R.id.stopButton)
        val receivMsg = findViewById<TextView>(R.id.receivMsg)
        listenButton.setOnClickListener {
            //TCPConnect("10.0.0.217", 51234, "message")
            active = true
            CoroutineScope(IO).launch {
                Log.d("Success_test", client("10.0.0.217", 51234))
                //Log.d("success_test", receivedMessage)


            }
        }

        stopButton.setOnClickListener {
            Log.d("Success_test", "stop button")
            active = false
        }

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
    var active: Boolean = false
    var data: String = ""
    private suspend fun client(ipAddressString: String, portNumber: Int): String {
        socket = Socket(ipAddressString, portNumber)
        Log.d("Success_test", "in connection")
        val reader = Scanner(socket.getInputStream())
        Log.d("Success_test", "in reader")

        val stringBuilder = StringBuilder()

        try {

            while (active) {

                Log.d("Success_test", "in active")
                val input = reader.nextLine()
                Log.d("Success_test", "in input")
                Log.d("Success_test", input)

                if (stringBuilder.length < 300)
                    stringBuilder.append("\n$input")


                Log.d("Success_test", stringBuilder.toString())
            }
            Log.d("Success_test", "Out of while")
            socket.close()
        } finally {
            reader.close()
        }

        return stringBuilder.toString()
    }

}