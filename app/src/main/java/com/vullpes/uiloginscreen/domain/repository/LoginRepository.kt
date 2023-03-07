package com.vullpes.uiloginscreen.domain.repository

import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.model.Login

interface LoginRepository {

    suspend fun login(email:String, senha:String): Resource<Login>

    suspend fun signUp(nome:String, usuario:String,senha:String): Resource<Login>


    suspend fun signUpDefaultUser(nome:String, usuario:String,senha:String): Resource<Login>

}