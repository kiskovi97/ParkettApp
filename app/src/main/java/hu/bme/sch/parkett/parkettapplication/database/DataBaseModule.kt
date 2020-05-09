package hu.bme.sch.parkett.parkettapplication.database

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDancesApi(client: OkHttpClient): DataBase {
        return DataBaseImpl()
    }
}