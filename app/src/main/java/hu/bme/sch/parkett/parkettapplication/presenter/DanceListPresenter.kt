package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceListScreen
import hu.bme.sch.parkett.parkettapplication.model.Dance

class DanceListPresenter: Presenter<DanceListScreen>() {

    override fun attachScreen(screen: DanceListScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun showDanceList(danceList: List<Dance>) {
        screen?.showDanceList(danceList)
    }
}