package sketch.avatar.api.service.impl

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import sketch.avatar.api.configuration.AwsConfiguration
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository
import java.io.InputStream
import java.util.*

internal class AvatarServiceImplTest {
    private val avatarRepository = mockk<AvatarRepository>()
    private val fileStorageService = mockk<FileStorageServiceImpl>()
    private val awsConfiguration = mockk<AwsConfiguration>()
    private val avatarService = AvatarServiceImpl(avatarRepository, fileStorageService, awsConfiguration)

    @Test
    fun `Find by key`() {
        // Given
        val key = "key"
        val avatar = Avatar(key)

        every { avatarRepository.findByS3key(key) } returns avatar

        // When
        val actual = avatarService.findByKey(key)

        // Then
        assertEquals(key, actual.s3key)
        verify(exactly = 1) { avatarRepository.findByS3key(key) }
    }

    @Test
    fun `Save avatar`() {
        // Given
        val key = "key"
        val avatar = Avatar(key)

        every { avatarRepository.save(avatar) } returns avatar

        // When
        val actual = avatarService.save(avatar)

        // Then
        assertEquals(key, actual.s3key)
        verify(exactly = 1) { avatarRepository.save(avatar) }
    }

    @Test
    fun `Find all avatars`() {
        // Given
        val key0 = "key0"
        val avatar0 = Avatar(key0)

        val key1 = "key1"
        val avatar1 = Avatar(key1)

        every { avatarRepository.findAll() } returns listOf(avatar0, avatar1)

        // When
        val avatars = avatarService.findAll()

        // Then
        assertEquals(2, avatars.count())

        val actualAvatar0 = avatars.first { it.s3key == key0 }
        assertEquals(key0, actualAvatar0.s3key)

        val actualAvatar1 = avatars.first { it.s3key == key1 }
        assertEquals(key1, actualAvatar1.s3key)

        verify(exactly = 1) { avatarRepository.findAll() }
    }

    @Test
    fun `Find by id`() {
        // Given
        val id: Long = 1
        val key = "key"
        val avatar = Avatar(key, id)

        every { avatarRepository.findById(id) } returns Optional.of(avatar)

        // When
        val actual = avatarService.findById(id)

        // Then
        assertEquals(id, actual.id)
        assertEquals(key, actual.s3key)
        verify(exactly = 1) { avatarRepository.findById(id) }
    }

    @Test
    fun `Update key`() {
        // Given
        val id: Long = 1
        val key = "key"
        val avatar = Avatar(key, id)
        val newKey = "newKey"

        every { avatarRepository.findById(id) } returns Optional.of(avatar)
        every { avatarRepository.update(any()) } returns Avatar(newKey, avatar.id)

        // When
        val found = avatarService.findById(id)
        val newAvatar = Avatar(newKey, found.id)
        val updated = avatarService.update(newAvatar)

        // Then
        assertEquals(id, updated.id)
        assertEquals(newKey, updated.s3key)
        verify(exactly = 1) { avatarRepository.update(newAvatar) }
    }

    @Test
    fun `Get image throws exception on invalid key`() {
        // Given
        val id: Long = 1
        val key = "key"
        val avatar = Avatar(key, id)
        val bucket = "bucket"
        val inputStream = mockk<InputStream>()

        every { avatarRepository.findById(id) } returns Optional.of(avatar)
        every { awsConfiguration.legacyPrefix } returns "legacy/"
        every { awsConfiguration.modernPrefix } returns "modern/"
        every { fileStorageService.get(bucket, key) } returns inputStream

        // When
        shouldThrow<InvalidKeyException> {
            avatarService.getImage(id)
        }

        // Then
        verify(exactly = 1) { avatarRepository.findById(id) }
    }

    @Test
    fun `Get image from legacy`() {
        // Given
        val id: Long = 1
        val key = "legacy/avatar.png"
        val avatar = Avatar(key, id)
        val bucket = "legacy-bucket"
        val inputStream = mockk<InputStream>()

        every { avatarRepository.findById(id) } returns Optional.of(avatar)
        every { awsConfiguration.legacyBucket } returns bucket
        every { awsConfiguration.legacyPrefix } returns "legacy/"
        every { awsConfiguration.modernPrefix } returns "modern/"
        every { fileStorageService.get(bucket, key) } returns inputStream

        // When
        avatarService.getImage(id)

        // Then
        verify(exactly = 1) { avatarRepository.findById(id) }
        verify(exactly = 1) { awsConfiguration.legacyPrefix }
        verify(exactly = 1) { awsConfiguration.legacyBucket }
        verify(exactly = 0) { awsConfiguration.modernPrefix }
        verify(exactly = 0) { awsConfiguration.modernBucket }
        verify(exactly = 1) { fileStorageService.get(bucket, key) }
    }

    @Test
    fun `Get image from modern`() {
        // Given
        val id: Long = 1
        val key = "modern/avatar.png"
        val avatar = Avatar(key, id)
        val bucket = "modern-bucket"
        val inputStream = mockk<InputStream>()

        every { avatarRepository.findById(id) } returns Optional.of(avatar)
        every { awsConfiguration.legacyPrefix } returns "legacy/"
        every { awsConfiguration.modernBucket } returns bucket
        every { awsConfiguration.modernPrefix } returns "modern/"
        every { fileStorageService.get(bucket, key) } returns inputStream

        // When
        avatarService.getImage(id)

        // Then
        verify(exactly = 1) { avatarRepository.findById(id) }
        verify(exactly = 1) { awsConfiguration.legacyPrefix }
        verify(exactly = 0) { awsConfiguration.legacyBucket }
        verify(exactly = 1) { awsConfiguration.modernPrefix }
        verify(exactly = 1) { awsConfiguration.modernBucket }
        verify(exactly = 1) { fileStorageService.get(bucket, key) }
    }
}
