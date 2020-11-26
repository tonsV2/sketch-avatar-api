package sketch.avatar.api.service.impl

import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository
import sketch.avatar.api.service.AvatarService
import javax.inject.Singleton

@Singleton
class AvatarServiceImpl(private val avatarRepository: AvatarRepository) : AvatarService {
    override fun findByKey(key: String): Avatar = avatarRepository.findByKey(key)
    override fun save(avatar: Avatar): Avatar = avatarRepository.save(avatar)
    override fun findAll(): Iterable<Avatar> = avatarRepository.findAll()
    override fun findById(id: Long): Avatar = avatarRepository.findById(id).get()
}
