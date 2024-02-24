package com.example.homework23

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel (private val repository: Repository) : ViewModel() {
   private val _uiState = MutableLiveData<UIState>(UIState.NoData)
    val uiState: LiveData<UIState> = _uiState

    fun getData() {
        _uiState.postValue(UIState.Processing)
        viewModelScope.launch {
            val response = repository.getCurrencyByName("bitcoin")
            if (response.data != null) {
                _uiState.postValue(UIState.UpdatedData(response.data.type))
            } else {
                _uiState.postValue(UIState.Error)
            }
        }
    }

    sealed class UIState {
        object Processing: UIState()
        object NoData : UIState()
        object Error : UIState()
        class UpdatedData(val type: String) : UIState()
    }
}