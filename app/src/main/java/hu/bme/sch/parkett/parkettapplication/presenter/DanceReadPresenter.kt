package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceEditScreen
import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceReadScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.model.Dance
import javax.inject.Inject

class DanceReadPresenter @Inject constructor(
        private val danceInteractor: DanceInteractor
) : Presenter<DanceReadScreen>() {
    override fun attachScreen(screen: DanceReadScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun showDance(id: Int) {
        val dance = danceInteractor.getDance(id)
        screen?.showDance(dance)
    }
}
