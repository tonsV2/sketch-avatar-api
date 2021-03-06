package sketch.avatar.api.controller.client

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import sketch.avatar.api.domain.Avatar

@Client("/")
interface AvatarClient {
    @Post
    fun postAvatar(avatar: Avatar): Avatar

    @Get
    fun getAvatars(): Iterable<Avatar>

    @Get("{id}")
    fun getAvatar(id: Long): Avatar

    @Get("{id}/avatar")
    fun getImage(id: Long): HttpResponse<ByteArray>
}
