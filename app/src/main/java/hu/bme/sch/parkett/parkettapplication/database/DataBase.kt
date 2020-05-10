package hu.bme.sch.parkett.parkettapplication.database

import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import hu.bme.sch.parkett.parkettapplication.model.DanceTypeRecord

interface DataBase {
    fun save(dance: Dance?)

    fun save(danceType: DanceType)

    fun delete(id: Int)

    fun getAllDanceType() : List<DanceType>

    fun getAllDance() : List<Dance>

    fun getDance(id: Int) : Dance?
}