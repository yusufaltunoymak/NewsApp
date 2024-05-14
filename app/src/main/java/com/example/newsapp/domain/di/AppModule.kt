package com.example.newsapp.domain.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.remote.NewsAPI
import com.example.newsapp.data.repository.ConnectivityRepository
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpLoggerInterceptor() : HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logger
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor : HttpLoggingInterceptor) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideApi(client : OkHttpClient) : NewsAPI = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    object RepositoryModule {
        @Provides
        fun provideNewsRepository(@ApplicationContext context : Context): NewsRepository {
            return NewsRepositoryImpl(provideApi(client = provideOkHttpClient(
                provideHttpLoggerInterceptor()
            )), provideNewsDao(provideNewsDatabase(context))
            )
        }
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context : Context) : NewsDatabase {
        return Room.databaseBuilder(context, NewsDatabase::class.java,"news_db")
            .build()
    }

    @Provides
    fun provideNewsDao(database: NewsDatabase): NewsDao {
        return database.newsDao()
    }

    @Singleton
    @Provides
    fun provideConnectivityRepository(@ApplicationContext context : Context): ConnectivityRepository {
        return ConnectivityRepository(context)
    }
}