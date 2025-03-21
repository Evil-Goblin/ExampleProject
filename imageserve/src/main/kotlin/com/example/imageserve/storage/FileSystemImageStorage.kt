package com.example.imageserve.storage

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Component
class FileSystemImageStorage : ImageStorage {
    private val rootLocation: Path = Paths.get("upload-dir")

    init {
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not create directory at $rootLocation", e)
        }
    }

    override fun upload(file: MultipartFile): String {
        // translate filename use strategy
        val filename = file.originalFilename!!

        val destinationFile = rootLocation.resolve(
            Paths.get(filename)
        )
            .normalize().toAbsolutePath()
        if (destinationFile.parent != rootLocation.toAbsolutePath()) {
            throw StorageException(
                "Cannot store file outside current directory."
            )
        }
        file.inputStream.use { inputStream ->
            Files.copy(
                inputStream, destinationFile,
                StandardCopyOption.REPLACE_EXISTING
            )
        }

        return filename
    }

    override fun download(url: String): Resource {
        val path = rootLocation.resolve(url)

        val urlResource = UrlResource(path.toUri())
        if (urlResource.exists() && urlResource.isReadable) {
            return urlResource
        }

        throw StorageException("Could not download image $url")
    }
}
