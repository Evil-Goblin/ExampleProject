package com.example.imageserve

import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<Image, Long> {
}
