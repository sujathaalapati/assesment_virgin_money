package com.sujata.virginmoneydemo.framework

import android.util.Log
import com.sujata.virginmoneydemo.data.RoomsDataSource
import com.sujata.virginmoneydemo.domain.RoomsData
import com.sujata.virginmoneydemo.framework.api.PeoplesApiService
import com.sujata.virginmoneydemo.framework.api.dto.Rooms
import javax.inject.Inject

class RoomsAPIDataSource @Inject constructor(private val peoplesApiService: PeoplesApiService) :
    RoomsDataSource {

    override suspend fun getRoomsData(): List<RoomsData> {
        val roomsResponse = peoplesApiService.getRooms()
        val roomsData = mutableListOf<RoomsData>()
        if (roomsResponse.isSuccessful) {
            roomsResponse.body()?.run {
                Log.i(TAG, "Size " + this.size)
                roomsData.addAll(transformRoomData(this))
            }

        } else {
            println("$TAG : Response issue")
        }
        return roomsData
    }

    fun transformRoomData(rooms: Rooms) = rooms.map { RoomsData(it.createdAt, it.id, it.isOccupied, it.maxOccupancy) }

}