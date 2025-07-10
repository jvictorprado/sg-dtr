package br.upe.poli.sgdtr.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import feign.RequestInterceptor
import org.springframework.context.annotation.Bean
import java.util.Base64

@Configuration
class JiraFeignConfig(
    @Value("\${jira.auth.username}") private val username: String,
    @Value("\${jira.auth.api-token}") private val apiToken: String
) {

    @Bean
    fun jiraRequestInterceptor(): RequestInterceptor {
        val authHeader = "Basic " + Base64.getEncoder().encodeToString("$username:$apiToken".toByteArray())
        return RequestInterceptor { requestTemplate ->
            requestTemplate.header("Authorization", authHeader)
            requestTemplate.header("Content-Type", "application/json")
        }
    }
}
