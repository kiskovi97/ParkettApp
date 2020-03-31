package hu.bme.sch.parkett.parkettapplication.interactor

import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import javax.inject.Inject

class DanceInteractor @Inject constructor() {

    private val list = List<Dance>(1) {
        Dance(-1,"DName","Content", DanceType(-1, "DTName","010101","image"))
    }

    fun getDanceList(): List<Dance> {
        return list
    }

    fun getDance(id: Int): Dance? {
        if (id >= list.size) {
            return null
        }
        return list[id]
    }

    fun addDance(dance: Dance) {}

    fun deleteDance(id: Int?) {}

    fun saveDance(dance: Dance) {}
}