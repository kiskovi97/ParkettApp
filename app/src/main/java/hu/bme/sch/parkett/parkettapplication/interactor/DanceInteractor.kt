package hu.bme.sch.parkett.parkettapplication.interactor

import hu.bme.sch.parkett.parkettapplication.model.Dance

interface DanceInteractor {
    fun getDanceTypeList()

    fun getDanceList()

    fun getDance(id: Int)

    fun addDance(dance: Dance)

    fun deleteDance(id: Int)

    fun saveDance(dance: Dance)
}