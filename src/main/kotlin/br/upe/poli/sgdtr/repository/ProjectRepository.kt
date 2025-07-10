package br.upe.poli.sgdtr.repository

import br.upe.poli.sgdtr.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProjectRepository: JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.key = :key")
    fun findByKey(key: String): Project?
}