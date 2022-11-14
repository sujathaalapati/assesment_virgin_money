package com.sujata.virginmoneydemo.ui.rooms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujata.virginmoneydemo.data.RoomsRepository
import com.sujata.virginmoneydemo.domain.RoomsData
import com.sujata.virginmoneydemo.framework.api.Resource
import com.sujata.virginmoneydemo.ui.peoples.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(val roomsRepository: RoomsRepository) : ViewModel() {

    private val _roomsDataResponse =MutableLiveData<Resource<List<RoomsData>>>()
    val roomsDataResponse:LiveData<Resource<List<RoomsData>>> get() = _roomsDataResponse

    init{

    }

    fun fetchRoomsData(){
        _roomsDataResponse.postValue(Resource.loading(null))
        viewModelScope.launch {
            val roomsData=roomsRepository.getRoomsData()
            Log.i(TAG,"Size"+roomsData.size)
            if (roomsData.isNotEmpty()){
                Log.i(TAG,"Size in VM"+roomsData.size)
                _roomsDataResponse.postValue(Resource.success(roomsData))
            }else{
                _roomsDataResponse.postValue(Resource.error(null,"Something went wrong"))
            }
        }
    }
}