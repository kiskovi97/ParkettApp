package hu.bme.sch.parkett.parkettapplication.model

data class Dance(var id: Int, var name: String?, var content : String?, var dance_type: DanceType?) {
    fun toDanceRecord(): DanceRecord {
        var record = DanceRecord(name, content, dance_type?.toDanceTypeRecord())
        if (id != -1) {
            record.id =  id.toLong()
        }
        return record
    }
}