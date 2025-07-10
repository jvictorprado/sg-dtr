package br.upe.poli.sgdtr.consumers.jira

import br.upe.poli.sgdtr.config.JiraFeignConfig
import br.upe.poli.sgdtr.domain.jira.JiraSearchResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "jiraClient",
    url = "\${jira.api.url}",
    configuration = [JiraFeignConfig::class]
)
interface JiraApiClient {

    @GetMapping("/rest/api/3/search")
    fun getIssues(
        @RequestParam("jql") jql: String,
        @RequestParam("startAt", defaultValue = "0") startAt: Int = 0,
        @RequestParam("maxResults", defaultValue = "50") maxResults: Int = 50
    ): JiraSearchResponse
}