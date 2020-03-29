package hu.bme.sch.parkett.parkettapplication.framework.scenes

import hu.bme.sch.parkett.parkettapplication.model.Dance

interface DanceScreen {
    fun showDance(dance: Dance)
}