package hu.bme.sch.parkett.parkettapplication.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceEditScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceTypeListEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executor
import javax.inject.Inject

class DanceEditPresenter @Inject constructor(
        private val executor: Executor,
        private val danceInteractor: DanceInteractor,
        private val eventBus: EventBus
) : Presenter<DanceEditScreen>() {
    override fun attachScreen(screen: DanceEditScreen) {
        super.attachScreen(screen)
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this)
        }
    }

    override fun detachScreen() {
        eventBus.unregister(this)
        super.detachScreen()
    }

    fun refreshDanceTypeList() {
        executor.execute {
            danceInteractor.getDanceTypeList()
        }
    }

    fun showDance(id: Int) {
        executor.execute {
            danceInteractor.getDance(id)
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: GetDanceTypeListEvent) {
        if (event.throwable != null) {
            event.throwable?.printStackTrace()
        } else {

            if (screen != null) {
                if (event.danceTypeList != null) {
                    screen?.setDanceTypeList(event.danceTypeList!!)
                }

            }
        }
    }
}