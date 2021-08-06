package com.example.retrofitep.presentation.detailcompany

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitep.base.event.Event
import com.example.retrofitep.domain.companies.DetailCompany
import com.example.retrofitep.domain.companies.GetDetailCompany
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailCompanyViewModel(
    private val id: Long,
    private val getDetailCompany: GetDetailCompany
) : ViewModel() {

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _detailCompanyLiveData = MutableLiveData<DetailCompany>()
    val detailCompanyLiveData: LiveData<DetailCompany> = _detailCompanyLiveData

    private val _errorLiveData = MutableLiveData<Event<String?>>()
    val errorLiveData: LiveData<Event<String?>> = _errorLiveData

    init {
        getDetailCompanyById()
    }

    private fun getDetailCompanyById() {
        emitLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val detailCompany = getDetailCompany.execute(GetDetailCompany.Params(id))
                emitItems(detailCompany)
            } catch (t: Throwable) {
                Timber.e(t)
                emitError(t)
            }
            emitLoading(false)
        }
    }

    private fun emitLoading(isLoading: Boolean) {
        _loadingLiveData.postValue(isLoading)
    }

    private fun emitItems(detailCompany: DetailCompany) {
        _detailCompanyLiveData.postValue(detailCompany)
    }

    private fun emitError(t: Throwable) {
        _errorLiveData.postValue(Event(t.message))
    }

}
