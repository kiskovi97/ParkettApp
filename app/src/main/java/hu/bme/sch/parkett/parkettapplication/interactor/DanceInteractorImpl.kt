package hu.bme.sch.parkett.parkettapplication.interactor

import android.util.Log
import hu.bme.sch.parkett.parkettapplication.database.DataBase
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDanceTypeListEvent
import hu.bme.sch.parkett.parkettapplication.interactor.events.GetDancesEvent
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.network.DanceNetwork
import hu.bme.sch.parkett.parkettapplication.network.NetworkException
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class DanceInteractorImpl @Inject constructor(private var danceNetwork: DanceNetwork, private var database: DataBase, private val eventBus: EventBus) : DanceInteractor {

    private var firstTime = true

    override fun getDanceTypeList() {
        val event = GetDanceTypeListEvent()
        try {
            refreshDanceTypesFromAPI()
            event.danceTypeList = database.getAllDanceType()
            event.code = 200
            eventBus.post(event)
        } catch (e: NetworkException) {
            event.throwable = e
            eventBus.post(event)
        }
    }

    override fun getDanceList(){
        val event = GetDancesEvent()
        try {
            refreshDancesFromAPI()
            event.danceList = database.getAllDance()
            event.code = 200
            eventBus.post(event)
        } catch (e: NetworkException) {
            event.throwable = e
            eventBus.post(event)
        }
    }

    private fun refreshDancesFromAPI() {
        if (!firstTime) return
        firstTime = false
        val dances = danceNetwork.getDances()
        for (dance in dances) {
            database.save(dance)
        }
    }

    private fun refreshDanceTypesFromAPI() {
        if (!firstTime) return
        firstTime = false
        val danceTypes = danceNetwork.getDanceTypes()
        for (danceType in danceTypes) {
            database.save(danceType)
        }
    }

    override fun getDance(id: Int) {
        val event = GetDanceEvent()
        try{
            var dance = database.getDance(id)
            if (dance == null) {
                val danceFromApi = danceNetwork.getDance(id)
                database.save(danceFromApi)
                dance = danceFromApi
            }
            event.dance = dance
            event.code = 200
            eventBus.post(event)
        } catch (e: NetworkException) {
            event.throwable = e
            eventBus.post(event)
        }

    }

    override fun addDance(dance: Dance) {
        Log.d("Adding Dance", dance.toString())
        database.save(dance)
    }

    override fun deleteDance(id: Int) {
        database.delete(id)
    }

    override fun saveDance(dance: Dance) {
        Log.d("Saving Dance",dance.toString())
        database.save(dance)
    }
}