package br.upe.poli.sgdtr.entity

import br.upe.poli.dto.ArtifactDTO
import jakarta.persistence.*


@Entity
data class Artifact(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var key: String,
    var title: String? = "",
    var projectId: Long,

    @OneToMany(mappedBy = "artifactId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var issues: MutableList<Issue> = mutableListOf(),

    var description: String? = "",
    var admin: String,

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Artifact) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun toDTO() = ArtifactDTO(
        admin = this.admin,
        description = this.description,
        key = this.key,
        title = title
    )
}