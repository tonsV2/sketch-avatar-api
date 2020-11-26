package sketch.avatar.api.service

import sketch.avatar.api.domain.Avatar

interface AvatarService {
    fun findByKey(key: String): Avatar
    fun save(avatar: Avatar): Avatar
    fun findAll(): Iterable<Avatar>
    fun findById(id: Long): Avatar
}
