package br.upe.poli.sgdtr.service

import br.upe.poli.api.DTRApiService
import br.upe.poli.dto.*
import br.upe.poli.sgdtr.consumers.jira.JiraApiClient
import br.upe.poli.sgdtr.entity.Artifact
import br.upe.poli.sgdtr.entity.DTR
import br.upe.poli.sgdtr.entity.Issue
import br.upe.poli.sgdtr.entity.Project
import br.upe.poli.sgdtr.mapper.IssueMapper
import br.upe.poli.sgdtr.mapper.JiraIssueMapper
import br.upe.poli.sgdtr.repository.ArtifactRepository
import br.upe.poli.sgdtr.repository.DTRRepository
import br.upe.poli.sgdtr.repository.IssueRepository
import br.upe.poli.sgdtr.repository.ProjectRepository
import org.springframework.stereotype.Service
import java.math.BigInteger
import kotlin.jvm.optionals.getOrNull

@Service
class DTRServiceImpl(
    private val jiraApiClient: JiraApiClient,
    private val projectRepository: ProjectRepository,
    private val artifactRepository: ArtifactRepository,
    private val issueRepository: IssueRepository,
    private val dtrRepository: DTRRepository
) : DTRApiService {
    override fun createArtifact(projectId: Long, createArtifactRequest: CreateArtifactRequest): ArtifactDTO {
        val savedArtifact = artifactRepository.save(
            Artifact(
                admin = createArtifactRequest.admin,
                description = createArtifactRequest.description,
                key = createArtifactRequest.key,
                projectId = projectId
            ))
        return savedArtifact.toDTO()
    }

    override fun createIssue(projectId: Long, artifactId: Long, issueDTO: IssueDTO): ProjectDTO {
        TODO("Not yet implemented")
    }

    override fun createProject(createProjectRequest: CreateProjectRequest): ProjectDTO {
        TODO("Not yet implemented")
    }

    override fun detailArtifact(projectId: Long, artifactId: Long): ArtifactDetailDTO {
        TODO("Not yet implemented")
    }

    override fun detailIssue(projectId: Long, artifactId: Long, issueId: Long): IssueDTO {
        val optionalIssue =  issueRepository.findById(issueId)
        return optionalIssue.getOrNull()?.toDTO() ?: throw IllegalArgumentException("Issue not found")
    }

    override fun detailProject(
        projectId: Long,
        page: BigInteger,
        size: BigInteger,
        name: String?,
        admin: String,
        client: String?
    ): ProjectPage {
        TODO("Not yet implemented")
    }

    override fun getProjectsPage(
        page: BigInteger,
        size: BigInteger,
        name: String?,
        admin: String,
        client: String?
    ): ProjectPage {
        TODO("Not yet implemented")
    }

    override fun importProject(projectImportRequest: ImportProjectRequest): ProjectDetailDTO {
        // Fetch all epics for the project
        val epics = fetchEpics(projectImportRequest.projectKey)
        val savedProject = projectRepository.save(Project(key = projectImportRequest.projectKey))
        val artifacts = mutableListOf<ArtifactDTO>()
        epics.map { epic ->
            val artifactDTO = ArtifactDTO(
                key = epic.key, admin = "", title = epic.summary, description = epic.description
            )
            artifacts.add(artifactDTO)
            val issuesDTO = fetchChildIssues(epic.key)

            val artifactDetailDTO = ArtifactDetailDTO(
                artifact = artifactDTO,
                issues = issuesDTO
            )

            val savedArtifact = artifactRepository.save(
                    Artifact (
                    key = artifactDetailDTO.artifact.key,
                    admin = artifactDetailDTO.artifact.admin!!,
                    title = artifactDetailDTO.artifact.title,
                    description = artifactDetailDTO.artifact.description,
                    projectId = savedProject.id
                )
            )


            val issues = issuesDTO.map { Issue(
                key = it.key,
                artifactId = savedArtifact.id,
                type = it.type,
                description = it.description,
                created = it.created,
                updated = it.updated
            ) }

            issueRepository.saveAll(issues)
        }
        return ProjectDetailDTO(
            project =  ProjectDTO(savedProject.id,savedProject.key),
            artifacts = artifacts
        )
    }

    private fun fetchEpics(projectKey: String): List<IssueDTO> {
        val jql = "project = $projectKey AND issuetype = Epic"
        return fetchIssues(jql)
    }

    private fun fetchChildIssues(epicKey: String): List<IssueDTO> {
        val jql = "parent = $epicKey"
        val childIssues = fetchIssues(jql)
        return childIssues.map { issue ->
            IssueDTO(
                key = issue.key,
                self = issue.self,
                summary = issue.summary,
                description = issue.description,
                type = issue.type,
                created = issue.created,
                updated = issue.updated,
                idtr = false,
            )
        }
    }

    private fun fetchIssues(jql: String): List<IssueDTO> {
        val issues = mutableListOf<IssueDTO>()
        val mapper: IssueMapper = JiraIssueMapper()

        var startAt = 0
        var total: Int
        do {
            val response = jiraApiClient.getIssues(jql, startAt)
            val mappedIssues = mapper.mapIssue(response.issues.toString())
            issues.addAll(mappedIssues)
            startAt += response.maxResults
            total = response.total
        } while (startAt < total)
        return issues
    }

    override fun listArtifacts(projectId: Long, type: String?, admin: String?): ArtifactPage {
        TODO("Not yet implemented")
    }

    override fun listDTRProposals(
        page: BigInteger,
        size: BigInteger,
        projectKey: Long?,
        artifactKey: Long?,
        issueKey: Long?,
        status: IDTRStatus?,
        cause: IDTRCause?,
        cost: IDTRCost?,
        impact: IDTRImpact?,
        priority: IDTRPriority?,
        intentional: Boolean?
    ): IDTRDTO {
        TODO("Not yet implemented")
    }

    override fun listIssues(
        projectId: Long,
        artifactId: Long,
        page: BigInteger,
        size: BigInteger,
        id: String?,
        admin: String?,
        issueType: IssueType?,
        idtr: Boolean?
    ): IssuePage {
        TODO("Not yet implemented")
    }

    override fun updateDTR(dtrId: Long, updateIDTRDTO: UpdateIDTRDTO): IDTRDTO {
        TODO("Not yet implemented")
    }

    override fun submitDTRProposal(submitDTRProposal: SubmitDTRProposal): IDTRDTO {
        val issue = issueRepository.findById(submitDTRProposal.issueId!!).getOrNull() ?: throw IllegalArgumentException("Issue not found")

        val pendingIDTR = dtrRepository.save(
            DTR(
                    admin = "",
                    description = "",
                    cause = DTR.DTRCause.fromCode(submitDTRProposal.cause.value)!!,
                    impact = DTR.DTRImpact.fromCode(submitDTRProposal.impact.value)!!,
                    cost = DTR.DTRCost.fromCode(submitDTRProposal.cost.value)!!,
                    priority = DTR.DTRPriority.fromCode(submitDTRProposal.priority.value)!!,
                    intentional = submitDTRProposal.intentional,
                    status = DTR.DTRStatus.INDICADO,
                    issue = issue,
                    title = "",
                )
        )
        return pendingIDTR.toDTO()
    }


    override fun updateIssue(projectId: Long, artifactId: Long, issueId: Long, issueDTO: IssueDTO): IssueDTO {
        TODO("Not yet implemented")
    }

}