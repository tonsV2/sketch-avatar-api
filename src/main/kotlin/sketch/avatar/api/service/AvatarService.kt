package sketch.avatar.api.service

import sketch.avatar.api.domain.Avatar
import java.io.InputStream

interface AvatarService {
    fun findByKey(key: String): Avatar
    fun save(avatar: Avatar): Avatar
    fun findAll(): Iterable<Avatar>
    fun findById(id: Long): Avatar
    fun getImage(id: Long): InputStream
}
