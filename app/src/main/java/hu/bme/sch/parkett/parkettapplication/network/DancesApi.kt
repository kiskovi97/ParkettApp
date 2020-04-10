package hu.bme.sch.parkett.parkettapplication.network
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface DancesApi {
    @GET("dances")
    fun getDances(): Call<List<Dance>>

    @GET("dances/{danceId}")
    fun getDance(@Path("danceId") danceId: Int): Call<Dance>

    @GET("dance_types")
    fun getDanceTypes(): Call<List<DanceType>>
}