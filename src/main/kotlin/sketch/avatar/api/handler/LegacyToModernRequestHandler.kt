package sketch.avatar.api.handler

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage
import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler
import mu.KotlinLogging
import sketch.avatar.api.configuration.AwsConfiguration
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.service.AvatarService
import sketch.avatar.api.service.FileStorageService
import javax.inject.Inject

@Introspected
class LegacyToModernRequestHandler : MicronautRequestHandler<SQSEvent, Unit>() {
    private val logger = KotlinLogging.logger {}

    @Inject
    lateinit var avatarService: AvatarService

    @Inject
    lateinit var fileStorageService: FileStorageService

    @Inject
    lateinit var awsConfiguration: AwsConfiguration

    override fun execute(input: SQSEvent) {
        logger.info { "LegacyToModernRequestHandler.execute invoked" }
        logger.info { "Got ${input.records.size} records" }

        input.records.forEach {
            handle(it)
        }
    }

    private fun handle(message: SQSMessage) {
        val key = message.body
        logger.info { "LegacyToModernRequestHandler($key)" }
        val avatar = avatarService.findByKey(key)

        val newKey = key.replace(awsConfiguration.legacyPrefix, awsConfiguration.modernPrefix)
        logger.info { "FileStorageService.copy(${awsConfiguration.legacyBucket}, $key, ${awsConfiguration.modernBucket}, $newKey)" }
        fileStorageService.copy(awsConfiguration.legacyBucket, key, awsConfiguration.modernBucket, newKey)

        logger.info { "AvatarService.update($avatar)" }
        val newAvatar = Avatar(newKey, avatar.id)
        avatarService.update(newAvatar)
    }
}
