package com.jax.scanner

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.jax.codescannerqrbaretc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bScan.setOnClickListener {
            startActivity(Intent(this, Scanner::class.java))
        }
        binding.gButton.setOnClickListener {
            val text = binding.editQuery.text.toString()
            if(text.isEmpty()){
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show()
            }else{
                generateQrCode(text)
            }
        }
    }

    private fun generateQrCode(text: String) {
        val qrWriter = QRCodeWriter()
        val hints = mapOf(
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L,
            EncodeHintType.MARGIN to 1
        )
        try {
            val bitMatrix = qrWriter.encode(text, BarcodeFormat.QR_CODE, 512, 512, hints)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            binding.imageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Toast.makeText(this, "Ошибка при генерации QR-кода", Toast.LENGTH_SHORT).show()
        }
    }
}
