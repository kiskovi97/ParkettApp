package hu.bme.sch.parkett.parkettapplication.unit.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceListScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.mock.MockExecutor
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.presenter.DanceListPresenter
import hu.bme.sch.parkett.parkettapplication.presenter.DanceReadPresenter
import org.greenrobot.eventbus.EventBus
import org.junit.Test
import org.mockito.Mockito

class DanceListPresenterTest {
    private val executor = MockExecutor()
    private val eventBus = Mockito.mock(EventBus::class.java)
    private val interactor = Mockito.mock(DanceInteractor::class.java)
    private val screen = Mockito.mock(DanceListScreen::class.java)

    @Test
    fun showDance_HappyPath() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)

        //Act
        presenter.refreshDanceList()

        //Assert
        Mockito.verify(interactor).getDanceList()
    }

    @Test
    fun attachScreen_NotRegistered_HappyPath() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)
        Mockito.`when`(eventBus.isRegistered(presenter)).thenReturn(false)

        //Act
        presenter.attachScreen(screen)

        //Assert
        Mockito.verify(eventBus).isRegistered(presenter)
        Mockito.verify(eventBus).register(presenter)
    }

    @Test
    fun attachScreen_AlreadyRegistered_HappyPath() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)
        Mockito.`when`(eventBus.isRegistered(presenter)).thenReturn(true)

        //Act
        presenter.attachScreen(screen)

        //Assert
        Mockito.verify(eventBus).isRegistered(presenter)
        Mockito.verifyNoMoreInteractions(eventBus)
    }

    @Test
    fun detachScreen_HappyPath() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)

        //Act
        presenter.detachScreen()

        //Assert
        Mockito.verify(eventBus).unregister(presenter)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwble_printStackTrace() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)
        val exception = Mockito.mock(Throwable::class.java)
        val event = GetDancesEvent()
        event.throwable = exception

        //Act
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verify(exception).printStackTrace()
    }

    @Test
    fun onEventMainThread_GetDanceEvent_AttachScreen_throwbleNull_showDance() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)
        val event = GetDancesEvent()
        val dance = Dance(2,null,null,null)
        event.throwable = null
        event.danceList = mutableListOf()

        //Act
        presenter.attachScreen(screen)
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verify(screen).showDanceList(listOf())
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwbleNull_doNothing() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)
        val event = GetDancesEvent()
        val dance = Dance(2,null,null,null)
        event.throwable = null
        event.danceList = listOf()

        //Act
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verifyZeroInteractions(screen)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwbleAndDanceNull_doNothing() {
        //Arrange
        val presenter = DanceListPresenter(executor, interactor, eventBus)
        val event = GetDancesEvent()
        event.throwable = null
        event.danceList = null

        //Act
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verifyZeroInteractions(screen)
    }
}