package com.bc.core.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object CameraImageProvider {

    fun createImageUri(context: Context): Uri {
        val directory = File(context.cacheDir, "camera").apply {
            mkdirs()
        }

        val file = File.createTempFile(
            "cover_",
            ".jpg",
            directory,
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file,
        )
    }
}