package hu.bme.sch.parkett.parkettapplication.network

import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType

interface DanceNetwork {
    fun getDances(): List<Dance>

    fun getDance(danceId: Int): Dance

    fun getDanceTypes(): List<DanceType>
}