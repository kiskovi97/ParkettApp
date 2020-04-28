package hu.bme.sch.parkett.parkettapplication.interactor.events

import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType

data class GetDanceTypeListEvent (
        var code: Int = 0,
        var danceTypeList: List<DanceType>? = null,
        var throwable: Throwable? = null
)