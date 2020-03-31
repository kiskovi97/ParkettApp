package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceListScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import javax.inject.Inject

class DanceListPresenter @Inject constructor(
        private val danceInteractor: DanceInteractor
) : Presenter<DanceListScreen>() {

    override fun attachScreen(screen: DanceListScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun refreshDanceList() {
        val danceList = danceInteractor.getDanceList()
        screen?.showDanceList(danceList)
    }
}