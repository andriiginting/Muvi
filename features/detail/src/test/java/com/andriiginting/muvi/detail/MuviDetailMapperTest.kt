package com.andriiginting.muvi.detail

import com.andriiginting.muvi.detail.domain.MuviDetailMapper
import com.andriiginting.muvi.detail.domain.MuviDetailMapperImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MuviDetailMapperTest {
    private lateinit var mapper: MuviDetailMapper

    @Before
    fun setUp() {
        mapper = MuviDetailMapperImpl()
    }

    @Test
    fun `when want to map from MuviFavEntity into Movie data model`(){
        val result = mapper.mapToMovieItem(getFavoritesDummy())

        assertEquals(result, getMovieDummyResponse())
    }

    @Test
    fun `when want to map from Movie data model into MuviFavEntity`(){
        val result = mapper.mapToMuviFavorite(getMovieDummyResponse())

        assertEquals(result, getFavoritesDummy())
    }
}