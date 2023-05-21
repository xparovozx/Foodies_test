package com.example.test.di

import android.content.Context
import com.example.test.domain.api.ApiRepository
import com.example.test.domain.api.Api
import com.example.test.domain.usecases.GetCategoriesUseCase
import com.example.test.domain.usecases.GetProductsUseCase
import com.example.test.domain.usecases.GetTagsUseCase
import com.example.test.domain.usecases.UseCases
import com.example.test.viewmodel.TestApp
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun baseUrl() = "https://api.test.ru/".toHttpUrl()

    @Provides
    @Singleton
    fun provideApp(@ApplicationContext app: Context): TestApp {
        return app as TestApp
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, baseUrl: HttpUrl): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    fun provideApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)

    @Provides
    fun provideRetrofitRepository(api: Api) = ApiRepository(api)

    @Provides
    fun provideRemoteUseCases(
        app: TestApp
    ): UseCases = UseCases(
        getTagsUseCase = GetTagsUseCase(app),
        getCategoriesUseCase = GetCategoriesUseCase(app),
        getProductsUseCase = GetProductsUseCase(app)
    )
}