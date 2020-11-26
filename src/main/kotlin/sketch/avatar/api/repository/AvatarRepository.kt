package sketch.avatar.api.repository

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import sketch.avatar.api.domain.Avatar

@Repository
interface AvatarRepository : CrudRepository<Avatar, Long> {
    @Query("from Avatar a where a.key = ':key'")
    fun findByKey(key: String): Avatar
}
