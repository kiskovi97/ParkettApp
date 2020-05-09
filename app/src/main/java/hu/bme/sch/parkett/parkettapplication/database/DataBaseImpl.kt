package hu.bme.sch.parkett.parkettapplication.database

import android.util.Log
import com.orm.SugarRecord
import hu.bme.sch.parkett.parkettapplication.model.Dance
import hu.bme.sch.parkett.parkettapplication.model.DanceRecord
import hu.bme.sch.parkett.parkettapplication.model.DanceType
import hu.bme.sch.parkett.parkettapplication.model.DanceTypeRecord

class DataBaseImpl : DataBase {
    override fun save(dance: Dance?) {
        if (dance == null) return
        dance.toDanceRecord().save()
        if (dance.dance_type != null) {
            dance.dance_type!!.toDanceTypeRecord().save()
        }
    }

    override fun save(danceType: DanceType) {
        danceType.toDanceTypeRecord().save()
    }

    override fun delete(id: Int) {
        val book: DanceRecord = SugarRecord.findById(DanceRecord::class.java, id)
        book.delete()
    }

    override fun getAllDanceType(): List<DanceType> {
        val list = SugarRecord.listAll(DanceTypeRecord::class.java)
        return list.map { value -> value.toDanceType()  }
    }

    override fun getAllDance(): List<Dance> {
        val list = SugarRecord.listAll(DanceRecord::class.java)
        return list.map { value -> value.toDance()  }
    }

    override fun getDance(id: Int): Dance? {
        val danceRecord = SugarRecord.findById(DanceRecord::class.java, id)
        return danceRecord?.toDance()
    }
}