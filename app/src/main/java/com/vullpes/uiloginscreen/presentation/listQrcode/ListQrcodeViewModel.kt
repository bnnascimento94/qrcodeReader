package com.vullpes.uiloginscreen.presentation.listQrcode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.usecases.DeleteAllListUseCase
import com.vullpes.uiloginscreen.domain.usecases.DeleteQrcodeByIdUseCase
import com.vullpes.uiloginscreen.domain.usecases.ListAllQrcodesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListQrcodeViewModel @Inject constructor(
    private val listAllQrcodesUseCase: ListAllQrcodesUseCase,
    private val deleteAllListUseCase: DeleteAllListUseCase,
    private val deleteQrcodeByIdUseCase: DeleteQrcodeByIdUseCase
) : ViewModel() {

    var state  by mutableStateOf(ListQrcodeState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    init {
        getListQrcodes()
    }

    fun onEvent(event: ListFormEvent) {
        when (event) {
            is ListFormEvent.DeleteQrcodeById -> {
                deleteById(event.id)

            }
            is ListFormEvent.DeleteAllQrcodes -> {
                deleteAll()
            }
        }
    }

    private fun deleteById(id: Int?) = viewModelScope.launch(Dispatchers.IO) {
       val result = deleteQrcodeByIdUseCase.execute(id!!)

        when(result){
            is Resource.Loading ->{
                state = state.copy(isloading = true)
            }
            is Resource.Error ->{
                state = state.copy(isloading = false)
                validationEventChannel.send(ValidationEvent.Error(result.message))
            }
            is Resource.Success ->{
                state = state.copy(isloading = false)
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    private fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
       val result =  deleteAllListUseCase.execute();
        when(result){
            is Resource.Loading ->{
                state = state.copy(isloading = true)
            }
            is Resource.Error ->{
                state = state.copy(isloading = false)
                validationEventChannel.send(ValidationEvent.Error(result.message))
            }
            is Resource.Success ->{
                state = state.copy(isloading = false)
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }


    private fun getListQrcodes()  {
        listAllQrcodesUseCase().onEach { result ->
            state = state.copy(qrcodes = result)
        }.launchIn(viewModelScope)
    }


    sealed class ValidationEvent {
        object Success: ValidationEvent()
        data class Error(val error: String?):ValidationEvent()
    }


}

