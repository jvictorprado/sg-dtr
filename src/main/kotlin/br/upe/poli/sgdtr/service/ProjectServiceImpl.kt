package br.upe.poli.sgdtr.service

import br.upe.poli.ProjectApiService
import br.upe.poli.dto.ArtifactPage
import br.upe.poli.dto.ImportProjectRequest
import br.upe.poli.dto.Project
import br.upe.poli.dto.ProjectPage
import java.math.BigInteger

class ProjectServiceImpl() : ProjectApiService {
    override fun getProjectsPage(
        page: BigInteger,
        size: BigInteger,
        name: String?,
        admin: String,
        client: String?
    ): ProjectPage {
        TODO("Not yet implemented")
    }

    override fun importProject(importProjectRequest: ImportProjectRequest): Project {
        TODO("Not yet implemented")
    }

    override fun listArtifacts(projectId: String, type: String?, admin: String?): ArtifactPage {
        TODO("Not yet implemented")
    }

}