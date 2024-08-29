package com.jax.scanner

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jax.codescannerqrbaretc.databinding.ActivityScannerBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class Scanner : AppCompatActivity() {
    private val binding by lazy {
        ActivityScannerBinding.inflate(layoutInflater)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private val scannerLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Cannot open link", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        scannerLauncher.launch(getCustomisedOptions())
    }
    private fun getCustomisedOptions(): ScanOptions {
        return ScanOptions()
            .setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
            .setPrompt("Scan a code")
            .setCameraId(0)
            .setOrientationLocked(false)
            .setBeepEnabled(false)
            .setBarcodeImageEnabled(true)
            .setTorchEnabled(true)
    }

}
