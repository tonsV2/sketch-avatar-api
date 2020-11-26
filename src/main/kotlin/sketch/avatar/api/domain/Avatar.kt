package sketch.avatar.api.domain

import io.micronaut.core.annotation.Introspected
import javax.persistence.*

@Entity
@Table(indexes = [Index(columnList = "key")])
@Introspected
data class Avatar(
        @Column(nullable = false, unique = true) val key: String,
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long = 0
)
