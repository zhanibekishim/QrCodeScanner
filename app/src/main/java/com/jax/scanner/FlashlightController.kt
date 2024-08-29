package com.jax.scanner

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.widget.Toast

class FlashlightController(private val context: Context) {
    private var cameraManager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId: String? = null

    init {
        try {
            cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            }
        } catch (e: CameraAccessException) {
            Toast.makeText(context, "Ошибка при изменении подсветки", Toast.LENGTH_SHORT).show()
        }
    }

    fun setFlashlight(on: Boolean) {
        cameraId?.let { id ->
            try {
                cameraManager.setTorchMode(id, on)
            }catch (e: CameraAccessException) {
                Toast.makeText(context, "Ошибка при изменении подсветки", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
