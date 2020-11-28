package sketch.avatar.api.service.impl

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CopyObjectRequest
import com.amazonaws.services.s3.model.GetObjectRequest
import io.micronaut.context.annotation.Factory
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
    override fun get(bucket: String, key: String): InputStream {
        val getObjectRequest = GetObjectRequest(bucket, key)
        return s3Client.getObject(getObjectRequest).objectContent
    }

    override fun copy(sourceBucket: String, sourceKey: String, destinationBucket: String, destinationKey: String) {
        val copyObjRequest = CopyObjectRequest(sourceBucket, sourceKey, destinationBucket, destinationKey)
        val copyObjectResult = s3Client.copyObject(copyObjRequest)
        // TODO: Assert success
    }
}
