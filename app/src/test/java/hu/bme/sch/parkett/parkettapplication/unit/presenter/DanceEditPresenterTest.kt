package hu.bme.sch.parkett.parkettapplication.unit.presenter

import hu.bme.sch.parkett.parkettapplication.framework.scenes.DanceEditScreen
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceTypeListEvent
import hu.bme.sch.parkett.parkettapplication.mock.MockExecutor
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import hu.bme.sch.parkett.parkettapplication.presenter.DanceEditPresenter
import org.greenrobot.eventbus.EventBus
import org.junit.Test
import org.mockito.Mockito.*

class DanceEditPresenterTest {

    private val executor = MockExecutor()
    private val eventBus = mock(EventBus::class.java)
    private val interactor = mock(DanceInteractor::class.java)
    private val screen = mock(DanceEditScreen::class.java)

    @Test
    fun refreshDanceTypeList_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)

        //Act
        presenter.refreshDanceTypeList()

        //Assert
        verify(interactor).getDanceTypeList()
    }

    @Test
    fun showDance_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)

        //Act
        presenter.showDance(1)

        //Assert
        verify(interactor).getDance(1)
    }

    @Test
    fun saveDance_DanceAdded_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val dance = Dance(-1,null,null,null)

        //Act
        presenter.saveDance(dance)

        //Assert
        verify(interactor).addDance(dance)
    }

    @Test
    fun saveDance_DanceSaved_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val dance = Dance(2,null,null,null)

        //Act
        presenter.saveDance(dance)

        //Assert
        verify(interactor).saveDance(dance)
    }

    @Test
    fun deleteDance_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)

        //Act
        presenter.deleteDance(1)

        //Assert
        verify(interactor).deleteDance(1)
    }

    @Test
    fun attachScreen_NotRegistered_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        `when`(eventBus.isRegistered(presenter)).thenReturn(false)

        //Act
        presenter.attachScreen(screen)

        //Assert
        verify(eventBus).isRegistered(presenter)
        verify(eventBus).register(presenter)
    }

    @Test
    fun attachScreen_AlreadyRegistered_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        `when`(eventBus.isRegistered(presenter)).thenReturn(true)

        //Act
        presenter.attachScreen(screen)

        //Assert
        verify(eventBus).isRegistered(presenter)
        verifyNoMoreInteractions(eventBus)
    }

    @Test
    fun detachScreen_HappyPath() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)

        //Act
        presenter.detachScreen()

        //Assert
        verify(eventBus).unregister(presenter)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwble_printStackTrace() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val exception = mock(Throwable::class.java)
        val event = GetDanceEvent()
        event.throwable = exception

        //Act
        presenter.onEventMainThread(event)

        //Assert
        verify(exception).printStackTrace()
    }

    @Test
    fun onEventMainThread_GetDanceEvent_AttachScreen_throwbleNull_showDance() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val event = GetDanceEvent()
        val dance = Dance(2,null,null,null)
        event.throwable = null
        event.dance = dance

        //Act
        presenter.attachScreen(screen)
        presenter.onEventMainThread(event)

        //Assert
        verify(screen).showDance(dance)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwbleNull_doNothing() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val event = GetDanceEvent()
        val dance = Dance(2,null,null,null)
        event.throwable = null
        event.dance = dance

        //Act
        presenter.onEventMainThread(event)

        //Assert
        verifyZeroInteractions(screen)
    }

    @Test
    fun onEventMainThread_GetDanceEvent_throwbleAndDanceNull_doNothing() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val event = GetDanceEvent()
        event.throwable = null
        event.dance = null

        //Act
        presenter.onEventMainThread(event)

        //Assert
        verifyZeroInteractions(screen)
    }

    @Test
    fun onEventMainThread_GetDanceTypeListEvent_throwble_printStackTrace() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val exception = mock(Throwable::class.java)
        val event = GetDanceTypeListEvent()
        event.throwable = exception

        //Act
        presenter.onEventMainThread(event)

        //Assert
        verify(exception).printStackTrace()
    }

    @Test
    fun onEventMainThread_GetDanceTypeListEvent_AttachScreen_throwbleNull_showDance() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val event = GetDanceTypeListEvent()
        event.throwable = null
        event.danceTypeList = listOf()

        //Act
        presenter.attachScreen(screen)
        presenter.onEventMainThread(event)

        //Assert
        verify(screen).setDanceTypeList(listOf<DanceType>())
    }

    @Test
    fun onEventMainThread_GetDanceTypeListEvent_throwbleNull_doNothing() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val event = GetDanceTypeListEvent()
        val dance = Dance(2,null,null,null)
        event.throwable = null
        event.danceTypeList = listOf()

        //Act
        presenter.onEventMainThread(event)

        //Assert
        verifyZeroInteractions(screen)
    }

    @Test
    fun onEventMainThread_GetDanceTypeListEvent_throwbleAndDanceNull_doNothing() {
        //Arrange
        val presenter = DanceEditPresenter(executor, interactor, eventBus)
        val event = GetDanceTypeListEvent()
        event.throwable = null
        event.danceTypeList = null

        //Act
        presenter.onEventMainThread(event)

        //Assert
        verifyZeroInteractions(screen)
    }
}