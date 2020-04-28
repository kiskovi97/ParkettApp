package hu.bme.sch.parkett.parkettapplication.framework.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceEditFragment
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceReadFragment
import hu.bme.sch.parkett.parkettapplication.presenter.DanceEditPresenter
import kotlinx.android.synthetic.main.activity_dance.*
import okhttp3.internal.wait
import javax.inject.Inject

class DanceActivity : AppCompatActivity() {

    var selectedId: Int? = null
    var edit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dance)
        val id = intent.getIntExtra(DANCE_ID, -1)
        dance_activity_debug.text = id.toString()
        selectedId = id
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (selectedId == -1) {
            edit = true
            supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceEditFragment.newInstance(id)).commit()
        } else {
            edit = false
            supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceReadFragment.newInstance(id)).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.readview_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteIcon -> {
                delete()
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

    private fun delete() {
        if (edit) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_dance_read) as DanceEditFragment
            fragment.delete()
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_dance_read) as DanceReadFragment
            fragment.delete()
        }
    }


    companion object {
        public const val DANCE_ID = "DANCE_ID"
    }
}
