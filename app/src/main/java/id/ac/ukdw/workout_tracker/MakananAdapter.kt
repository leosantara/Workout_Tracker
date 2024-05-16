package id.ac.ukdw.workout_tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Calendar

class MakananAdapter(private val makananList:ArrayList<MakananClass>)
    : RecyclerView.Adapter<MakananAdapter.MakananViewHolder>(){

    var onItemClick : ((MakananClass) -> Unit)? = null

    class MakananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val MakananItemJudul : TextView = itemView.findViewById(R.id.btnJudulMakanan)
        val MakananItemImage : ImageView = itemView.findViewById(R.id.btnFotoMakanan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_makanan, parent, false)
        return MakananViewHolder(view)
    }

    override fun getItemCount(): Int {
        return makananList.size
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val makanaan = makananList[position]
        holder.MakananItemJudul.text = makanaan.Judul
        Picasso.get().load(makanaan.Foto).into(holder.MakananItemImage)


//        Glide.with(holder.itemView.context).load(makanaan.Foto+"/"+makanaan.Judul).into(holder.MakananItemImage)
//        val storageRefe = FirebaseStorage.getInstance().getReference("Makanan/")
////        holder.MakananItemImage.setImageResource(makanaan.Foto)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(makanaan)
        }
    }

}