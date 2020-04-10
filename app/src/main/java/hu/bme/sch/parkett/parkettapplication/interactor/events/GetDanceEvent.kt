package hu.bme.sch.parkett.parkettapplication.interactor.events

import hu.bme.sch.parkett.parkettapplication.model.Dance

data class GetDanceEvent (
        var code: Int = 0,
        var dance: Dance? = null,
        var throwable: Throwable? = null
)