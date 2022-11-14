package com.sujata.virginmoneydemo.ui.peoples

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sujata.virginmoneydemo.data.PeoplesRepository
import com.sujata.virginmoneydemo.domain.PeoplesData
import com.sujata.virginmoneydemo.framework.api.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG="PeoplesViewModel"

@HiltViewModel
class PeoplesViewModel @Inject constructor(val peoplesRepository: PeoplesRepository): ViewModel() {
    private val _peoplesDataResponse=MutableLiveData<Resource<List<PeoplesData>>>()
    val peoplesDataResponse:MutableLiveData<Resource<List<PeoplesData>>> get()=_peoplesDataResponse

    fun fetchPeoplesData(){
        _peoplesDataResponse.postValue(Resource.loading(null))
        viewModelScope.launch {
            val peoplesData=peoplesRepository.getPeoplesData()
            if (peoplesData.isNotEmpty()){
                _peoplesDataResponse.postValue(Resource.success(peoplesData))
            }else{
                _peoplesDataResponse.postValue(Resource.error(null,"Something went wrong"))
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is Peoples Fragment"
    }
    val text: LiveData<String> = _text
}