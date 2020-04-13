package hu.bme.sch.parkett.parkettapplication.di

import dagger.Component
import hu.bme.sch.parkett.parkettapplication.ParkettApplication
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceEditFragment
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceListFragment
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceReadFragment
import hu.bme.sch.parkett.parkettapplication.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component( modules = [
    ApplicationModule::class,
    NetworkModule::class
])
public interface AppComponent {
    fun inject(parkettApplication: ParkettApplication)
    fun inject(artistsFragment: DanceListFragment)
    fun inject(dancePresenter: DanceReadFragment)
    fun inject(dancePresenter: DanceEditFragment)
}