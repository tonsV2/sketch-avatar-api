package sketch.avatar.api.controller

import io.kotest.assertions.fail
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sketch.avatar.api.controller.client.AvatarClient
import sketch.avatar.api.domain.Avatar
import sketch.avatar.api.repository.AvatarRepository
import javax.inject.Inject

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
        val actualKey = response.key

        assertNotEquals(0, actualId)
        assertEquals(key, actualKey)
    }

    @Test
    fun getAvatars() {
        fail("")
    }

    @Test
    fun getAvatar() {
        fail("")
    }
}
