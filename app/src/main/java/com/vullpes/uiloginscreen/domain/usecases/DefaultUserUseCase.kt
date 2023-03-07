package com.vullpes.uiloginscreen.domain.usecases

import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.model.Login
import com.vullpes.uiloginscreen.domain.repository.LoginRepository
import javax.inject.Inject

class DefaultUserUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun execute(): Resource<Login> = loginRepository.signUpDefaultUser("default","admin", "123Mudar")
}