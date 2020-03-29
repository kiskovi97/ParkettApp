package parkett.sch.bme.hu.parkettapplication.presenter

import parkett.sch.bme.hu.parkettapplication.framework.scenes.DanceListScreen
import parkett.sch.bme.hu.parkettapplication.model.Dance

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