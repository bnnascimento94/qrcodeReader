package com.vullpes.uiloginscreen.presentation.qrcodeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.usecases.SaveQrcodeUseCase
import com.vullpes.uiloginscreen.presentation.listQrcode.ListQrcodeState
import com.vullpes.uiloginscreen.presentation.listQrcode.ListQrcodeViewModel
import com.vullpes.uiloginscreen.presentation.login.LoginFormEvent
import com.vullpes.uiloginscreen.presentation.login.LoginState
import com.vullpes.uiloginscreen.presentation.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrcodeViewModel @Inject constructor(private val saveQrcodeUseCase: SaveQrcodeUseCase) :
    ViewModel() {

    var state by mutableStateOf(QrcodeState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()


    fun onEvent(event: QrcodeEvent) {
        when (event) {
            is QrcodeEvent.SaveQrcode -> {
                if(state.qrcode.isBlank()){
                    viewModelScope.launch(Dispatchers.IO){
                        validationEventChannel.send(ValidationEvent.Error("Bipe o Qrcode"))
                    }
                }else{
                    saveQrcode(state.qrcode)
                    state = state.copy(qrcode = "")

                }
            }
            is QrcodeEvent.QrcodeCaptured ->{
                state = state.copy(qrcode = event.qrcode)
            }
        }
    }

    private fun saveQrcode(qrcode: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = saveQrcodeUseCase.execute(qrcode)

        when (result) {
            is Resource.Loading -> {
                state = state.copy(
                    isloading = true
                )
            }
            is Resource.Error -> {
                state = state.copy(
                    isloading = false
                )
                validationEventChannel.send(ValidationEvent.Error(result.message))
            }
            is Resource.Success -> {
                state = state.copy(
                    isloading = false, qrcode = ""
                )
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }


    sealed class ValidationEvent {
        object Success : ValidationEvent()
        data class Error(val error: String?) : ValidationEvent()
    }

}