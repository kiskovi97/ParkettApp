package hu.bme.sch.parkett.parkettapplication.unit

import hu.bme.sch.parkett.parkettapplication.database.DataBase
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractorImpl
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceTypeListEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.network.DanceNetwork
import org.greenrobot.eventbus.EventBus
import org.junit.Test
import org.mockito.Mockito.*

class DanceInteractorImplTest {
    @Test
    fun addDance_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val interactor = DanceInteractorImpl(mock(DanceNetwork::class.java), mockedDataBase, EventBus.getDefault())
        val addedDance = Dance(-1, "MockDance",null,null)

        //Act
        interactor.addDance(addedDance)

        //Assert
        verify(mockedDataBase).save(addedDance)
    }

    @Test
    fun saveDance_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val interactor = DanceInteractorImpl(mock(DanceNetwork::class.java), mockedDataBase, EventBus.getDefault())
        val addedDance = Dance(-1, "MockDance",null,null)

        //Act
        interactor.saveDance(addedDance)

        //Assert
        verify(mockedDataBase).save(addedDance)
    }

    @Test
    fun deleteDance_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val interactor = DanceInteractorImpl(mock(DanceNetwork::class.java), mockedDataBase, EventBus.getDefault())

        //Act
        interactor.deleteDance(1)

        //Assert
        verify(mockedDataBase).delete(1)
    }

    @Test
    fun getDanceTypes_EmptyLists_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val mockedDanceNetwork = mock(DanceNetwork::class.java)
        val eventBus = spy(EventBus.getDefault())
        val interactor = DanceInteractorImpl(mockedDanceNetwork, mockedDataBase, eventBus)
        `when`(mockedDanceNetwork.getDanceTypes()).thenReturn(listOf())
        `when`(mockedDataBase.getAllDanceType()).thenReturn(listOf())

        //Act
        interactor.getDanceTypeList()

        //Assert
        verify(mockedDataBase).getAllDanceType()
        verify(mockedDanceNetwork).getDanceTypes()

        val outEvent = GetDanceTypeListEvent()
        outEvent.danceTypeList = listOf()
        outEvent.code = 200
        verify(eventBus).post(outEvent)
    }

    @Test
    fun getDances_EmptyLists_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val mockedDanceNetwork = mock(DanceNetwork::class.java)
        val eventBus = spy(EventBus.getDefault())
        val interactor = DanceInteractorImpl(mockedDanceNetwork, mockedDataBase, eventBus)
        `when`(mockedDanceNetwork.getDances()).thenReturn(listOf())
        `when`(mockedDataBase.getAllDance()).thenReturn(listOf())

        //Act
        interactor.getDanceList()

        //Assert
        verify(mockedDataBase).getAllDance()
        verify(mockedDanceNetwork).getDances()

        val outEvent = GetDancesEvent()
        outEvent.danceList = listOf()
        outEvent.code = 200
        verify(eventBus).post(outEvent)
    }

    @Test
    fun getDances_FormDatabase_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val mockedDanceNetwork = mock(DanceNetwork::class.java)
        val eventBus = spy(EventBus.getDefault())
        val interactor = DanceInteractorImpl(mockedDanceNetwork, mockedDataBase, eventBus)

        val dance = Dance(0,"Dance", null, null)
        val list = listOf<Dance>(dance)

        `when`(mockedDanceNetwork.getDances()).thenReturn(listOf())
        `when`(mockedDataBase.getAllDance()).thenReturn(list)

        //Act
        interactor.getDanceList()

        //Assert
        verify(mockedDataBase).getAllDance()
        verify(mockedDanceNetwork).getDances()

        val outEvent = GetDancesEvent()
        outEvent.danceList = list
        outEvent.code = 200
        verify(eventBus).post(outEvent)
    }

    @Test
    fun getDances_FormApi_HappyPathAndSaveToDatabase() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val mockedDanceNetwork = mock(DanceNetwork::class.java)
        val eventBus = spy(EventBus.getDefault())
        val interactor = DanceInteractorImpl(mockedDanceNetwork, mockedDataBase, eventBus)

        val dance = Dance(0,"Dance", null, null)
        val list = listOf<Dance>(dance)

        `when`(mockedDanceNetwork.getDances()).thenReturn(list)
        `when`(mockedDataBase.getAllDance()).thenReturn(list)

        //Act
        interactor.getDanceList()

        //Assert
        verify(mockedDataBase).getAllDance()
        verify(mockedDanceNetwork).getDances()
        verify(mockedDataBase).save(dance)

        val outEvent = GetDancesEvent()
        outEvent.danceList = list
        outEvent.code = 200
        verify(eventBus).post(outEvent)
    }

    @Test
    fun getDance_FromDataBase_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val mockedDanceNetwork = mock(DanceNetwork::class.java)
        val eventBus = spy(EventBus.getDefault())
        val interactor = DanceInteractorImpl(mockedDanceNetwork, mockedDataBase, eventBus)

        val dance = Dance(1,"name", null, null)
        `when`(mockedDataBase.getDance(1)).thenReturn(dance)

        //Act
        interactor.getDance(1)

        //Assert
        verify(mockedDataBase).getDance(1)
        verifyZeroInteractions(mockedDanceNetwork)

        val outEvent = GetDanceEvent()
        outEvent.dance = dance
        outEvent.code = 200
        verify(eventBus).post(outEvent)
    }

    @Test
    fun getDance_FromNetwork_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val mockedDanceNetwork = mock(DanceNetwork::class.java)
        val eventBus = spy(EventBus.getDefault())
        val interactor = DanceInteractorImpl(mockedDanceNetwork, mockedDataBase, eventBus)

        val dance = Dance(1,"name", null, null)
        `when`(mockedDataBase.getDance(1)).thenReturn(null)
        `when`(mockedDanceNetwork.getDance(1)).thenReturn(dance)

        //Act
        interactor.getDance(1)

        //Assert
        verify(mockedDataBase).getDance(1)
        verify(mockedDanceNetwork).getDance(1)

        val outEvent = GetDanceEvent()
        outEvent.dance = dance
        outEvent.code = 200
        verify(eventBus).post(outEvent)
    }
}