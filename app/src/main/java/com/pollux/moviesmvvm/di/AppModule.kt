package com.pollux.moviesmvvm.di

import android.app.Application
import androidx.room.Room
import com.pollux.moviesmvvm.model.data.MoviesDatabase
import com.pollux.moviesmvvm.model.network.MovieApiService
import com.pollux.moviesmvvm.utils.C
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(C.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("X-Platform", "Android")
                    builder.header("X-App-Version", "1.0")
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }.build()

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoviesDatabase =
        Room.databaseBuilder(app, MoviesDatabase::class.java, "movies_database")
            .fallbackToDestructiveMigration()
            .build()

}