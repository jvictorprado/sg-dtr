package br.upe.poli.sgdtr.mapper

import br.upe.poli.dto.IssueDTO
import br.upe.poli.dto.IssueType
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDate

class JiraIssueMapper : IssueMapper {
    private val objectMapper = ObjectMapper()

    override fun mapIssue(issuesJson: String): List<IssueDTO> {
        val issuesNode: JsonNode = objectMapper.readTree(issuesJson)

        return issuesNode.map { issueNode ->
            val fieldsNode = issueNode["fields"]
            val descriptionNode = fieldsNode["description"]

            val descriptionText = descriptionNode?.get("content")?.get(0)?.get("content")?.get(0)?.get("text")?.asText()
            val type = issueNode?.get("issueType")?.get("name")?.asText()
            val parentNode = fieldsNode["parent"]
            val parentKey = parentNode?.get("key")?.asText()

            IssueDTO(
                key = issueNode["key"].asText(),
                self = issueNode["self"].asText(),
                summary = fieldsNode["summary"]?.asText() ?: "",
                description = descriptionText ?: "",
                type = type?.let { IssueType.valueOf(it) } ?: IssueType.BUG,
                created = LocalDate.now(),//fieldsNode["created"]?.asText()!!,
                updated = LocalDate.now(),//fieldsNode["updated"]?.asText() ?: "",
                parentKey = parentKey
            )
        }
    }
}
