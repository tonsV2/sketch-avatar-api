package sketch.avatar.api.configuration

import io.micronaut.context.annotation.Value
import javax.inject.Singleton

@Singleton
class AwsConfiguration(
        @Value("\${aws.s3.legacy.bucket}") val legacyBucket: String,
        @Value("\${aws.s3.legacy.prefix}") val legacyPrefix: String,
        @Value("\${aws.s3.modern.bucket}") val modernBucket: String,
        @Value("\${aws.s3.modern.prefix}") val modernPrefix: String,
//        @Value("\${aws.s3.region}") val region: String
)
