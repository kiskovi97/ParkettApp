package hu.bme.sch.parkett.parkettapplication.interactor

import android.util.Log
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

    fun getDanceList(): List<Dance> {
        val event = GetDancesEvent()
        try {
            val daccesQueryCall = dancesApi.getArtists()
            val response = daccesQueryCall.execute()
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
        return list
    }

    fun getDance(id: Int): Dance? {
        if (id >= list.size) {
            return null
        }
        return list[id]
    }

    fun addDance(dance: Dance) {}

    fun deleteDance(id: Int?) {}

    fun saveDance(dance: Dance) {}
}