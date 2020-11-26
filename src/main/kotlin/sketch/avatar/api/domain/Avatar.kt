package sketch.avatar.api.domain

import io.micronaut.core.annotation.Introspected
import javax.persistence.*

@Entity
@Table(indexes = [Index(columnList = "s3key")])
@Introspected
data class Avatar(
        @Column(nullable = false, unique = true) val s3key: String,
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) val id: Long = 0
)
