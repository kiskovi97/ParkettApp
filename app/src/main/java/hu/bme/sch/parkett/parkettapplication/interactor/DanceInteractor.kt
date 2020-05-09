package hu.bme.sch.parkett.parkettapplication.interactor

import android.util.Log
import com.orm.SugarRecord
import com.orm.SugarRecord.findById
import hu.bme.sch.parkett.parkettapplication.database.DataBase
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceTypeListEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceRecord
import hu.bme.sch.parkett.parkettapplication.model.DanceTypeRecord
import hu.bme.sch.parkett.parkettapplication.network.DancesApi
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class DanceInteractor @Inject constructor(private var dancesApi: DancesApi, private var database: DataBase) {

    private var firstTime = true

    fun getDanceTypeList(){
        val event = GetDanceTypeListEvent()
        refreshDanceTypesFromAPI()
        try {
            val danceTypeRecordList = SugarRecord.listAll(DanceTypeRecord::class.java)
            event.danceTypeList = danceTypeRecordList.map { value -> value.toDanceType()  }
            event.code = 200
            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
        }
    }

    fun getDanceList(){
        val event = GetDancesEvent()
        refreshDancesFromAPI()
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

    private fun refreshDancesFromAPI() {
        if (!firstTime) return
        firstTime = false
        try {
            val queryCall = dancesApi.getDances()
            val response = queryCall.execute()
            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
            for (dance in response.body()!!) {
                val danceRecord = dance.toDanceRecord()
                danceRecord.dance_type?.save()
                danceRecord.save()
            }
        } catch (e: Exception) {
            Log.w("API Error",e)
        }
    }

    private fun refreshDanceTypesFromAPI() {
        if (!firstTime) return
        firstTime = false
        try {
            val queryCall = dancesApi.getDanceTypes()
            val response = queryCall.execute()
            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
            for (danceType in response.body()!!) {
                danceType.toDanceTypeRecord().save()
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
        database.save(dance)
    }

    fun deleteDance(id: Int?) {
        val book: DanceRecord = findById(DanceRecord::class.java, id)
        book.delete()
    }

    fun saveDance(dance: Dance) {
        Log.d("Saving Dance",dance.toString())
        database.save(dance)
    }
}