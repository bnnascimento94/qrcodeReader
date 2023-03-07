package com.vullpes.uiloginscreen.domain.usecases

import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.model.SavedQrcode
import com.vullpes.uiloginscreen.domain.repository.QrcodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ListAllQrcodesUseCase @Inject constructor(private val qrcodeRepository: QrcodeRepository) {
    operator fun invoke(): Flow<List<SavedQrcode>> = qrcodeRepository.listAllQrcodes()


}