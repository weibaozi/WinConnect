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
import android.widget.Toast
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

        val mediaStatus: TextView = binding.mediactrlStatus
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            mediaStatus.text = it
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

        val escapeButton: ImageButton = binding.escapeButton
        val fullscreenButton: ImageButton = binding.fullscreenButton
        val alttabButton: ImageButton = binding.alttabButton
        val ctrlwButton: ImageButton = binding.ctrlwButton
        val spaceButton: ImageButton = binding.spaceButton
        val enterButton: ImageButton = binding.enterButton
        val leftclickButton: ImageButton = binding.leftclickButton
        val rightclickButton: ImageButton = binding.rightclickButton
        val customButton: ImageButton = binding.customkbButton
        val customkbcmd: TextView = binding.customkbcmd

        var isPlaying = false
        playButton.setOnClickListener {
            mediaStatus.text = "Play/Pause Button Pressed"
            isPlaying = !isPlaying
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media play")
            if (isPlaying) {
                playButton.setBackgroundResource(R.drawable.pause_button) // Set pause icon
            } else {
                playButton.setBackgroundResource(R.drawable.play_button) // Set play icon
            }

        }

        scrollBack.setOnClickListener {
            mediaStatus.text = "Previous Song Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media prev")
            Log.d("Buttons", "scrollback")
        }

        scrollForward.setOnClickListener {
            mediaStatus.text = "Next Song Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "media next")
            Log.d("Buttons", "scrollforward")
        }

        upButton.setOnClickListener {
            mediaStatus.text = "Up Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "arrow up")
            Log.d("Buttons", "up")
        }


        downButton.setOnClickListener {
            mediaStatus.text = "Down Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "arrow down")
            Log.d("Buttons", "down")
        }

        leftButton.setOnClickListener {
            mediaStatus.text = "Left Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "arrow left")
            Log.d("Buttons", "left")
        }

        rightButton.setOnClickListener {
            mediaStatus.text = "Right Button Pressed"
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

        escapeButton.setOnClickListener {
            mediaStatus.text = "Escape Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key esc")
            Log.d("Buttons", "esc")
        }

        fullscreenButton.setOnClickListener {
            mediaStatus.text = "Fullscreen Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key ctrl+f5")
            Log.d("Buttons", "f")
        }

        fullscreenButton.setOnLongClickListener {
            // Handle long press event here
            mediaStatus.text = "Fulscreen Button Long Pressed (F11)"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key f11")
            Log.d("Buttons", "fullscreen long press")

            // Return true to consume the long press event
            true
        }

        alttabButton.setOnClickListener {
            mediaStatus.text = "Alt+Tab Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key alt+tab")
            Log.d("Buttons", "alttab")
        }

        ctrlwButton.setOnClickListener {
            mediaStatus.text = "Ctrl+W Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key ctrl+w")
            Log.d("Buttons", "ctrlw")
        }
        ctrlwButton.setOnLongClickListener {
            // Handle long press event here
            mediaStatus.text = "Alt+F4 Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key alt+f4")
            Log.d("Buttons", "Alt+F4 Button Pressed")

            // Return true to consume the long press event
            true
        }

        spaceButton.setOnClickListener {
            mediaStatus.text = "Space Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key space")
            Log.d("Buttons", "space")
        }

        enterButton.setOnClickListener {
            mediaStatus.text = "Enter Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key enter")
            Log.d("Buttons", "enter")
        }

        leftclickButton.setOnClickListener {
            mediaStatus.text = "Left Click Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "mouse leftclick")
            Log.d("Buttons", "leftclick")
        }

        rightclickButton.setOnClickListener {
            mediaStatus.text = "Right Click Button Pressed"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "mouse rightclick")
            Log.d("Buttons", "rightclick")
        }

        customButton.setOnClickListener {
            mediaStatus.text = "Custom Keyboard Command Button Pressed"
            if (customkbcmd.text.toString() == "") {
                // Toast.makeText(context, "Please enter a command", Toast.LENGTH_SHORT).show()
                mediaStatus.text = "Please enter a command"
                return@setOnClickListener
            } else {
                //Toast.makeText(context, "Sending command: ${customkbcmd.text}", Toast.LENGTH_SHORT).show()
                mediaStatus.text = "Sending kb command: ${customkbcmd.text}"
                TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "key ${customkbcmd.text}")
            }
            
            Log.d("Buttons", "customkb")
        }



        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}