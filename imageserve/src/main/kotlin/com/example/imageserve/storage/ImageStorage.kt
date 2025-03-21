package com.example.imageserve.storage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface ImageStorage {

    fun upload(file: MultipartFile): String

    fun download(url: String): Resource
}
