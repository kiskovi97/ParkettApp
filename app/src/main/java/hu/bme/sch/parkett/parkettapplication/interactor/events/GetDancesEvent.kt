package hu.bme.sch.parkett.parkettapplication.interactor.events

import hu.bme.sch.parkett.parkettapplication.model.Dance

data class GetDancesEvent (
    var code: Int = 0,
    var danceList: List<Dance>? = null,
    var throwable: Throwable? = null
)