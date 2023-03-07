package com.vullpes.uiloginscreen.domain.usecases

import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.model.Login
import com.vullpes.uiloginscreen.domain.repository.LoginRepository
import javax.inject.Inject

class UserRegisterUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun registerNewUser(nome:String, email:String, senha:String): Resource<Login> = loginRepository.signUp(nome, email,senha)

}