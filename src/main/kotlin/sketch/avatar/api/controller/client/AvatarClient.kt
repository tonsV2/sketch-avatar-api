package sketch.avatar.api.controller.client

import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import sketch.avatar.api.domain.Avatar

@Client("/")
interface AvatarClient {
    @Post("/")
    fun postAvatar(avatar: Avatar): Avatar
}
