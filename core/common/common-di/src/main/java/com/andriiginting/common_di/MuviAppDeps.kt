package com.andriiginting.common_di

import com.andriiginting.common_database.MuviDatabase
import com.andriiginting.core_network.MuviDetailService
import com.andriiginting.core_network.MuviHomeService
import com.andriiginting.core_network.MuviSearchService

interface MuviAppDeps {
    fun muviHomeService(): MuviHomeService
    fun muviDetailService(): MuviDetailService
    fun muviSearchService(): MuviSearchService
    fun dbService(): MuviDatabase
}