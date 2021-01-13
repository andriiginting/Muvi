package com.andriiginting.common_di

import com.andriiginting.common_database.MuviDatabase
import com.andriiginting.common_database.MuviFavoriteDAO
import com.andriiginting.core_network.MuviDetailService
import com.andriiginting.core_network.MuviHomeService

interface MuviAppDeps {
    fun muviHomeService(): MuviHomeService
    fun muviDetailService(): MuviDetailService
    fun dbService(): MuviDatabase
}