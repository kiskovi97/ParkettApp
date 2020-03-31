package hu.bme.sch.parkett.parkettapplication.framework.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_list, DanceListFragment.newInstance()).commit()

    }

    fun onClick(view: View) {
        val intent = Intent(this, DanceActivity::class.java).apply {
            putExtra(DanceActivity.DANCE_ID, 1)
        }
        startActivity(intent)
    }
}
