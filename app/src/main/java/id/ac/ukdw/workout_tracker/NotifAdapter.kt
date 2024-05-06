package id.ac.ukdw.workout_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ac.ukdw.workout_tracker.databinding.ActivityMainBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentItemNotifBinding

class NotifAdapter(private val notifList:ArrayList<NotifClass>)
    : RecyclerView.Adapter<NotifAdapter.NotifViewHolder>(){

    var onItemClick : ((NotifClass) -> Unit)? = null
    class NotifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val NotifItemTitle : TextView = itemView.findViewById(R.id.NotifitemTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_notif, parent, false)
        return NotifViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notifList.size
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        val notif = notifList[position]
        holder.NotifItemTitle.text = notif.Judul

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(notif)
        }
    }
}