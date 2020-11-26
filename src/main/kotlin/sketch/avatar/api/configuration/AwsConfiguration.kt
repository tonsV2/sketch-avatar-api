package sketch.avatar.api.configuration

import io.micronaut.context.annotation.Value
import javax.inject.Singleton

@Singleton
class AwsConfiguration(
        @Value("\${aws.s3.bucket.legacy}") val legacyBucket: String,
        @Value("\${aws.s3.bucket.modern}") val modernBucket: String,
//        @Value("\${aws.s3.region}") val region: String
)
