package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceScreen
import hu.bme.sch.parkett.parkettapplication.model.Dance

class DancePresenter: Presenter<DanceScreen>() {
    override fun attachScreen(screen: DanceScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun showDanceList(dance: Dance) {
        screen?.showDance(dance)
    }
}