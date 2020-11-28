package sketch.avatar.api.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import sketch.avatar.api.domain.Avatar

@Repository
interface AvatarRepository : CrudRepository<Avatar, Long> {
    fun findByS3key(s3key: String): Avatar?
}
