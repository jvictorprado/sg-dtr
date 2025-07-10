package br.upe.poli.sgdtr.repository

import br.upe.poli.sgdtr.entity.Artifact
import br.upe.poli.sgdtr.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArtifactRepository: JpaRepository<Artifact, Long> {

    @Query("SELECT a FROM Artifact a WHERE a.key = :key")
    fun findByKey(key: String): Artifact?
}