package hu.bme.sch.parkett.parkettapplication.di

import android.content.Context
import dagger.Module
import dagger.Provides
import hu.bme.sch.parkett.parkettapplication.database.DataBase
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractorImpl
import hu.bme.sch.parkett.parkettapplication.network.DanceNetwork
import hu.bme.sch.parkett.parkettapplication.presenter.DanceEditPresenter
import hu.bme.sch.parkett.parkettapplication.presenter.DanceListPresenter
import hu.bme.sch.parkett.parkettapplication.presenter.DanceReadPresenter
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class ApplicationModule(
        @get:Provides @Singleton val context: Context
) {

    @Provides
    @Singleton
    fun provideDanceInteractor(danceNetwork: DanceNetwork, database: DataBase) : DanceInteractor = DanceInteractorImpl(danceNetwork, database, EventBus.getDefault())

    @Provides
    @Singleton
    fun danceEditPresenter(executor: Executor, danceInteractor: DanceInteractor) = DanceEditPresenter(executor, danceInteractor, EventBus.getDefault())

    @Provides
    @Singleton
    fun danceReadPresenter(executor: Executor, danceInteractor: DanceInteractor) = DanceReadPresenter(executor, danceInteractor, EventBus.getDefault())

    @Provides
    @Singleton
    fun danceListPresenter(executor: Executor, danceInteractor: DanceInteractor) = DanceListPresenter(executor, danceInteractor, EventBus.getDefault())

    @Provides
    @Singleton
    fun networkExecutor(): Executor = Executors.newFixedThreadPool(1)
}
