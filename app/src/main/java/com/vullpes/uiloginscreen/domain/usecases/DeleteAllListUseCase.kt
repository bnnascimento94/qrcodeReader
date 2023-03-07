package com.vullpes.uiloginscreen.domain.usecases

import com.vullpes.uiloginscreen.domain.repository.QrcodeRepository
import javax.inject.Inject

class DeleteAllListUseCase @Inject constructor(private val qrcodeRepository: QrcodeRepository) {

    suspend fun execute() = qrcodeRepository.deleteAllQrcodes()


}