package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import id.ac.ukdw.workout_tracker.databinding.FragmentLainnyaBinding
import id.ac.ukdw.workout_tracker.databinding.FragmentSettingBinding
import java.io.File

class fragmentLainnya : Fragment() {
    private var _binding : FragmentLainnyaBinding? = null
    private lateinit var currentUserUid: String
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private val binding get() = _binding!!
    private lateinit var user : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = FragmentLainnyaBinding.inflate(inflater, container, false)
            if (_binding != null){
                auth = FirebaseAuth.getInstance()
                if (auth != null){
                    currentUserUid = auth.currentUser?.uid.toString()
                    if (currentUserUid != null){
                        val storageRefe = FirebaseStorage.getInstance().getReference(currentUserUid+"/profil")

                        storageRefe.downloadUrl.addOnSuccessListener { uri ->
                            // Gunakan Picasso untuk memuat gambar dari URL
                            Picasso.get()
                                .load(uri)
                                .placeholder(R.drawable.app_profil) // Gambar placeholder saat gambar sedang dimuat
                                .error(R.drawable.app_profil)       // Gambar jika terjadi kesalahan
                                .into(binding.imgFotoUser)            // ImageView di mana gambar akan dimuat
                        }.addOnFailureListener {
                            // Tangani kesalahan jika tidak dapat mendapatkan URL
                            binding.imgFotoUser.setImageResource(R.drawable.app_profil)
                        }

                        databaseRef = FirebaseDatabase.getInstance().getReference("users")
                        databaseRef.child(currentUserUid).get().addOnSuccessListener {
                            if (it.exists()){
                                var nama = it.child("nama").value
                                var abs = it.child("abs").value
                                var arm = it.child("arm").value
                                var chest = it.child("chest").value

                                binding.txtNama.text = nama.toString()
                                binding.txtJumlahABS.text = abs.toString()
                                binding.txtJumlahARM.text = arm.toString()
                                binding.txtJumlahChest.text = chest.toString()

                                val absValue = (abs?.toString()?.toIntOrNull() ?: 0)
                                val armValue = (arm?.toString()?.toIntOrNull() ?: 0)
                                val chestValue = (chest?.toString()?.toIntOrNull() ?: 0)

                                val total = absValue*80 + armValue*80 + chestValue*100
                                binding.txtKalori.text = "Total memakar hinggal $total Kalori!!!"

                            }
                        }


                    }
                }
            }

            binding.btnSetting.setOnClickListener{
                removeFragment(requireActivity().supportFragmentManager.findFragmentById(R.id.main_activity)!!)
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentSetting()).commit()
            }
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun removeFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().remove(fragment).commit()
    }
}

