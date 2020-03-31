package hu.bme.sch.parkett.parkettapplication.di

import android.content.Context
import dagger.Module
import dagger.Provides
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import javax.inject.Singleton

@Module
class ApplicationModule(
        @get:Provides @Singleton val context: Context
) {

    @Provides
    @Singleton
    fun provideDanceInteractor() = DanceInteractor()
}
