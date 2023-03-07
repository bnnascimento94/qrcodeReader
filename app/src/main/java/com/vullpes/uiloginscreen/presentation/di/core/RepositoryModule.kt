package com.vullpes.uiloginscreen.presentation.di.core

import com.vullpes.uiloginscreen.data.repository.LoginRepositoryImpl
import com.vullpes.uiloginscreen.data.repository.QrcodeRepositoryImpl
import com.vullpes.uiloginscreen.data.room.RoomService
import com.vullpes.uiloginscreen.domain.repository.LoginRepository
import com.vullpes.uiloginscreen.domain.repository.QrcodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesLoginRepository(roomService: RoomService): LoginRepository{
        return LoginRepositoryImpl(roomService.loginDao())
    }

    @Provides
    @Singleton
    fun providesQrcodeRepository(roomService: RoomService): QrcodeRepository{
        return QrcodeRepositoryImpl(roomService.savedQrcodeDao())
    }

}