package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.ac.ukdw.workout_tracker.databinding.FragmentResepMakananBinding

class fragmentResepMakanan : Fragment() {
    private lateinit var makananAdapter: MakananAdapter
    private var makananList: ArrayList<MakananClass> = arrayListOf()
    private var _binding : FragmentResepMakananBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResepMakananBinding.inflate(inflater, container, false)
        binding.recyclerViewMakanan.layoutManager = LinearLayoutManager(context)

        makananAdapter = MakananAdapter(makananList)
        binding.recyclerViewMakanan.adapter = makananAdapter
        fetchMakananData()


        binding.btnBack.setOnClickListener{
            Intent(getContext(), MainActivity::class.java).also {
                it.putExtra("fragmentType", "FragmentHome")
                it.putExtra("selectedItemId", R.id.btnHome)
                it.putExtra("selectedItemIdd", R.id.btnHome)
                startActivity(it)
            }
        }

        makananAdapter.onItemClick = {
            val fragment = fragmentDetailMakanan()
            val args = Bundle().apply {
                putParcelable("makanan", it)
            }
            fragment.arguments = args
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.makanan_activity, fragment).commit()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class FirebaseStorageHelper {
        private val storage = FirebaseStorage.getInstance()
        private val storageRef = storage.reference.child("makanan")

        fun getAllImagePaths(callback: (List<StorageReference>) -> Unit) {
            storageRef.listAll().addOnSuccessListener { listResult ->
                callback(listResult.items)
            }.addOnFailureListener {
                callback(emptyList())
            }
        }

        fun getImageUrl(imageRef: StorageReference, callback: (String?) -> Unit) {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString())
            }.addOnFailureListener {
                callback(null)
            }
        }
    }
    private fun fetchMakananData() {
        val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("makanan")
        databaseRef.get().addOnSuccessListener { snapshot ->
            for (makananSnapshot in snapshot.children) {
                val bahan = makananSnapshot.child("Bahan").getValue(String::class.java) ?: ""
                val imgUri = makananSnapshot.child("imgUri").getValue(String::class.java) ?: ""
                val cara = makananSnapshot.child("Cara").getValue(String::class.java) ?: ""
                val judul = makananSnapshot.child("Judul").getValue(String::class.java) ?: ""

                val formattedCara = formatStepsText(cara)
                val formattedBahan = formatStepsText(bahan)

                val makanan = MakananClass(judul, formattedBahan, formattedCara, imgUri)
                makananList.add(makanan)
            }
            // Update RecyclerView after fetching data
            makananAdapter.notifyDataSetChanged()
        }.addOnFailureListener {
            // Handle error
        }
    }

    private fun formatStepsText(steps: String): String {
        // Replace '\n' with '\n\n' to add a blank line between steps
        return steps.replace("\\n".toRegex(), "\n")
    }

}

