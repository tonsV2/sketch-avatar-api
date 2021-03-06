package sketch.avatar.api.controller

import com.amazonaws.util.IOUtils
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import mu.KotlinLogging
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository
import sketch.avatar.api.service.AvatarService

@Controller
class AvatarController(private val avatarService: AvatarService, private val avatarRepository: AvatarRepository) {
    private val logger = KotlinLogging.logger {}

    @Post
    fun postAvatar(avatar: Avatar): Avatar = avatarService.save(avatar)

    // I use this end point to reset the database. Obviously this should never be committed in a real world scenario
    @Delete("/delete-avatars")
    fun deleteAvatars() = avatarRepository.deleteAll()

    @Get
    fun getAvatars(): Iterable<Avatar> = avatarService.findAll()

    @Get("/{id}")
    fun getAvatar(id: Long): Avatar = avatarService.findById(id)

    @Get("/{id}/avatar")
    fun getImage(id: Long): HttpResponse<ByteArray> {
        logger.info { "AvatarController.getImage($id)" }
        val image = avatarService.getImage(id)
        val bytes: ByteArray = IOUtils.toByteArray(image)
        return HttpResponse.ok(bytes)
                .contentType(MediaType.APPLICATION_OCTET_STREAM_TYPE)
    }
}
