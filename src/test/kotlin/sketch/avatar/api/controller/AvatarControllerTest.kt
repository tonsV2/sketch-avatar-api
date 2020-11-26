package sketch.avatar.api.controller

import io.kotest.assertions.fail
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.MockKAnnotations
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import sketch.avatar.api.repository.AvatarRepository
import javax.inject.Inject

@MicronautTest
internal class AvatarControllerTest(private val avatarRepository: AvatarRepository) {

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        avatarRepository.deleteAll()
    }

    @Test
    fun postAvatar() {
        fail("")
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
