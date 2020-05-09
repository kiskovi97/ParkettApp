package hu.bme.sch.parkett.parkettapplication.database

import android.util.Log
import hu.bme.sch.parkett.parkettapplication.model.Dance

class DataBaseImpl : DataBase {
    override fun save(dance: Dance) {
        dance.toDanceRecord().save()
    }
}