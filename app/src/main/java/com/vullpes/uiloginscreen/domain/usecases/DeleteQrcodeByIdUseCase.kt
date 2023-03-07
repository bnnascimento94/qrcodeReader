package com.vullpes.uiloginscreen.domain.usecases

import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.repository.QrcodeRepository
import javax.inject.Inject

class DeleteQrcodeByIdUseCase @Inject constructor(private val qrcodeRepository: QrcodeRepository) {
    suspend fun execute(id: Int): Resource<Boolean> = qrcodeRepository.deleteQrcode(id)
}