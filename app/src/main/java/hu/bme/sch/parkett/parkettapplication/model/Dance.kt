package hu.bme.sch.parkett.parkettapplication.model

import com.orm.SugarRecord

data class Dance(var id: Int, val name: String?, val content : String?, val danceType: DanceType?) {
    fun toDanceRecord(): DanceRecord {
        var record = DanceRecord(name, content, danceType?.toDanceTypeRecord())
        record.id = id.toLong()
        return record
    }
}