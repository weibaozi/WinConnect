package com.cse115a.winconnect.ui.notifications

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cse115a.winconnect.databinding.FragmentNotificationsBinding
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.ToggleButton
import com.cse115a.winconnect.R
import com.cse115a.winconnect.SharedVars
import com.cse115a.winconnect.TCPConnect

class NotificationsFragment : Fragment(){

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
        val playButton: ImageButton = binding.playButton
        val scrollBack: ImageButton = binding.scrollBack
        val scrollForward: ImageButton = binding.scrollForward
        val upButton: ImageButton = binding.upButton
        val downButton: ImageButton = binding.downButton
        val leftButton: ImageButton = binding.leftButton
        val rightButton: ImageButton = binding.rightButton
        val volumeSeekBar: SeekBar = binding.volumeSeekBar
        val volumeSlider: TextView = binding.volumeStats

        var isPlaying = false
        playButton.setOnClickListener {
            isPlaying = !isPlaying
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media play")
            if (isPlaying) {
                playButton.setBackgroundResource(R.drawable.pause_button) // Set pause icon
            } else {
                playButton.setBackgroundResource(R.drawable.play_button) // Set play icon
            }

        }

        scrollBack.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media prev")
            Log.d("Buttons", "scrollback")
        }

        scrollForward.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media next")
            Log.d("Buttons", "scrollforward")
        }

        upButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "arrow up")
            Log.d("Buttons", "up")
        }


        downButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "arrow down")
            Log.d("Buttons", "down")
        }

        leftButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "arrow left")
            Log.d("Buttons", "left")
        }

        rightButton.setOnClickListener {
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "arrow right")
            Log.d("Buttons", "right")
        }

        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d("volume", "setvol $progress")
                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "setvol $progress")
                volumeSlider.text = "Volume: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}