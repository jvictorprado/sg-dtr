package br.upe.poli.sgdtr.repository

import br.upe.poli.sgdtr.entity.DTR
import org.springframework.data.jpa.repository.JpaRepository

interface DTRRepository: JpaRepository<DTR, Long> {

}