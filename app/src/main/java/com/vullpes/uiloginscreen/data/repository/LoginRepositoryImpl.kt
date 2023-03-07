package com.vullpes.uiloginscreen.data.repository

import com.vullpes.uiloginscreen.data.room.LoginDao
import com.vullpes.uiloginscreen.data.util.Resource
import com.vullpes.uiloginscreen.domain.model.Login
import com.vullpes.uiloginscreen.domain.repository.LoginRepository
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginDao: LoginDao):LoginRepository {


    override suspend fun login(email: String, senha: String): Resource<Login> {
        return try {
           val login:Login? = loginDao.checarLogin(email,senha)

            if(login != null){
                Resource.Success(login)
            }else{
                Resource.Error("Usuário não encontrado")
            }
        }catch (e:Exception){
            Resource.Error(e.message)

        }
    }

    override suspend fun signUp(nome: String, email: String, senha: String): Resource<Login> {
        return try {
            val login:Login? = loginDao.checarLogin(email,senha)
            if(login != null){
                Resource.Success(login)
            }else{
                val saveLogin = Login(null,nome,email,senha)
                loginDao.insert(saveLogin)
                Resource.Success(loginDao.checarLogin(email,senha)!!)
            }
        }catch (e:Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun signUpDefaultUser(
        nome: String,
        usuario: String,
        senha: String
    ): Resource<Login> {
        return try {
            val login:Login? = loginDao.checarLogin(usuario,senha)
            if(login != null){
                Resource.Success(login)
            }else{
                val saveLogin = Login(null,nome,usuario,senha)
                loginDao.insert(saveLogin)
                Resource.Success(loginDao.checarLogin(usuario,senha)!!)
            }
        }catch (e:Exception){
            Resource.Error(e.message)
        }
    }
}