package com.example.imageserve

import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class ImageController(
    private val imageService: ImageService,
) {
    @PostMapping("/upload")
    fun saveImage(@RequestPart("file") file: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(imageService.uploadImage(file))
    }

    @GetMapping("/images/{filename:.+}")
    fun getImage(@PathVariable filename: String): ResponseEntity<Resource> {
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
            // return file type
            .contentType(MediaType.IMAGE_PNG)
            .body(imageService.downloadImage(filename))
    }

    @PostMapping("/images/{filename:.+}")
    fun persistImage(@PathVariable filename: String): ResponseEntity<String> {
        return ResponseEntity.ok(imageService.persistImage(filename))
    }
}
