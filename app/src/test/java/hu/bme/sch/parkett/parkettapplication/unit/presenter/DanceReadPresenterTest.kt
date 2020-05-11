package hu.bme.sch.parkett.parkettapplication.unit.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceReadScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.mock.MockExecutor
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.presenter.DanceReadPresenter
import org.greenrobot.eventbus.EventBus
import org.junit.Test
import org.mockito.Mockito

class DanceReadPresenterTest {
    private val executor = MockExecutor()
    private val eventBus = Mockito.mock(EventBus::class.java)
    private val interactor = Mockito.mock(DanceInteractor::class.java)
    private val screen = Mockito.mock(DanceReadScreen::class.java)

    @Test
    fun showDance_HappyPath() {
        //Arrange
        val presenter = DanceReadPresenter(executor, interactor, eventBus)

        //Act
        presenter.showDance(1)

        //Assert
        Mockito.verify(interactor).getDance(1)
    }

    @Test
    fun deleteDance_HappyPath() {
        //Arrange
        val presenter = DanceReadPresenter(executor, interactor, eventBus)

        //Act
        presenter.deleteDance(1)

        //Assert
        Mockito.verify(interactor).deleteDance(1)
    }

    @Test
    fun attachScreen_NotRegistered_HappyPath() {
        //Arrange
        val presenter = DanceReadPresenter(executor, interactor, eventBus)
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
        val presenter = DanceReadPresenter(executor, interactor, eventBus)
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
        val presenter = DanceReadPresenter(executor, interactor, eventBus)

        //Act
        presenter.detachScreen()

        //Assert
        Mockito.verify(eventBus).unregister(presenter)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwble_printStackTrace() {
        //Arrange
        val presenter = DanceReadPresenter(executor, interactor, eventBus)
        val exception = Mockito.mock(Throwable::class.java)
        val event = GetDanceEvent()
        event.throwable = exception

        //Act
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verify(exception).printStackTrace()
    }

    @Test
    fun onEventMainThread_GetDanceEvent_AttachScreen_throwbleNull_showDance() {
        //Arrange
        val presenter = DanceReadPresenter(executor, interactor, eventBus)
        val event = GetDanceEvent()
        val dance = Dance(2,null,null,null)
        event.throwable = null
        event.dance = dance

        //Act
        presenter.attachScreen(screen)
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verify(screen).showDance(dance)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwbleNull_doNothing() {
        //Arrange
        val presenter = DanceReadPresenter(executor, interactor, eventBus)
        val event = GetDanceEvent()
        val dance = Dance(2,null,null,null)
        event.throwable = null
        event.dance = dance

        //Act
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verifyZeroInteractions(screen)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwbleAndDanceNull_doNothing() {
        //Arrange
        val presenter = DanceReadPresenter(executor, interactor, eventBus)
        val event = GetDanceEvent()
        event.throwable = null
        event.dance = null

        //Act
        presenter.onEventMainThread(event)

        //Assert
        Mockito.verifyZeroInteractions(screen)
    }
}