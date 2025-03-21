package com.example.imageserve

import com.example.imageserve.storage.ImageStorage
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    private val imageStorage: ImageStorage,
    private val imageRepository: ImageRepository,
) {
    fun uploadImage(file: MultipartFile): String {
        return imageStorage.upload(file)
    }

    @Transactional
    fun persistImage(url: String): String {
        val image = Image(url)

        imageRepository.save(image)

        return image.url
    }

    fun downloadImage(url: String): Resource {
        return imageStorage.download(url)
    }
}
