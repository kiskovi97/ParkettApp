package hu.bme.sch.parkett.parkettapplication.framework.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.sch.parkett.parkettapplication.R
import hu.bme.sch.parkett.parkettapplication.framework.activities.DanceActivity
import hu.bme.sch.parkett.parkettapplication.model.Dance
import kotlinx.android.synthetic.main.dance_list_item.view.*
import java.nio.ByteBuffer

class DanceAdapter(private val danceList: List<Dance>) : RecyclerView.Adapter<DanceAdapter.DanceViewHolder>() {
    class DanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName : TextView = itemView.item_name
        val layout : LinearLayout = itemView.itemLayout
        val image : ImageView = itemView.cardImageView
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

        holder.itemName.text = currentItem.name
        if (currentItem.dance_type != null) {
            holder.layout.setBackgroundColor(Color.parseColor(currentItem.dance_type?.color))

            if (currentItem.dance_type?.image != null) {
                val base64string = currentItem.dance_type?.image?.substring(22)

                if (base64string != null) {
                    val imageBytes = Base64.decode(base64string, Base64.DEFAULT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val imageByteBuffer = ByteBuffer.wrap(imageBytes)
                        val source = ImageDecoder.createSource(imageByteBuffer)
                        val bitmap = ImageDecoder.decodeDrawable(source)
                        holder.image.setImageDrawable(bitmap)
                    } else {
                        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        holder.image.setImageBitmap(decodedImage)
                    }
                }
            }
        }




        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DanceActivity::class.java).apply {
                putExtra(DanceActivity.DANCE_ID, currentItem.id)
                putExtra(DanceActivity.DANCE_COLOR, currentItem.dance_type?.color)
            }
            it.context.startActivity(intent)
        }
    }
}