package org.example.kotlin_back.service

import org.example.kotlin_back.entity.NetflixEntity
import org.example.kotlin_back.repository.NetflixRepository
import org.springframework.stereotype.Service

@Service
class NetflixService(
    val netflixRepository: NetflixRepository
) {

    fun readAllMovies(): List<NetflixEntity> {
        return netflixRepository.findAll()
    }

    fun postAllMovies(movies: List<NetflixEntity>) {
        netflixRepository.saveAll(movies)
    }

}
