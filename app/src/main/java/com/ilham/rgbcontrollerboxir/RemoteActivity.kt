package com.ilham.rgbcontrollerboxir

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.ilham.rgbcontrollerboxir.core.Emitter
import com.ilham.rgbcontrollerboxir.core.EmitterImpl
import com.ilham.rgbcontrollerboxir.core.LEDColors
import com.ilham.rgbcontrollerboxir.core.LEDColors.BRIGHT_DOWN
import com.ilham.rgbcontrollerboxir.core.LEDColors.BRIGHT_UP
import com.ilham.rgbcontrollerboxir.core.LEDColors.FADE
import com.ilham.rgbcontrollerboxir.core.LEDColors.FLASH
import com.ilham.rgbcontrollerboxir.core.LEDColors.OFF
import com.ilham.rgbcontrollerboxir.core.LEDColors.ON
import com.ilham.rgbcontrollerboxir.core.LEDColors.SMOOTH
import com.ilham.rgbcontrollerboxir.core.LEDColors.STROBE
import com.ilham.rgbcontrollerboxir.core.LEDColors.WHITE
import com.ilham.rgbcontrollerboxir.databinding.ActivityRemoteBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RemoteActivity : AppCompatActivity() {

    private val binding: ActivityRemoteBinding by lazy {
        ActivityRemoteBinding.inflate(layoutInflater)
    }

    private val emitter: Emitter by lazy {
        EmitterImpl(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.TRANSMIT_IR), 1)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        GlobalScope.launch {
            for (a in 0 until 14) {
                emitter.emit(LEDColors.getColors()[a])
                delay(150)
            }
        }

        setupUI()

    }

    private fun setupUI() {
        setupColoredButton()

        binding.btnWhite.setOnClickListener {
            emitter.emit(WHITE)
        }

        binding.powerOff.setOnClickListener {
            emitter.emit(OFF)
        }

        binding.powerOn.setOnClickListener {
            emitter.emit(ON)
        }

        binding.brightnessUp.setOnClickListener {
            emitter.emit(BRIGHT_UP)
        }

        binding.brightnessDown.setOnClickListener {
            emitter.emit(BRIGHT_DOWN)
        }

        binding.btnStrobe.setOnClickListener {
            emitter.emit(STROBE)
        }

        binding.btnFade.setOnClickListener {
            emitter.emit(FADE)
        }

        binding.btnFlash.setOnClickListener {
            emitter.emit(FLASH)
        }

        binding.btnSmooth.setOnClickListener {
            emitter.emit(SMOOTH)
        }
    }

    private fun setupColoredButton() {
        val colors = resources.getIntArray(R.array.color_list)
        val scale = resources.displayMetrics.density
        val margin = (5 * scale).toInt()
        val params = LinearLayout.LayoutParams(
            LayoutParams((70 * scale + 0.5f).toInt(), (75 * scale + 0.5f).toInt())
        )
        params.apply {
            marginStart = margin
            marginEnd = margin
        }

        LEDColors.getPairedColor().forEachIndexed { index, data ->
            binding.colorGrid.addView(
                MaterialButton(this).apply {
                    setBackgroundColor(colors[index])
                    cornerRadius = 15
                    layoutParams = params
                    setOnClickListener {
                        emitter.emit(data.first)
                    }
                }
            )
        }
    }
}