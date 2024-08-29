package com.jax.scanner

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class Scanner2 : AppCompatActivity(), ZBarScannerView.ResultHandler {
    private lateinit var zbView: ZBarScannerView
    private lateinit var flashButton: Button
    private var torchOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        zbView = ZBarScannerView(this)

        flashButton = Button(this).apply {
            text = "Включить фонарик"
            setOnClickListener {
                torchOn = !torchOn
                zbView.flash = torchOn
                text = if (torchOn) "Выключить фонарик" else "Включить фонарик"
            }
        }


        zbView.addView(flashButton)
        setContentView(zbView)
    }

    override fun onResume() {
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        finish()
    }
}
