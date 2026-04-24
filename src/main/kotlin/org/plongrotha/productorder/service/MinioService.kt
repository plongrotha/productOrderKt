package org.plongrotha.productorder.service

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.*

@Service
class MinioService {

    @Value("\${minio.url}")
    private lateinit var url: String

    @Value("\${minio.access-key}")
    private lateinit var accessKey: String

    @Value("\${minio.secret-key}")
    private lateinit var secretKey: String

    @Value("\${minio.bucket-name}")
    private lateinit var bucketName: String

    private lateinit var minioClient: MinioClient

        @PostConstruct
    fun init() {
        minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build()

        val exists = minioClient.bucketExists(
            BucketExistsArgs.builder().bucket(bucketName).build()
        )

        if (!exists) {
            minioClient.makeBucket(
                MakeBucketArgs.builder().bucket(bucketName).build()
            )
        }
    }

    fun uploadImage(file: MultipartFile): String {
        val filename = "${UUID.randomUUID()}.${file.originalFilename?.substringAfterLast('.') ?: "jpg"}"
        val bytes: ByteArray = file.bytes
        val inputStream = ByteArrayInputStream(bytes)
        val size = bytes.size.toLong()

        minioClient.putObject(
            PutObjectArgs.builder().bucket(bucketName).`object`(filename).stream(inputStream, size, -1)
                .contentType(file.contentType ?: "application/octet-stream").build()
        )

        return "$url/$bucketName/$filename"
    }
}