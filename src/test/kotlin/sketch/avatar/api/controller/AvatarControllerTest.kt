package sketch.avatar.api.controller

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sketch.avatar.api.controller.client.AvatarClient
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository
import io.micronaut.http.HttpHeaders.*
import io.micronaut.http.MediaType

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
        val actual = avatarClient.postAvatar(avatar)

        // Then
        assertNotEquals(0, actual.id)
        assertEquals(key, actual.s3key)
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

        val actualAvatar0 = avatars.first { it.s3key == key0 }
        assertEquals(avatar0.s3key, actualAvatar0.s3key)

        val actualAvatar1 = avatars.first { it.s3key == key1 }
        assertEquals(avatar1.s3key, actualAvatar1.s3key)
    }

    @Test
    fun `Get avatar by id`() {
        // Given
        val key = "key"
        val avatar = postAvatar(key)
        val id = avatar.id

        // When
        val actual = avatarClient.getAvatar(id)

        // Then
        assertEquals(id, actual.id)
        assertEquals(key, actual.s3key)
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

        // When
        val actual = avatarClient.getImage(avatar.id)

        // Then
        assertEquals(200, actual.status.code)
        assertTrue(actual.headers.contains(CONTENT_TYPE))
        assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE.name, actual.headers[CONTENT_TYPE])
    }

    private fun postAvatar(key: String): Avatar {
        val avatar = Avatar(key)
        return avatarClient.postAvatar(avatar)
    }
}
