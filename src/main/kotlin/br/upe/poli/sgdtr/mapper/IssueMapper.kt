package br.upe.poli.sgdtr.mapper

import br.upe.poli.dto.IssueDTO

interface IssueMapper {
    fun mapIssue(issuesJson: String): List<IssueDTO> // Takes a raw response and returns a list of issues
}
