package hu.bme.sch.parkett.parkettapplication.framework.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.google.firebase.analytics.FirebaseAnalytics
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceEditFragment
import hu.bme.sch.parkett.parkettapplication.framework.fragments.DanceReadFragment

class DanceActivity : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    private var selectedId: Int? = null
    private var edit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContentView(R.layout.activity_dance)
        val id = intent.getIntExtra(DANCE_ID, -1)
        if (id != -1) {
            val color = intent.getStringExtra(DANCE_COLOR)
            if (color != null) {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))
            }
            val name = intent.getStringExtra(DANCE_NAME)
            supportActionBar?.title = name
        } else {
            supportActionBar?.title = "New Dance"
        }
        
        selectedId = id
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (selectedId == -1) {
            edit = true
            supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceEditFragment.newInstance(id)).commit()
        } else {
            edit = false
            supportFragmentManager.beginTransaction().replace(R.id.fragment_dance_read, DanceReadFragment.newInstance(id)).commit()
        }

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "OnCreate")
        mFirebaseAnalytics?.logEvent("DanceActivity", bundle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.readview_menu, menu)

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "onCreateOptionsMenu")
        mFirebaseAnalytics?.logEvent("DanceActivity", bundle)

        if (selectedId == -1) {
            val item0 = menu?.getItem(0)
            item0?.isEnabled = false
            item0?.isVisible = false
            val item1 = menu?.getItem(1)
            item1?.isEnabled = false
            item1?.isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "onOptionsItemSelected")
        mFirebaseAnalytics?.logEvent("DanceActivity", bundle)

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

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CREATIVE_NAME, "delete")
        mFirebaseAnalytics?.logEvent("DanceActivity", bundle)

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
        public const val DANCE_COLOR = "DANCE_COLOR"
        public const val DANCE_NAME = "DANCE_NAME"
    }
}
