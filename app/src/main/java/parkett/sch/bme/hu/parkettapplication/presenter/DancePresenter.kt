package parkett.sch.bme.hu.parkettapplication.presenter

import parkett.sch.bme.hu.parkettapplication.framework.scenes.DanceScreen
import parkett.sch.bme.hu.parkettapplication.model.Dance

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