package com.vullpes.uiloginscreen.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vullpes.uiloginscreen.data.room.LoginDao
import com.vullpes.uiloginscreen.data.room.RoomService
import com.vullpes.uiloginscreen.domain.model.Login
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginDaoImplTest {

    private lateinit var dataBase: RoomService
    private lateinit var loginDao: LoginDao

    //será executado antes dos testes
    @Before
    fun setupDatabase(){
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RoomService::class.java
        ).allowMainThreadQueries().build()

        loginDao = dataBase.loginDao()
    }

    //rodará após os testes
    @After
    fun closeDatabase(){
        dataBase.close()
    }

    @Test
    fun `test to sign up user`() = runBlocking {
        val login = Login(nome = "Bruno Nunes", email = "bnnascimento94@gmail.com", senha = "123456")
        loginDao.insert(login)

        val loginInserted = loginDao.checarLogin("bnnascimento94@gmail.com", "123456")

        MatcherAssert.assertThat(loginInserted, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        MatcherAssert.assertThat(login.nome, CoreMatchers.`is`(loginInserted!!.nome))
    }

    @Test
    fun `test to login user`() = runBlocking {
        val login = Login(nome = "Bruno Nunes", email = "bnnascimento94@gmail.com", senha = "123456")
        loginDao.insert(login)

        val loginInserted = loginDao.checarLogin("bnnascimento94@gmail.com", "123456")
        MatcherAssert.assertThat(loginInserted, CoreMatchers.`is`(CoreMatchers.notNullValue()))
    }

}