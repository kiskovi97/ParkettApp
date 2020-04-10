package hu.bme.sch.parkett.parkettapplication.interactor

import android.util.Log
import com.orm.SugarRecord
import com.orm.SugarRecord.listAll
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceRecord
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import hu.bme.sch.parkett.parkettapplication.model.DanceTypeRecord
import hu.bme.sch.parkett.parkettapplication.network.DancesApi
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus

class DanceInteractor @Inject constructor(private var dancesApi: DancesApi) {

    fun getDanceList(){
        val event = GetDancesEvent()
        refreshFromAPI()
        try {
            val danceRecordList = SugarRecord.listAll(DanceRecord::class.java)
            event.danceList = danceRecordList.map { value -> value.toDance()  }
            event.code = 200
            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }

    private fun refreshFromAPI() {
        try {
            val queryCall = dancesApi.getDances()
            val response = queryCall.execute()
            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
            for (dance in response.body()!!) {
                dance.toDanceRecord().save()
            }
        } catch (e: Exception) {
            Log.w("API Error",e)
        }
    }

    fun getDance(id: Int) {
        val event = GetDanceEvent()
        try {
            val queryCall = dancesApi.getDance(id)
            val response = queryCall.execute()
            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
            event.code = response.code()
            event.dance = response.body();
            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }

    fun addDance(dance: Dance) {}

    fun deleteDance(id: Int?) {}

    fun saveDance(dance: Dance) {}
}