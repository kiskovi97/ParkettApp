package hu.bme.sch.parkett.parkettapplication.framework.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.model.DanceType

class DanceTypeSpinnerAdapter(context: Context, resource: Int, val objects: MutableList<DanceType>) : ArrayAdapter<DanceType>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        var outView = convertView ?: LayoutInflater.from(context).inflate(R.layout.dance_type_spinner_item, parent, false)
        val textView1 = outView.findViewById<TextView>(R.id.spinner_text1)
        val selected = objects[position]
        textView1?.text = selected.name
        return outView
    }
}