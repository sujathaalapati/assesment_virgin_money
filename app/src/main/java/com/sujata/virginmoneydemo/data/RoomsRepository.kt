package com.sujata.virginmoneydemo.data

import javax.inject.Inject

class RoomsRepository @Inject constructor(private val roomsDataSource: RoomsDataSource) {
    suspend fun getRoomsData()=roomsDataSource.getRoomsData()
}