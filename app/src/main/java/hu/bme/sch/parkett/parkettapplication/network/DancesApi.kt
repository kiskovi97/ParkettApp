package hu.bme.sch.parkett.parkettapplication.network
import hu.bme.sch.parkett.parkettapplication.model.Dance
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface DancesApi {
    @GET("dances")
    fun getArtists(): Call<List<Dance>>
}