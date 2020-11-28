package sketch.avatar.api.handler

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage
import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler
import mu.KotlinLogging
import sketch.avatar.api.service.AvatarService
import javax.inject.Inject

@Introspected
class LegacyToModernRequestHandler : MicronautRequestHandler<SQSEvent, Unit>() {
    private val logger = KotlinLogging.logger {}

    @Inject
    lateinit var avatarService: AvatarService

    override fun execute(input: SQSEvent) {
        logger.info { "LegacyToModernRequestHandler.execute invoked" }

        input.records.forEach {
            handle(it)
        }
    }

    private fun handle(message: SQSMessage) {
        val key = message.body
        logger.info { "LegacyToModernRequestHandler($key)" }
        val avatar = avatarService.findByKey(key)
        logger.info { avatar }
    }
}
