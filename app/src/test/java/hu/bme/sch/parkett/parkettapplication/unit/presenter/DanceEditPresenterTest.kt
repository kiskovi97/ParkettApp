package hu.bme.sch.parkett.parkettapplication.unit.presenter

import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.mock.MockExecutor
import hu.bme.sch.parkett.parkettapplication.presenter.DanceEditPresenter
import org.junit.Test
import org.mockito.Mockito.*

class DanceEditPresenterTest {

    private val executor = MockExecutor()

    @Test
    fun refreshDanceTypeList_HappyPath() {
        //Arrange
        val interactor = mock(DanceInteractor::class.java)
        val presenter = DanceEditPresenter(executor, interactor)

        //Act
        presenter.refreshDanceTypeList()

        //Assert
        verify(interactor).getDanceTypeList()
    }
}