package br.upe.poli.sgdtr.entity

import br.upe.poli.dto.IDTRCost
import br.upe.poli.dto.IDTRStatus
import br.upe.poli.dto.IssueDTO
import br.upe.poli.dto.IssueType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate


@Entity
data class Issue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var key: String,
    var artifactId: Long,
    @Column(length = 1024)
    var description: String? = "",
    var type: IssueType,
    var dtrRequestStatus: DTRRequestStatus = DTRRequestStatus.NONE,
    var created: LocalDate,
    var updated: LocalDate? = LocalDate.now(),
    @OneToOne(mappedBy = "issue")
    var dtr: DTR? = null
)
    {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Issue) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    enum class DTRRequestStatus {
        NONE, INDICADA, REJEITADA, APROVADA
    }

    fun toDTO() = IssueDTO(
        key =  this.key,
        type = this.type,
        created = this.created,
        updated = this.updated,
        idtr = if (this.dtr != null) true else false

    )
}