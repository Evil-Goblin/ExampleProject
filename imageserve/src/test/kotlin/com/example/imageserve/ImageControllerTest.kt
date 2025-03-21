package com.example.imageserve

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@SpringBootTest
class ImageControllerTest(
    private val mockMvc: MockMvc,
    private val imageService: ImageService,
    private val imageRepository: ImageRepository
) {
    private val rootLocation: Path = Paths.get("upload-dir")
    private val testFile: File = ClassPathResource("FactoryMethod.png").file

    @BeforeEach
    fun setup() {
        rootLocation.toFile().deleteRecursively()
        Files.createDirectories(rootLocation)
    }

    @Test
    fun `upload image success`() {
        val mockMultipartFile = MockMultipartFile("file", "FactoryMethod.png", "png", FileInputStream(testFile))

        mockMvc.perform(
            multipart("/upload")
            .file(mockMultipartFile))
            .andExpect(status().isOk)

        Assertions.assertThat(rootLocation.resolve("FactoryMethod.png")).exists()
    }

    @Transactional
    @Test
    fun `persist image success`() {
        val mockMultipartFile = MockMultipartFile("file", "FactoryMethod.png", "png", FileInputStream(testFile))

        imageService.uploadImage(mockMultipartFile)

        mockMvc.perform(post("/images/{filename:.+}", "FactoryMethod.png"))
            .andExpect(status().isOk)

        val get = imageRepository.findById(1).get()
        Assertions.assertThat(get.url).isEqualTo("FactoryMethod.png")
    }

    @Test
    fun `get image success`() {
        val mockMultipartFile = MockMultipartFile("file", "FactoryMethod.png", "png", FileInputStream(testFile))

        imageService.uploadImage(mockMultipartFile)

        mockMvc.perform(get("/images/{filename:.+}", "FactoryMethod.png"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.IMAGE_PNG))
            .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION))
            .andExpect(content().bytes(Files.readAllBytes(testFile.toPath())))
    }
}
