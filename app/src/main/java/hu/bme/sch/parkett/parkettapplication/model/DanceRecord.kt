package hu.bme.sch.parkett.parkettapplication.model

import com.orm.SugarRecord

data class DanceRecord(val name: String?, val content : String?, val danceType: DanceTypeRecord?) : SugarRecord() {
    constructor() : this(null,null,null) {}
    fun toDance(): Dance {
        return Dance(id.toInt(), name, content, danceType?.toDanceType())
    }
}