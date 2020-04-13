package hu.bme.sch.parkett.parkettapplication.model

import com.orm.SugarRecord

data class DanceTypeRecord(val name: String?, val color: String?, val image: String?): SugarRecord() {
    constructor() : this(null,null,null) {}
    fun toDanceType(): DanceType {
        return DanceType(id.toInt(), name, color, image)
    }
}