package sketch.avatar.api.service.impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import sketch.avatar.api.configuration.AwsConfiguration
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository
import java.util.*

internal class AvatarServiceImplTest {
    private val avatarRepository = mockk<AvatarRepository>()
    private val fileStorageService = mockk<FileStorageServiceImpl>()
    private val awsConfiguration = mockk<AwsConfiguration>()
    private val avatarService = AvatarServiceImpl(avatarRepository, fileStorageService, awsConfiguration)

    @Test
    fun `Find by key`() {
        val key = "key"
        val avatar = Avatar(key)

        every { avatarRepository.findByS3key(key) } returns avatar

        val found = avatarService.findByKey(key)

        assertEquals(key, found.s3key)
        verify(exactly = 1) { avatarRepository.findByS3key(key) }
    }

    @Test
    fun `Save avatar`() {
        val key = "key"
        val avatar = Avatar(key)

        every { avatarRepository.save(avatar) } returns avatar

        val saved = avatarService.save(avatar)

        assertEquals(key, saved.s3key)
        verify(exactly = 1) { avatarRepository.save(avatar) }
    }

    @Test
    fun `Find all avatars`() {
        val key0 = "key0"
        val avatar0 = Avatar(key0)

        val key1 = "key1"
        val avatar1 = Avatar(key1)

        every { avatarRepository.findAll() } returns listOf(avatar0, avatar1)

        val avatars = avatarService.findAll()

        assertEquals(2, avatars.count())
        assertTrue(avatars.contains(avatar0))
        assertTrue(avatars.contains(avatar1))

        val firstAvatar = avatars.elementAt(0)
        assertEquals(key0, firstAvatar.s3key)

        val secondAvatar = avatars.elementAt(1)
        assertEquals(key1, secondAvatar.s3key)

        verify(exactly = 1) { avatarRepository.findAll() }
    }

    @Test
    fun `Find by id`() {
        val id: Long = 1
        val key = "key"
        val avatar = Avatar(key, id)

        every { avatarRepository.findById(id) } returns Optional.of(avatar)

        val found = avatarService.findById(id)

        assertEquals(id, found.id)
        assertEquals(key, found.s3key)
        verify(exactly = 1) { avatarRepository.findById(id) }
    }

    @Test
    fun `Update key`() {
        val id: Long = 1
        val key = "key"
        val avatar = Avatar(key, id)
        val newKey = "newKey"

        every { avatarRepository.findById(id) } returns Optional.of(avatar)
        every { avatarRepository.update(any()) } returns Avatar(newKey, avatar.id)

        val found = avatarService.findById(id)
        val newAvatar = Avatar(newKey, found.id)
        val updated = avatarService.update(newAvatar)

        assertEquals(id, updated.id)
        assertEquals(newKey, updated.s3key)
        verify(exactly = 1) { avatarRepository.update(newAvatar) }
    }
}
