package org.example.kalbas_backend.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.URLDecoder
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class FileStorageService(
    @Value("\${file.upload-dir:uploads}") private val uploadDir: String
) {
    private val rootLocation: Path = Paths.get(uploadDir)

    init {
        try {
            Files.createDirectories(rootLocation)
        } catch (ex: IOException) {
            throw RuntimeException("Could not initialize storage directory", ex)
        }
    }

    /**
     * Store a file in the root uploads directory with a unique name.
     */
    fun store(file: MultipartFile): String = store(file, null, null)

    /**
     * Store a file in a subdirectory with a desired file name (UUID prefix for uniqueness).
     * @param file the file to store
     * @param subDirectory the subdirectory under uploads/ (e.g. "profiles/123")
     * @param desiredFileName the desired file name (e.g. "profile.jpg")
     * @return the relative URL/path to the stored file
     */
    fun store(file: MultipartFile, subDirectory: String?, desiredFileName: String?): String {
        val originalFilename = file.originalFilename ?: "file"
        val extension = originalFilename.substringAfterLast('.', "")
        val baseName = desiredFileName?.substringBeforeLast('.', "") ?: originalFilename.substringBeforeLast('.', "file")
        val ext = desiredFileName?.substringAfterLast('.', extension)
        val uuid = UUID.randomUUID().toString()
        val uniqueFilename = if (baseName.isNotBlank()) "$uuid-$baseName" else uuid
        val filenameWithExt = if (!ext.isNullOrEmpty()) "$uniqueFilename.$ext" else uniqueFilename

        val targetDir = if (!subDirectory.isNullOrBlank()) rootLocation.resolve(subDirectory) else rootLocation
        try {
            Files.createDirectories(targetDir)
        } catch (ex: IOException) {
            throw RuntimeException("Could not create subdirectory $subDirectory", ex)
        }
        val destinationFile = targetDir.resolve(filenameWithExt).normalize().toAbsolutePath()
        try {
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING)
            }
        } catch (ex: IOException) {
            throw RuntimeException("Failed to store file $filenameWithExt", ex)
        }
        val relativePath = if (!subDirectory.isNullOrBlank()) "$uploadDir/$subDirectory/$filenameWithExt" else "$uploadDir/$filenameWithExt"
        return "/$relativePath"
    }

    fun load(filename: String): Path {
        val cleanFilename = URLDecoder.decode(filename, Charsets.UTF_8)
        val file = rootLocation.resolve(cleanFilename).normalize().toAbsolutePath()
        if (!Files.exists(file)) {
            throw RuntimeException("File not found: $filename")
        }
        return file
    }

    fun delete(filename: String) {
        val file = rootLocation.resolve(filename).normalize().toAbsolutePath()
        try {
            Files.deleteIfExists(file)
        } catch (ex: IOException) {
            throw RuntimeException("Failed to delete file $filename", ex)
        }
    }

    fun listAll(): List<String> =
        Files.list(rootLocation)
            .filter { Files.isRegularFile(it) }
            .map { it.fileName.toString() }
            .toList()
} 