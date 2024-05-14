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
                        val  localfile = File.createTempFile("profil", "jpg")
                        if (storageRefe != null && localfile != null){
                            storageRefe.getFile(localfile).addOnSuccessListener {
                                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                                if (bitmap != null){
                                    binding.imgFotoUser.setImageBitmap(bitmap)
                                }else {
                                    binding.imgFotoUser.setImageResource(R.drawable.app_profil)
                                }
                            }
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

                            }
                        }
//                        var userRef = databaseRef.child(currentUserUid)
//                        if (databaseRef != null && userRef != null){
//                            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    var namaa = snapshot.child("nama").getValue(String::class.java)
//                                    var abs = snapshot.child("abs").getValue(String::class.java)
//                                    var arm = snapshot.child("arm").getValue(String::class.java)
//                                    var chest = snapshot.child("chest").getValue(String::class.java)
//                                    if (namaa != null) {
//                                        user = User(namaa)
//                                        if (user.getNama() != null){
//                                            binding.txtNama.text = user.getNama()
////                                            binding.txtJumlahABS.text = abs
////                                            binding.txtJumlahARM.text = arm
////                                            binding.txtJumlahChest.text = chest
//                                        }else {
//                                            binding.txtNama.text = "Nama Pengguna"
//                                        }
//                                    } else {
//                                        binding.txtNama.text = "Nama Pengguna"
//                                    }
//                                }
//                                override fun onCancelled(error: DatabaseError) {
//                                    // Handle onCancelled event
//                                    Toast.makeText(requireContext(), "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
//                                }
//                            })
//                        }

                    }
                }
            }
            // Inflate the layout for this fragment

            binding.btnSetting.setOnClickListener{
//                Intent(requireContext(), TestActivity::class.java).also {
//                    startActivity(it)
//                }
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

