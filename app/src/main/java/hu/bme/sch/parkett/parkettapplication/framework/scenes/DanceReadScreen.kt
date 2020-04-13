package hu.bme.sch.parkett.parkettapplication.framework.scenes

import hu.bme.sch.parkett.parkettapplication.model.Dance

interface DanceReadScreen {
    fun showDance(dance: Dance?)
}