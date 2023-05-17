package com.cse115a.winconnect.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cse115a.winconnect.databinding.FragmentNotificationsBinding
import android.widget.Button
import com.cse115a.winconnect.SharedVars
import com.cse115a.winconnect.TCPConnect

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Media control button actions
        val playButton: Button = binding.playButton
        val scrollBack: Button = binding.scrollBack
        val scrollForward: Button = binding.scrollForward
        val upButton: Button = binding.upButton
        val downButton: Button = binding.downButton
        val leftButton: Button = binding.leftButton
        val rightButton: Button = binding.rightButton

        playButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media play")
        }

        scrollBack.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media prev")
        }

        scrollForward.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media next")
        }

        upButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media up")
        }

        downButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media down")
        }

        leftButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media left")
        }

        rightButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media right")
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}