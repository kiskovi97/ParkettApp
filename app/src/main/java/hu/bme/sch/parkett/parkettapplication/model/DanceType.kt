package hu.bme.sch.parkett.parkettapplication.model

import com.orm.SugarRecord

data class DanceType(val id: Int, val name: String?, val color: String?, val image: String?) {
    fun toDanceTypeRecord(): DanceTypeRecord {
        var record = DanceTypeRecord(name, color, image)
        record.id = id.toLong()
        return record
    }
}

