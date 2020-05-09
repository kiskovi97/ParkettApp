package hu.bme.sch.parkett.parkettapplication.network

import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import javax.inject.Inject

class DanceNetworkImpl @Inject constructor(private var dancesApi: DancesApi): DanceNetwork {
    override fun getDances(): List<Dance> {
        val queryCall = dancesApi.getDances()
        val response = queryCall.execute()
        if (response.code() != 200) {
            throw Exception("Result code is not 200")
        }
        if (response.body() != null) {
            return response.body()!!
        }
        throw Exception("Body is empty")
    }

    override fun getDance(danceId: Int): Dance {
        val queryCall = dancesApi.getDance(danceId)
        val response = queryCall.execute()
        if (response.code() != 200) {
            throw Exception("Result code is not 200")
        }
        if (response.body() != null) {
            return response.body()!!
        }
        throw Exception("Body is empty")
    }

    override fun getDanceTypes(): List<DanceType> {
        val queryCall = dancesApi.getDanceTypes()
        val response = queryCall.execute()
        if (response.code() != 200) {
            throw Exception("Result code is not 200")
        }
        if (response.body() != null) {
            return response.body()!!
        }
        throw Exception("Body is empty")
    }
}