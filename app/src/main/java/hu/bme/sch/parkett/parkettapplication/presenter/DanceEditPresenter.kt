package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceEditScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class DanceEditPresenter @Inject constructor(
        private val danceInteractor: DanceInteractor
) : Presenter<DanceEditScreen>() {
    override fun attachScreen(screen: DanceEditScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun showDance(id: Int) {
        val dance = danceInteractor.getDance(id)
        screen?.showDance(dance)
    }

    fun saveDance(dance: Dance) {
        if (dance.id < 0) {
            danceInteractor.addDance(dance)
        } else {
            danceInteractor.saveDance(dance)
        }
    }

    fun deleteDance(id: Int) {
        danceInteractor.deleteDance(id)
    }
}