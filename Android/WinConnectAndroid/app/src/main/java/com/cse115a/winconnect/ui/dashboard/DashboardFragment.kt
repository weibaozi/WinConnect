package com.cse115a.winconnect.ui.dashboard

import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cse115a.winconnect.SharedVars
import com.cse115a.winconnect.TCPConnect
import com.cse115a.winconnect.databinding.FragmentDashboardBinding
import android.opengl.GLES20

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Handler to fetch live stats
    private val handler = Handler()
    private val updateInterval = 1000L // Update every 1 second
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateHardwareStats()
            handler.postDelayed(this, updateInterval)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val button: Button = binding.infobutton
        val powerbutton: ImageButton = binding.powerButton
        val hardwareInfoTextView: TextView = binding.hardwareInfoTextView


        button.setOnClickListener {
            Toast.makeText(requireContext(), "All made by Austin xdd", Toast.LENGTH_SHORT).show()
        }

        powerbutton.setOnClickListener {
            textView.text = "Shutting down PC in 5 seconds"
            TCPConnect(SharedVars.ipAddressString, SharedVars.portNumber, "shutdown")
        }

        // Display hardware stats with usage percentage
        handler.postDelayed(updateRunnable, updateInterval)
        return root
    }

    private fun updateHardwareStats() {
        val hardwareInfoTextView: TextView = binding.hardwareInfoTextView
        val hardwareLiveStats: TextView = binding.hardwareLiveStats

        val hardwareInfo = getHardwareInfo()
        val usageInfo = getHardwareUsage()

        hardwareInfoTextView.text = Html.fromHtml(hardwareInfo, Html.FROM_HTML_MODE_LEGACY)
        hardwareLiveStats.text = Html.fromHtml(usageInfo, Html.FROM_HTML_MODE_LEGACY)

    }

    private fun getHardwareInfo(): String {
        val builder = StringBuilder()
        builder.append("<b>Device:</b> ${Build.DEVICE}<br>")
        builder.append("<b>Manufacturer:</b> ${Build.MANUFACTURER}<br>")
        builder.append("<b>Model:</b> ${Build.MODEL}<br>")
        builder.append("<b>Hardware:</b> ${Build.HARDWARE}<br>")
        builder.append("<b>CPU ABI:</b> ${Build.CPU_ABI}<br>")
        builder.append("<b>CPU ABI2:</b> ${Build.CPU_ABI2}<br>")
        builder.append("<b>Board:</b> ${Build.BOARD}<br>")
        builder.append("<b>Brand:</b> ${Build.BRAND}<br>")
        builder.append("<b>Bootloader:</b> ${Build.BOOTLOADER}<br>")
        builder.append("<b>Display:</b> ${Build.DISPLAY}<br>")
        // Add more hardware information as needed
        val gpuRenderer = GLES20.glGetString(GLES20.GL_RENDERER)
        builder.append("<b>GPU Renderer:</b> <big>$gpuRenderer</big><br>")

        return builder.toString()
    }

//    private fun getHardwareInfo(): String {
//        val builder = StringBuilder()
//        builder.append("Device: ${Build.DEVICE}\n")
//        builder.append("Manufacturer: ${Build.MANUFACTURER}\n")
//        builder.append("Model: ${Build.MODEL}\n")
//        builder.append("Hardware: ${Build.HARDWARE}\n")
//        builder.append("CPU ABI: ${Build.CPU_ABI}\n")
//        builder.append("CPU ABI2: ${Build.CPU_ABI2}\n")
//        builder.append("Board: ${Build.BOARD}\n")
//        builder.append("Brand: ${Build.BRAND}\n")
//        builder.append("Bootloader: ${Build.BOOTLOADER}\n")
//        builder.append("Display: ${Build.DISPLAY}\n")
//        // Add more hardware information as needed
//
//        return builder.toString()
//    }
    private var previousCpuTime: Long = 0
    private fun getHardwareUsage(): String {
        val currentCpuTime = Debug.threadCpuTimeNanos() / 1_000_000L
        val cpuUsage = (currentCpuTime - previousCpuTime).toFloat() / updateInterval
        previousCpuTime = currentCpuTime

        val formattedCpuUsage = String.format("%.3f", cpuUsage)
        val totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024
        val freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024
        val usedMemory = totalMemory - freeMemory

        val builder = StringBuilder()
        builder.append("<b>CPU Usage:</b> <big>$formattedCpuUsage%</big><br>")
        builder.append("<b>Total Memory:</b> <big>$totalMemory MB</big><br>")
        builder.append("<b>Used Memory:</b> <big>$usedMemory MB</big><br>")
        // Add more hardware usage information as needed

        return builder.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Stop updating hardware stats when the view is destroyed
        handler.removeCallbacks(updateRunnable)
    }
}