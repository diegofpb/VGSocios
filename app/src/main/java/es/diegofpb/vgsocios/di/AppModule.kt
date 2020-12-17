package es.diegofpb.vgsocios.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import es.diegofpb.vgsocios.data.remote.ApiManager
import es.diegofpb.vgsocios.data.remote.ApiManagerImpl
import es.diegofpb.vgsocios.data.remote.ApiService
import es.diegofpb.vgsocios.utils.Constants.BASE_URL
import es.diegofpb.vgsocios.utils.Constants.SHARED_PREFERENCES_NAME
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiManager(apiManagerImpl: ApiManagerImpl): ApiManager = apiManagerImpl

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

}