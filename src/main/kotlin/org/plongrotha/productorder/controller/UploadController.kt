package org.plongrotha.productorder.controller

import io.swagger.v3.oas.annotations.Operation
import org.plongrotha.productorder.service.MinioService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api")
class UploadController(
    private val minioService: MinioService
) {

    @Operation(summary = "Upload images")
    @PostMapping(value = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<*> {

        if (file.isEmpty) {
            return ResponseEntity.badRequest().body("Please select a file to upload.")
        }

        return try {
            val fileUrl = minioService.uploadImage(file)
            ResponseEntity.ok(mapOf("message" to "Upload successful", "url" to fileUrl))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: ${e.message}")
        }
    }
}