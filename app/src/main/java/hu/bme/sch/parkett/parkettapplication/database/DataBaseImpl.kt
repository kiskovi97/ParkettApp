package hu.bme.sch.parkett.parkettapplication.database

import android.util.Log
import com.orm.SugarRecord
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceRecord

class DataBaseImpl : DataBase {
    override fun save(dance: Dance) {
        dance.toDanceRecord().save()
    }

    override fun delete(id: Int) {
        val book: DanceRecord = SugarRecord.findById(DanceRecord::class.java, id)
        book.delete()
    }
}