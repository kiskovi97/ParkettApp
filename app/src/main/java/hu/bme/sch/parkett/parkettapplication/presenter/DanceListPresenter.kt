package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceListScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class DanceListPresenter @Inject constructor(
        private val executor: Executor,
        private val danceInteractor: DanceInteractor,
        private val eventBus: EventBus
) : Presenter<DanceListScreen>() {

    override fun attachScreen(screen: DanceListScreen) {
        super.attachScreen(screen)
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this)
        }
    }

    override fun detachScreen() {
        eventBus.unregister(this)
        super.detachScreen()
    }

    fun refreshDanceList() {
        executor.execute {
            danceInteractor.getDanceList()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetDancesEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
        } else {

            if (screen != null) {
                if (event.danceList != null) {
                    screen?.showDanceList(event.danceList as MutableList<Dance>)
                }

            }
        }
    }
}