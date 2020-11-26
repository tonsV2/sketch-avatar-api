package sketch.avatar.api.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.service.AvatarService

@Controller
class AvatarController(private val avatarService: AvatarService) {
    @Post
    fun postAvatar(avatar: Avatar): Avatar = avatarService.save(avatar)

    @Get
    fun getAvatars(): Iterable<Avatar> = avatarService.findAll()

    @Get("/{id}")
    fun getAvatar(id: Long): Avatar = avatarService.findById(id)
}
