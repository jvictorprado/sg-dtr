package br.upe.poli.sgdtr.entity

import jakarta.persistence.*
import java.math.BigDecimal


@Entity
data class Project(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var key: String,

    @OneToMany(mappedBy = "projectId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var artifacts: MutableList<Artifact> = mutableListOf(),

) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Project) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}