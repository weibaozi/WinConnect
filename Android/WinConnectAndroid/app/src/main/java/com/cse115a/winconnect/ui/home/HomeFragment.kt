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
import android.widget.ImageButton
import com.cse115a.winconnect.R
import com.cse115a.winconnect.SharedVars
import com.cse115a.winconnect.TCPConnect

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

        val app1: ImageButton = binding.app1
        val app2: ImageButton = binding.app2
        val app3: ImageButton = binding.app3
        val app4: ImageButton = binding.app4
        val app5: ImageButton = binding.app5
        val app6: ImageButton = binding.app6
        val app7: ImageButton = binding.app7
        val app8: ImageButton = binding.app8
        val app9: ImageButton = binding.app9

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
            textView.text = "Launched Google Chrome"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Google Chrome")
        }

        app2.setOnClickListener {
            textView.text = "Launched Microsoft PowerPoint"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Microsoft PowerPoint 2010")
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
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp soundcloud")
        }

        app7.setOnClickListener {
            textView.text = "Launched FL Studio"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp FL Studio 21")
        }

        app8.setOnClickListener {
            textView.text = "Launched Adobe Lightroom"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp Adobe Lightroom Classic")
        }

        app9.setOnClickListener {
            textView.text = "Launched Calculator"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "startapp calc")
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        print("app ended")
    }
}