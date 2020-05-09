package hu.bme.sch.parkett.parkettapplication.unit

import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.orm.SugarContext
import com.orm.SugarRecord
import hu.bme.sch.parkett.parkettapplication.database.DataBase
import hu.bme.sch.parkett.parkettapplication.interactor.DanceInteractor
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.network.DancesApi
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DanceInteractorTest {


    @Test
    fun addDance_HappyPath() {
        //Arrange
        val mockedDataBase = mock(DataBase::class.java)
        val interactor = DanceInteractor(mock(DancesApi::class.java), mockedDataBase)
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
        val interactor = DanceInteractor(mock(DancesApi::class.java), mockedDataBase)
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
        val interactor = DanceInteractor(mock(DancesApi::class.java), mockedDataBase)
        //Act
        interactor.deleteDance(1)

        //Assert
        verify(mockedDataBase).delete(1)
    }
}