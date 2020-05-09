package hu.bme.sch.parkett.parkettapplication.database

import hu.bme.sch.parkett.parkettapplication.model.Dance

interface DataBase {
    fun save(dance: Dance)
}