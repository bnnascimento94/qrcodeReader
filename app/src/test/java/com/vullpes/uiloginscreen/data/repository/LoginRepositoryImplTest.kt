package com.vullpes.uiloginscreen.data.repository


import com.vullpes.uiloginscreen.data.room.LoginDao
import com.vullpes.uiloginscreen.domain.model.Login
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryImplTest {

    @Mock
    lateinit var loginDao: LoginDao

    lateinit var loginRepositoryImpl: LoginRepositoryImpl


    @Before
    fun setup() {
        loginRepositoryImpl = LoginRepositoryImpl(loginDao)
    }

    @Test
    fun `test response when log user`() = runBlocking{
        `when`(loginDao.checarLogin("bnnascimento94@gmail.com", "123456")).thenReturn(
            Login(
                1,
                "Bruno",
                "bnnascimento94@gmail.com",
                "123456"
            )
        )

        val result = loginRepositoryImpl.login("bnnascimento94@gmail.com", "123456")

        assertThat(result.data , `is`(notNullValue()))
        assertThat(result.data!!.nome, `is`("Bruno"))
    }

    @Test
    fun `test when register a new user`() = runBlocking {
        `when`(loginDao.checarLogin("bnnascimento94@gmail.com", "123456")).thenReturn(
            Login(
                1,
                "Bruno",
                "bnnascimento94@gmail.com",
                "123456"
            )
        )

        val result = loginRepositoryImpl.signUp("Bruno","bnnascimento94@gmail.com","123456")

        assertThat(result.data , `is`(notNullValue()))
        assertThat(result.data!!.nome, `is`("Bruno"))

    }


}