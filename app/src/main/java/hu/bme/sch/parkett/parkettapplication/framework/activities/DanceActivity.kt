package hu.bme.sch.parkett.parkettapplication.framework.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceEditFragment
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceReadFragment
import kotlinx.android.synthetic.main.activity_dance.*

class DanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dance)
        val id = intent.getIntExtra(DANCE_ID, -1)
        dance_activity_debug.text = id.toString()

        supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceReadFragment.newInstance(id)).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_edit, DanceEditFragment.newInstance(id)).commit()
    }


    companion object {
        public const val DANCE_ID = "DANCE_ID"
    }
}
