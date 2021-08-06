package com.example.retrofitep.presentation.companies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitep.base.event.Event
import com.example.retrofitep.domain.companies.Company
import com.example.retrofitep.domain.companies.GetCompanies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class CompaniesViewModel(
    private val getCompanies: GetCompanies
) : ViewModel() {

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private val _companiesLiveData = MutableLiveData<List<Company>>()
    val companiesLiveData: LiveData<List<Company>> = _companiesLiveData

    private val _errorLiveData = MutableLiveData<Event<String?>>()
    val errorLiveData: LiveData<Event<String?>> = _errorLiveData

    init {
        loadItems()
    }

    private fun loadItems() {
        emitLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = getCompanies.execute()
                emitItems(items)
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

    private fun emitItems(items: List<Company>) {
        _companiesLiveData.postValue(items)
    }

    private fun emitError(t: Throwable) {
        _errorLiveData.postValue(Event(t.message))
    }

}
