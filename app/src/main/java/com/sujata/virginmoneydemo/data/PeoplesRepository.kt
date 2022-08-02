package com.sujata.virginmoneydemo.data

class PeoplesRepository(private val peopleDataSource: PeopleDataSource) {
    suspend fun getPeoplesData()=peopleDataSource.getPeoplesData()
}