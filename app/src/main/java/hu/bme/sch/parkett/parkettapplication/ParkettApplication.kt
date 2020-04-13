package hu.bme.sch.parkett.parkettapplication

import android.app.Application
import com.orm.SugarContext
import hu.bme.sch.parkett.parkettapplication.di.AppComponent
import hu.bme.sch.parkett.parkettapplication.di.ApplicationModule
import hu.bme.sch.parkett.parkettapplication.di.DaggerAppComponent

class ParkettApplication : Application(){

    lateinit var injector: AppComponent

    override fun onCreate() {
        super.onCreate()
        SugarContext.init(this)
        injector = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onTerminate() {
        SugarContext.terminate()
        super.onTerminate()
    }
}