package hu.bme.sch.parkett.parkettapplication.interactor

import android.util.Log
import com.orm.SugarRecord
import com.orm.SugarRecord.findById
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceRecord
import hu.bme.sch.parkett.parkettapplication.network.DancesApi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class DanceInteractor @Inject constructor(private var dancesApi: DancesApi) {

    private var firstTime = true

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
        if (!firstTime) return
        firstTime = false
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
        var danceRecord = findById(DanceRecord::class.java, id)
        Log.d("Help", danceRecord?.name + "_"+danceRecord?.id)
        if (danceRecord == null) {
            val dance = getDanceFromAPI(id)
            danceRecord = dance?.toDanceRecord()
            danceRecord?.save()
        }
        Log.d("Help", danceRecord?.name + "_"+danceRecord?.id)
        event.dance = danceRecord?.toDance()
        event.code = 200
        EventBus.getDefault().post(event)
    }

    private fun getDanceFromAPI(id: Int): Dance? {
        try {
            val queryCall = dancesApi.getDance(id)
            val response = queryCall.execute()
            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
            return response.body()
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
        return null
    }

    fun addDance(dance: Dance) {
        Log.d("Adding Dance", dance.toString())
        dance.toDanceRecord().save()
    }

    fun deleteDance(id: Int?) {
        val book: DanceRecord = findById(DanceRecord::class.java, id)
        book.delete()
    }

    fun saveDance(dance: Dance) {
        Log.d("Help", dance.name + "_"+dance.id)
        dance.toDanceRecord().save()
    }
}