package hu.bme.sch.parkett.parkettapplication.interactor

import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import hu.bme.sch.parkett.parkettapplication.network.DancesApi
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus

class DanceInteractor @Inject constructor(private var dancesApi: DancesApi) {

    private val list = List<Dance>(1) {
        Dance(-1,"DName","Content", DanceType(-1, "DTName","010101","image"))
    }

    fun getDanceList(){
        val event = GetDancesEvent()
        try {
            val queryCall = dancesApi.getDances()
            val response = queryCall.execute()
            if (response.code() != 200) {
                throw Exception("Result code is not 200")
            }
            event.code = response.code()
            event.danceList = response.body();
            EventBus.getDefault().post(event)
        } catch (e: Exception) {
            event.throwable = e
            EventBus.getDefault().post(event)
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