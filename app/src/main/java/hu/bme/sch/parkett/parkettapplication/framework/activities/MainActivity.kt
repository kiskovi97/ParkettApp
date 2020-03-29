package hu.bme.sch.parkett.parkettapplication.framework.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import parkett.sch.bme.hu.parkettapplication.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
