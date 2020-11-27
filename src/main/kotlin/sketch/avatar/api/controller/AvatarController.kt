package sketch.avatar.api.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.server.types.files.StreamedFile
import mu.KotlinLogging
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.service.AvatarService

@Controller
class AvatarController(private val avatarService: AvatarService) {
    private val logger = KotlinLogging.logger {}

    @Post
    fun postAvatar(avatar: Avatar): Avatar = avatarService.save(avatar)

    @Get
    fun getAvatars(): Iterable<Avatar> = avatarService.findAll()

    @Get("/{id}")
    fun getAvatar(id: Long): Avatar = avatarService.findById(id)

    @Get("/{id}/avatar")
    fun getImage(id: Long): StreamedFile {
        logger.info { "AvatarController.getImage($id)" }
        val image = avatarService.getImage(id)
        return StreamedFile(image, MediaType.APPLICATION_OCTET_STREAM_TYPE)
    }
}
