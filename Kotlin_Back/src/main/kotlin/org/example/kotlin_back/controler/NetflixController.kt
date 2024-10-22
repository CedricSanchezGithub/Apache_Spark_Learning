package org.example.kotlin_back.controler

import org.example.kotlin_back.entity.NetflixEntity
import org.example.kotlin_back.service.NetflixService
import org.springframework.web.bind.annotation.*

// http://localhost:8081/netflix/getmovies

@RestController
@RequestMapping("/netflix")
class NetflixController(val netflixService: NetflixService) {

    @GetMapping("/getmovies")
    fun getAllMoviesDatas(): List<NetflixEntity> {
        println("Lancement de la fonction getmovies")
        return netflixService.readAllMovies()
    }

    @PostMapping("/postmovies")
    fun postMovies(@RequestBody listNetflixEntity: List<NetflixEntity>) {
        println("Lancement de la fonction postmovies")
        return netflixService.postAllMovies(listNetflixEntity)
    }
}