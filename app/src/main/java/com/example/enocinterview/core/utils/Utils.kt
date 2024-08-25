package com.example.enocinterview.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Composable
fun GetScreenWidth(): Int {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp
}

object Base64Helper {

    fun uriToBase64(context: Context,uri: Uri): String? {
        return uriToByteArray(context, uri)?.let { byteArrayToBase64(byteArray = it) }
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        // Compress the bitmap into a PNG or JPEG format
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

    private  fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val buffer = ByteArrayOutputStream()
            val byteChunk = ByteArray(1024)
            var bytesRead: Int

            while (inputStream?.read(byteChunk).also { bytesRead = it ?: -1 } != -1) {
                buffer.write(byteChunk, 0, bytesRead)
            }

            buffer.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun byteArrayToBase64(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

}


object  UploadedImageSizeHelper {


    fun isUriFileSizeLessThan1MB(context: Context, uri: Uri): Boolean {
        var fileSize: Long = 0

        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst()) {
                fileSize = cursor.getLong(sizeIndex)
            }
        }

        // Convert the file size to MB and check if it's less than 1 MB (1 MB = 1_048_576 bytes)
        return fileSize <= 1_048_576
    }

    fun isBitmapSizeLessThanOrEqual1MB(bitmap: Bitmap): Boolean {
        // Compress the bitmap to a byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // Check if the size of the byte array is less than or equal to 1 MB (1 MB = 1_048_576 bytes)
        return byteArray.size <= 1_048_576 // 1 MB in bytes
    }
}
