package hu.bme.sch.parkett.parkettapplication.network

import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import java.lang.Exception

interface DanceNetwork {
    @Throws(NetworkException::class)
    fun getDances(): List<Dance>

    @Throws(NetworkException::class)
    fun getDance(danceId: Int): Dance

    @Throws(NetworkException::class)
    fun getDanceTypes(): List<DanceType>
}