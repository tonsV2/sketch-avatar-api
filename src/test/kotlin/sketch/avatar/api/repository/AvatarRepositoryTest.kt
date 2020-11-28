package sketch.avatar.api.repository

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sketch.avatar.api.domain.Avatar

@MicronautTest
internal class AvatarRepositoryTest(private val avatarRepository: AvatarRepository) {
    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Find by key`() {
        // Given
        val key = "key"
        val avatar = Avatar(key)
        avatarRepository.save(avatar)

        // When
        val actual = avatarRepository.findByS3key(key)

        // Then
        assertEquals(key, actual?.s3key)
    }
}
