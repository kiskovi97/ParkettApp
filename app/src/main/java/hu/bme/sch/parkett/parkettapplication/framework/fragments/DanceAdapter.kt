package hu.bme.sch.parkett.parkettapplication.framework.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.activities.DanceActivity
import hu.bme.sch.parkett.parkettapplication.model.Dance
import kotlinx.android.synthetic.main.dance_list_item.view.*

class DanceAdapter(private val danceList: List<Dance>) : RecyclerView.Adapter<DanceAdapter.DanceViewHolder>() {
    class DanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName : TextView = itemView.item_name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DanceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.dance_list_item, parent, false)
        return DanceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return danceList.count()
    }

    override fun onBindViewHolder(holder: DanceViewHolder, position: Int) {
        val currentItem = danceList[position]
        holder.itemName.text = currentItem.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DanceActivity::class.java).apply {
                putExtra(DanceActivity.DANCE_ID, currentItem.id)
            }
            it.context.startActivity(intent)
        }
    }
}