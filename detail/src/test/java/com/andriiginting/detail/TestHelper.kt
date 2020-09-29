package com.andriiginting.detail

import com.andriiginting.core_network.MovieItem
import com.andriiginting.core_network.MovieResponse

fun getDummyResponse() = MovieResponse(
    mutableListOf(
        MovieItem(
            id = "297761",
            movieId = "",
            posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            title = "Suicide Squad",
            overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
            releaseDate = "2016-08-03"
        ),
        MovieItem(
            id = "324668",
            movieId = "",
            posterPath = "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
            title = "Jason Bourne",
            overview = "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
            backdropPath = "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
            releaseDate = "2016-07-27"
        )
    )
)