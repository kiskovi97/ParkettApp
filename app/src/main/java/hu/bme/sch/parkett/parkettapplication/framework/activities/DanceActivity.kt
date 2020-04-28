package hu.bme.sch.parkett.parkettapplication.framework.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceEditFragment
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceReadFragment
import kotlinx.android.synthetic.main.activity_dance.*

class DanceActivity : AppCompatActivity() {

    var selectedId: Int? = null
    var edit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dance)
        val id = intent.getIntExtra(DANCE_ID, -1)
        dance_activity_debug.text = id.toString()
        selectedId = id
        supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceReadFragment.newInstance(id)).commit()
        edit = false
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.readview_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteIcon -> {

            }
            R.id.editIcon -> {
                if (selectedId != null) {
                    if (edit) {
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceReadFragment.newInstance(selectedId!!)).commit()
                        item.icon = ContextCompat.getDrawable(this, R.drawable.ic_edit_white_24dp)
                        edit = false
                    } else {
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceEditFragment.newInstance(selectedId!!)).commit()
                        item.icon = ContextCompat.getDrawable(this, R.drawable.ic_remove_red_eye_whit_24dp)
                        edit = true
                    }

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {
        public const val DANCE_ID = "DANCE_ID"
    }
}
