package hu.bme.sch.parkett.parkettapplication.di

import android.app.Activity
import androidx.fragment.app.Fragment
import hu.bme.sch.parkett.parkettapplication.ParkettApplication

val Activity.injector: AppComponent
    get() {
        return (this.applicationContext as ParkettApplication).injector
    }

val Fragment.injector: AppComponent
    get() {
        return (this.context!!.applicationContext as ParkettApplication).injector
    }