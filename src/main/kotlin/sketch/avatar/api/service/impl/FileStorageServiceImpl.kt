package sketch.avatar.api.service.impl

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.GetObjectRequest
import io.micronaut.context.annotation.Factory
import io.micronaut.http.multipart.CompletedFileUpload
import sketch.avatar.api.configuration.AwsConfiguration
import sketch.avatar.api.service.FileStorageService
import java.io.InputStream
import javax.inject.Singleton

@Factory
class S3ClientFactory {
    @Singleton
    fun s3client(awsConfiguration: AwsConfiguration): AmazonS3 = AmazonS3Client.builder()
//            .withRegion(awsConfiguration.region)
//            .withCredentials(awsConfiguration)
            .build()
}

@Singleton
class FileStorageServiceImpl(private val s3Client: AmazonS3) : FileStorageService {
    override fun get(bucket: String, key: String): InputStream  = s3Client.getObject(GetObjectRequest(bucket, key)).objectContent

    override fun put(key: String, file: CompletedFileUpload) {
        TODO("Not yet implemented")
    }
}
