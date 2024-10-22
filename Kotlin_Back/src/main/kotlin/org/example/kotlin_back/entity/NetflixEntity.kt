package org.example.kotlin_back.entity

import jakarta.persistence.*

@Entity
@Table(name = "netflix")
data class NetflixEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "movie", nullable = false)
    var movie: String? = "aucune donnée pour movie",

    @Column(name = "country", nullable = false)
    var country: String? = "aucune donnée pour country",

    @Column(name = "show_id", nullable = false)
    var showId: String? = "aucune donnée pour show_id",

    @Column(name = "type", nullable = false)
    var type: String? = "aucune donnée pour type",

    @Column(name = "director", nullable = false)
    var director: String? = "aucune donnée pour director",

    @Column(name = "cast", nullable = false)
    var cast: String? = "aucune donnée pour cast",

    @Column(name = "date_added", nullable = false)
    var dateAdded: String? = "aucune donnée pour date_added",

    @Column(name = "release_year", nullable = false)
    var releaseYear: String? = "aucune donnée pour release_year",

    @Column(name = "rating", nullable = false)
    var rating: String? = "aucune donnée pour rating",

    @Column(name = "duration", nullable = false)
    var duration: String? = "aucune donnée pour duration",

    @Column(name = "listed_in", nullable = false)
    var listedIn: String? = "aucune donnée pour listed_in",

    @Column(name = "description", nullable = false)
    var description: String? = "aucune donnée pour description"

)
