package hu.bme.sch.parkett.parkettapplication.model

import com.orm.SugarRecord

data class Dance(var id: Int, var name: String?, var content : String?, var danceType: DanceType?) {
    fun toDanceRecord(): DanceRecord {
        var record = DanceRecord(name, content, danceType?.toDanceTypeRecord())
        if (id != -1) {
            record.id =  id.toLong()
        }
        return record
    }
}