package br.upe.poli.sgdtr.repository

import br.upe.poli.sgdtr.entity.Artifact
import br.upe.poli.sgdtr.entity.Issue
import br.upe.poli.sgdtr.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IssueRepository: JpaRepository<Issue, Long> {

    @Query("SELECT i FROM Issue i WHERE i.key = :key")
    fun findByKey(key: String): Issue?
}