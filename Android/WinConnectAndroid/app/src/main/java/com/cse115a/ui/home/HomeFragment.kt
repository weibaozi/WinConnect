package com.cse115a.winconnect.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cse115a.winconnect.databinding.FragmentHomeBinding
import android.widget.Button
import com.cse115a.winconnect.R
import com.cse115a.winconnect.SharedVars
import com.cse115a.winconnect.TCPConnect
import com.cse115a.winconnect.TCPReceive
import kotlin.concurrent.thread
import java.util.concurrent.Executor

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

        val app1: Button = binding.app1
        val app2: Button = binding.app2
        val app3: Button = binding.app3
        val app4: Button = binding.app4
        val app5: Button = binding.app5
        val app6: Button = binding.app6
        val app7: Button = binding.app7
        val app8: Button = binding.app8
        val app9: Button = binding.app9

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
                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "Connected")
//                if (SharedVars.flag == 0) {
//                    SharedVars.executor.execute {
                TCPReceive(SharedVars.ipAddressString, SharedVars.portNumber)
//                    }
//                    SharedVars.flag = 1
//                }

            }
            

        }

        app1.setOnClickListener {
            textView.text = "Launched App 1"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 1")
        }

        app2.setOnClickListener {
            textView.text = SharedVars.message
            // TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 2")
        }

        app3.setOnClickListener {
            textView.text = "Launched App 3"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 3")
        }

        app4.setOnClickListener {
            textView.text = "Launched App 4"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 4")
        }

        app5.setOnClickListener {
            textView.text = "Launched App 5"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 5")
        }

        app6.setOnClickListener {
            textView.text = "Launched App 6"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 6")
        }

        app7.setOnClickListener {
            textView.text = "Launched App 7"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 7")
        }

        app8.setOnClickListener {
            textView.text = "Launched App 8"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 8")
        }

        app9.setOnClickListener {
            textView.text = "Launched App 9"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp 9")
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        print("app ended")
    }
}