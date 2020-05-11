package hu.bme.sch.parkett.parkettapplication.framework.scenes

import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType

interface DanceEditScreen {
    fun showDance(dance: Dance?)
    fun setDanceTypeList(danceList: List<DanceType>)
}