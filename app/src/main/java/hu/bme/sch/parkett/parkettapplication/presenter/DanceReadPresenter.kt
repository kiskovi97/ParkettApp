package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceEditScreen
import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceReadScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class DanceReadPresenter @Inject constructor(
        private val executor: Executor,
        private val danceInteractor: DanceInteractor
) : Presenter<DanceReadScreen>() {
    override fun attachScreen(screen: DanceReadScreen) {
        super.attachScreen(screen)
        EventBus.getDefault().register(this)
    }

    override fun detachScreen() {
        EventBus.getDefault().unregister(this)
        super.detachScreen()
    }

    fun showDance(id: Int) {
        executor.execute {
            danceInteractor.getDance(id)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetDanceEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
        } else {

            if (screen != null) {
                if (event.dance != null) {
                    screen?.showDance(event.dance)
                }

            }
        }
    }
}
