package com.vullpes.uiloginscreen.presentation.di.core

import com.vullpes.uiloginscreen.domain.repository.LoginRepository
import com.vullpes.uiloginscreen.domain.repository.QrcodeRepository
import com.vullpes.uiloginscreen.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsecaseModule {

    @Provides
    @Singleton
    fun providesDeleteAllListUseCase(qrcodeRepository: QrcodeRepository): DeleteAllListUseCase {
        return DeleteAllListUseCase(qrcodeRepository)
    }

    @Provides
    @Singleton
    fun providesDeleteQrcodeByIdUseCase(qrcodeRepository: QrcodeRepository): DeleteQrcodeByIdUseCase {
        return DeleteQrcodeByIdUseCase(qrcodeRepository)
    }

    @Provides
    @Singleton
    fun providesListAllQrcodesUseCase(qrcodeRepository: QrcodeRepository): ListAllQrcodesUseCase {
        return ListAllQrcodesUseCase(qrcodeRepository)
    }

    @Provides
    @Singleton
    fun providesLogInUseCase(loginRepository: LoginRepository): LogInUseCase {
        return LogInUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun providesDefaultUseCase(loginRepository: LoginRepository): DefaultUserUseCase {
        return DefaultUserUseCase(loginRepository)
    }

    @Provides
    @Singleton
    fun providesSaveQrcodeUseCase(qrcodeRepository: QrcodeRepository): SaveQrcodeUseCase {
        return SaveQrcodeUseCase(qrcodeRepository)
    }


}