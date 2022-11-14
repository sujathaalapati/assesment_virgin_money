package com.sujata.virginmoneydemo.data

import javax.inject.Inject

class PeoplesRepository @Inject constructor(private val peopleDataSource: PeopleDataSource) {
    suspend fun getPeoplesData()=peopleDataSource.getPeoplesData()
}