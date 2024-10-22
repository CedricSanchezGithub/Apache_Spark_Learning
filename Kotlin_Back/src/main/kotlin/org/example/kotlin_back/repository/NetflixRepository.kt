package org.example.kotlin_back.repository

import org.example.kotlin_back.entity.NetflixEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository interface NetflixRepository : JpaRepository<NetflixEntity, String>
