package hu.bme.sch.parkett.parkettapplication.framework.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceListFragment


class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_list, DanceListFragment.newInstance()).commit()

        mFirebaseAnalytics?.logEvent("OnCreate", Bundle())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mFirebaseAnalytics?.logEvent("onOptionsItemSelected", Bundle())
        when (item.itemId) {
            R.id.addIcon -> {
                val intent = Intent(this, DanceActivity::class.java).apply {
                    putExtra(DanceActivity.DANCE_ID, -1)
                }
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mFirebaseAnalytics?.logEvent("onCreateOptionsMenu", Bundle())
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
