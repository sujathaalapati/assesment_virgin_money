package com.sujata.virginmoneydemo.framework

import android.util.Log
import com.sujata.virginmoneydemo.data.PeopleDataSource
import com.sujata.virginmoneydemo.domain.PeoplesData
import com.sujata.virginmoneydemo.framework.api.PeoplesApiService
import com.sujata.virginmoneydemo.framework.api.dto.Peoples
import javax.inject.Inject

const val TAG = "PeoplesAPIDataSource"

class PeoplesAPIDataSource @Inject constructor(private val peoplesApiService: PeoplesApiService) : PeopleDataSource {
    override suspend fun getPeoplesData(): List<PeoplesData> {
        val peoplesResponse = peoplesApiService.getPeoples()
        val peoplesData = mutableListOf<PeoplesData>()
        if (peoplesResponse.isSuccessful) {
            peoplesResponse.body()?.run {
                peoplesData.addAll(transformPeoplesList(this))
            }
        } else {
            println("$TAG : Response issue")
        }
        return peoplesData
    }

     fun transformPeoplesList(peoples: Peoples) = peoples.map {
        PeoplesData(it.avatar, it.firstName, it.jobtitle, it.lastName, it.email, it.favouriteColor)
    }


}