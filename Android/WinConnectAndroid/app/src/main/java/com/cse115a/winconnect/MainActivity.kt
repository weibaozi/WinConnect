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

class TCPClient(private val ipAddress: String, val portNumber: String) {

    val portInt = portNumber.toInt()
    private var socket: Socket? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null

    fun connect() {
        socket = Socket(ipAddress, portInt)
        outputStream = socket?.getOutputStream()
        inputStream = socket?.getInputStream()
    }

    fun send(message: String) {
        val data = message.toByteArray()
        outputStream?.write(data)
    }

    fun receive(): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.readLine()
    }

    fun disconnect() {
        socket?.close()
        outputStream?.close()
        inputStream?.close()
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
        //setContentView(R.layout.activity_main)

        val portnum = findViewById<TextView>(R.id.portnum)
        val connect_button = findViewById<Button>(R.id.connect_button)
        val ipaddr = findViewById<TextView>(R.id.ipaddr)
        val text_home = findViewById<TextView>(R.id.text_home)


        connect_button.setOnClickListener {
            val stringBuilder = StringBuilder()
            stringBuilder.append(ipaddr.text)
            stringBuilder.append(":")
            stringBuilder.append(portnum.text)

            val result = stringBuilder.toString()
            // text_home.text = result
            text_home.text = portnum.text.javaClass.simpleName

            val client = TCPClient(ipaddr.text.toString(), portnum.text.toString())
//            client.connect()
        }
    }
}