package hu.bme.sch.parkett.parkettapplication.unit

import hu.bme.sch.parkett.parkettapplication.database.DataBase
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceTypeListEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import hu.bme.sch.parkett.parkettapplication.network.DanceNetwork
import org.greenrobot.eventbus.EventBus
import org.junit.Test
import org.mockito.Mockito.*

class DanceInteractorTest {


    @Test
    fun addDance_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val interactor = DanceInteractor(mock(DanceNetwork::class.java), mockedDataBase, EventBus.getDefault())
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
        val interactor = DanceInteractor(mock(DanceNetwork::class.java), mockedDataBase, EventBus.getDefault())
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
        val interactor = DanceInteractor(mock(DanceNetwork::class.java), mockedDataBase, EventBus.getDefault())

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
        val interactor = DanceInteractor(mockedDanceNetwork, mockedDataBase, eventBus)
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
}