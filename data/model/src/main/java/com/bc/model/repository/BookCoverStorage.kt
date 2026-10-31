package com.bc.model.repository

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject

class BookCoverStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    suspend fun save(uri: Uri): String = withContext(Dispatchers.IO) {
        val coversDir = File(context.filesDir, COVERS_DIR).apply {
            mkdirs()
        }

        val file = File(coversDir, "${UUID.randomUUID()}.jpg")

        context.contentResolver.openInputStream(uri).use { input ->
            requireNotNull(input) { "Unable to open selected image" }

            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        file.absolutePath
    }

    suspend fun delete(path: String) = withContext(Dispatchers.IO) {
        val file = File(path)

        // Only allow deleting files inside the covers directory.
        val coversDir = File(context.filesDir, COVERS_DIR).canonicalFile
        val targetFile = file.canonicalFile

        require(targetFile.toPath().startsWith(coversDir.toPath())) {
            "Cannot delete a file outside the covers directory"
        }

        if (targetFile.exists()) {
            targetFile.delete()
        }
    }

    companion object {
        private const val COVERS_DIR = "covers"
    }
}