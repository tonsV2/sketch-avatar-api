package sketch.avatar.api.service.impl

import mu.KotlinLogging
import sketch.avatar.api.configuration.AwsConfiguration
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository
import sketch.avatar.api.service.AvatarService
import sketch.avatar.api.service.FileStorageService
import java.io.InputStream
import javax.inject.Singleton
import javax.persistence.EntityNotFoundException

@Singleton
class AvatarServiceImpl(
        private val avatarRepository: AvatarRepository,
        private val fileStorageService: FileStorageService,
        private val awsConfiguration: AwsConfiguration) : AvatarService {

    private val logger = KotlinLogging.logger {}

    override fun findByKey(key: String): Avatar = avatarRepository.findByS3key(key) ?: throw EntityNotFoundException("Key: $key")

    override fun save(avatar: Avatar): Avatar = avatarRepository.save(avatar)
    override fun findAll(): Iterable<Avatar> = avatarRepository.findAll()

    override fun findById(id: Long): Avatar = avatarRepository.findById(id).orElseGet { throw EntityNotFoundException("Id: $id") }

    override fun getImage(id: Long): InputStream {
        logger.info { "AvatarService.getImage($id)" }
        val avatar = findById(id)
        logger.info { avatar }
        val key = avatar.s3key
        return when {
            key.startsWith(awsConfiguration.legacyPrefix) -> fileStorageService.get(awsConfiguration.legacyBucket, key)
            key.startsWith(awsConfiguration.modernPrefix) -> fileStorageService.get(awsConfiguration.modernBucket, key)
            else -> throw InvalidKeyException(key)
        }
    }
}

class InvalidKeyException(key: String) : RuntimeException("Key starts with neither avatar/ or image/ (key: $key)")
