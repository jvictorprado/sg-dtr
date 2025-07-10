package br.upe.poli.sgdtr.domain.jira

import com.fasterxml.jackson.databind.JsonNode

data class JiraSearchResponse(
    val startAt: Int,
    val maxResults: Int,
    val total: Int,
    val issues: JsonNode
)