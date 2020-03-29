package parkett.sch.bme.hu.parkettapplication.framework.scenes

import parkett.sch.bme.hu.parkettapplication.model.Dance

interface DanceListScreen {
    fun showDanceList(result: List<Dance>)
}