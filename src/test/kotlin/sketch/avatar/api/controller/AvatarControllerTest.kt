package sketch.avatar.api.controller

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sketch.avatar.api.controller.client.AvatarClient
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository

@MicronautTest
internal class AvatarControllerTest(private val avatarRepository: AvatarRepository, private val avatarClient: AvatarClient) {

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        avatarRepository.deleteAll()
    }

    @Test
    fun `Post avatar`() {
        // Given
        val key = "key"
        val avatar = Avatar(key)

        // When
        val response = avatarClient.postAvatar(avatar)

        // Then
        val actualId = response.id
        val actualKey = response.s3key

        assertNotEquals(0, actualId)
        assertEquals(key, actualKey)
    }

    @Test
    fun `Get all avatars`() {
        // Given
        val key0 = "key0"
        val avatar0 = postAvatar(key0)

        val key1 = "key1"
        val avatar1 = postAvatar(key1)

        // When
        val avatars = avatarClient.getAvatars()

        // Then
        assertEquals(2, avatars.count())

        assertEquals(key0, avatar0.s3key)
        assertEquals(key1, avatar1.s3key)
    }

    @Test
    fun `Get avatar by id`() {
        // Given
        val key = "key"
        val avatar = postAvatar(key)
        val id = avatar.id

        // When
        val response = avatarClient.getAvatar(id)

        // Then
        assertEquals(id, response.id)
        assertEquals(key, response.s3key)
    }

    /**
     * This test assumes an image exists in S3 with a key matching what's given below
     * The actual binary content of the response isn't verified
     */
    @Test
    fun `Get avatar image by id`() {
        // Given
        val key = "image/avatar-1.png"
        val avatar = postAvatar(key)
        val id = avatar.id

        // When
        val response = avatarClient.getImage(id)

        // Then
        assertEquals(200, response.statusCode)
        assertTrue(response.isBase64Encoded)
    }

    private fun postAvatar(key: String): Avatar {
        val avatar = Avatar(key)
        return avatarClient.postAvatar(avatar)
    }
}
