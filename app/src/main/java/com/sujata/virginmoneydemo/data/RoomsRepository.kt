package com.sujata.virginmoneydemo.data

class RoomsRepository(private val roomsDataSource: RoomsDataSource) {
    suspend fun getRoomsData()=roomsDataSource.getRoomsData()
}