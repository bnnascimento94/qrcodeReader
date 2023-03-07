package com.vullpes.uiloginscreen.presentation.di.core

import com.vullpes.uiloginscreen.domain.validations.ValidadeEmail
import com.vullpes.uiloginscreen.domain.validations.ValidateConfirmPassword
import com.vullpes.uiloginscreen.domain.validations.ValidatePassword
import com.vullpes.uiloginscreen.domain.validations.ValidateUsername
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ValidationsModule {


    @Provides
    @Singleton
    fun providesValidationEmail(): ValidadeEmail {
        return ValidadeEmail()
    }

    @Provides
    @Singleton
    fun providesConfirmPassword(): ValidateConfirmPassword{
        return ValidateConfirmPassword()
    }

    @Provides
    @Singleton
    fun providesValidatePassword(): ValidatePassword{
        return ValidatePassword()
    }

    @Provides
    @Singleton
    fun providesValidateUsername(): ValidateUsername{
        return ValidateUsername()
    }





}