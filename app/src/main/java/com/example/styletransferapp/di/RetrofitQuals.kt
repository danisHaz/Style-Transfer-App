package com.example.styletransferapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitBaseUrl

@Qualifier
@Retention
annotation class RetrofitApiUrl