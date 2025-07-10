package br.upe.poli.sgdtr.entity

import br.upe.poli.dto.*
import jakarta.persistence.*

@Entity
data class DTR(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var title: String,
    var status: DTRStatus,
    var cause: DTRCause,
    var impact: DTRImpact,
    var cost: DTRCost,
    val priority: DTRPriority,
    var intentional: Boolean,
    var description: String,
    var admin: String,
    @OneToOne
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    var issue: Issue

    ) {

    enum class DTRCost(val value: String) {

        ALTO("ALTO"),
        MEDIO("MEDIO"),
        BAIXO("BAIXO");

        companion object {
            fun fromCode(code: String): DTRCost? {
                return DTRCost.entries.find { it.name == code }
            }
        }
    }

    enum class DTRPriority(val value: String) {

        ALTA("ALTA"),
        MEDIA("MEDIA"),
        BAIXA("BAIXA");

        companion object {
            fun fromCode(code: String): DTRPriority? {
                return DTRPriority.entries.find { it.name == code }
            }
        }
    }

    enum class DTRStatus(val value: String) {

        INDICADO("INDICADO"),
        ABERTO("ABERTO"),
        REJEITADO("REJEITADO"),
        PAGO("PAGO");

        companion object {
            fun fromCode(code: String): DTRStatus? {
                return DTRStatus.entries.find { it.name == code }
            }
        }

    }

    enum class DTRCause(val description: String) {
        C1("Problemas de desenvolvimento"),
        C1_1("Mudança de requisitos"),
        C1_2("Requisitos imprecisos ou complexos"),
        C2("Fatores externos"),
        C2_1("Pressão"),
        C2_2("Desconhecimento da necessidade do cliente"),
        C3("Problemas na Metodologia"),
        C3_1("Falta de processo bem definido"),
        C3_2("Teste inadequado/ mal planejado/ mal executado"),
        C3_3("Falta de análise de requisitos"),
        C4("Organizacional"),
        C4_1("Alta rotatividade da equipe"),
        C4_2("Falta de profissionais qualificados"),
        C5("Pessoas"),
        C5_1("Falta de comprometimento"),
        C5_2("Falta de comunicação da equipe"),
        C5_3("Falta de conhecimento"),
        C5_4("Falta de experiência"),
        C6("Planejamento e gerenciamento"),
        C6_1("Prazo e gerenciamento não eficaz do projeto"),
        C6_2("Gerenciamento dos recursos não eficaz");

        companion object {
            fun fromCode(code: String): DTRCause? {
                return entries.find { it.name == code }
            }
        }
    }


    enum class DTRImpact(val description: String) {
        I1("No Desenvolvimento: efeitos relacionados às atividades de desenvolvimento do projeto"),
        I1_1("Mudanças de design"),
        I1_2("Documentação inadequada"),
        I1_3("Constante necessidade de reteste"),
        I2("Em Fatores Externos: efeitos relacionados à qualidade dos artefatos"),
        I2_1("Baixa qualidade externa"),
        I2_2("Falta de credibilidade no produto"),
        I2_3("Projeto não concluído"),
        I3("Em Fatores Internos: efeitos associados à qualidade interna"),
        I3_1("Baixa capacidade de manutenção"),
        I3_2("Necessidade de refatoração e código incorreto"),
        I4("Na Organização: efeitos a nível organizacional"),
        I4_1("Perda financeira"),
        I4_2("Imagem da empresa prejudicada"),
        I5("Nas Pessoas: efeitos relacionados à equipe de desenvolvimento"),
        I5_1("Estresse com stakeholders"),
        I5_2("Insatisfação dos stakeholders"),
        I5_3("Desmotivação da equipe"),
        I6("No Planejamento e Gerenciamento: efeitos relacionados ao planejamento do projeto e gerenciamento"),
        I6_1("Atraso na entrega"),
        I6_2("Retrabalho"),
        I6_3("Aumento do esforço da equipe");

        companion object {
            fun fromCode(code: String): DTRImpact? {
                return entries.find { it.name == code }
            }
        }
    }

    fun toDTO() = IDTRDTO(
        key = this.issue.key,
        description = this.description,
        cause = IDTRCause.valueOf(this.cause.toString()),
        cost = IDTRCost.valueOf(this.cost.toString()),
        impact = IDTRImpact.valueOf(this.impact.toString()),
        priority = IDTRPriority.valueOf(this.priority.toString()),
        intentional = this.intentional,
        id = this.id
    )
}

