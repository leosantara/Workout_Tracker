package id.ac.ukdw.workout_tracker

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import id.ac.ukdw.workout_tracker.databinding.FragmentTambahMakananBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class fragmentTambahMakanan : Fragment() {
    private lateinit var _binding : FragmentTambahMakananBinding
    private lateinit var databaseRef: DatabaseReference
    private val binding get() = _binding!!
    private val REQUEST_IMAGE_CAPTURE = 1
    private var makananList: ArrayList<MakananClass> = arrayListOf()
    private lateinit var ImageUri: Uri
    var namaResep : String = ""
    var Bahan : String = ""
    var Cara : String = ""

    override fun onCreateView(



        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTambahMakananBinding.inflate(inflater, container, false)

        binding.btnGaleri.setOnClickListener {
            selectImage()
        }

        binding.btnTambahResep.setOnClickListener {
            namaResep = binding.txtJudulMakanan.text.toString()
            Bahan = binding.txtBahanMakanan.text.toString()
            Cara = binding.txtCaraPembuatan.text.toString()

            if (namaResep.isEmpty()){
                binding.txtJudulMakanan.error = "Nama Resep Harus Diisi"
                binding.txtJudulMakanan.requestFocus()
                return@setOnClickListener
            }

            if (Bahan.isEmpty()){
                binding.txtBahanMakanan.error = "Bahan-bahan harus Diisi"
                binding.txtBahanMakanan.requestFocus()
                return@setOnClickListener
            }

            if (Cara.isEmpty()){
                binding.txtCaraPembuatan.error = "Cara Memasak Harus Diisi"
                binding.txtCaraPembuatan.requestFocus()
                return@setOnClickListener
            }
            if (isImageViewDrawableEqualToResource(binding.imgFotoUser, R.drawable.app_siluet_makanan)) {
                Toast.makeText(context, "Foto masakan harus di tambahkan", Toast.LENGTH_SHORT).show()
                binding.imgFotoUser.requestFocus()
                return@setOnClickListener
            }

            uploadImage()
        }

        binding.btnBack.setOnClickListener {
            Intent(getContext(), MainActivity::class.java).also {
                it.putExtra("fragmentType", "FragmentHome")
                startActivity(it)
            }
        }

        binding.btnKamera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        setupEditTextListeners()
        return(binding.root)
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Mengupdate Data...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val databaseRef = FirebaseDatabase.getInstance().getReference("makanan")
        // Menentukan indeks berikutnya dengan mendapatkan jumlah item di database
        databaseRef.get().addOnSuccessListener { snapshot ->
            val nextIndex = snapshot.childrenCount // Mendapatkan jumlah item sebagai indeks berikutnya
            val storagePath = "makanan/$nextIndex/judul"
            val storageRef = FirebaseStorage.getInstance().getReference(storagePath)

            storageRef.putFile(ImageUri).addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    val formattedCara = formatStepsText(Cara)
                    val formattedBahan = formatStepsText(Bahan)

                    val makanan = MakananClass(namaResep, formattedBahan, formattedCara, imageUrl)
                    this.databaseRef = FirebaseDatabase.getInstance().getReference("makanan")
                    val userRef = databaseRef.child(nextIndex.toString())
                    makananList.add(makanan)
                    val userData = hashMapOf<String, Any>(
                        "Bahan" to formattedBahan,
                        "Cara" to formattedCara,
                        "Judul" to namaResep,
                        "imgUri" to imageUrl
                    )
                    userRef.updateChildren(userData)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Berhasil Menambah Resep", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Gagal Menambah Resep", Toast.LENGTH_SHORT).show()
                        }
                    Intent(getContext(), MainActivity::class.java).also {
                        it.putExtra("fragmentType", "FragmentHome")
                        startActivity(it)
                    }
                    // Menyimpan URL gambar ke database
                }.addOnFailureListener {
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(requireActivity(), "Gagal Mendapatkan URL Foto", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(requireActivity(), "Gagal Mengganti Foto", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(requireActivity(), "Gagal Mendapatkan Data Makanan", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            ImageUri = data?.data!!
            binding.imgFotoUser.setImageURI(ImageUri)
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            binding.imgFotoUser.setImageURI(ImageUri)
        }
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    private fun formatStepsText(steps: String): String {
        // Replace '\n' with '\n\n' to add a blank line between steps
        return steps.replace("\\n".toRegex(), "\n")
    }

    private fun setupEditTextListeners() {
        binding.txtBahanMakanan.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                appendTextToTextView(binding.txtBahanMakanan.text.toString(), binding.textView1)
                binding.txtBahanMakanan.setText("")
                return@setOnKeyListener true
            }
            false
        }

        binding.txtCaraPembuatan.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                appendTextToTextView(binding.txtCaraPembuatan.text.toString(), binding.bahan)
                binding.txtCaraPembuatan.setText("")
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun appendTextToTextView(newText: String, textView: TextView) {
        val currentText = textView.text.toString()
        textView.text = "$currentText\n$newText"
    }

    private fun isImageViewDrawableEqualToResource(imageView: CircleImageView, resourceId: Int): Boolean {
        val imageViewDrawable = imageView.drawable
        val resourceDrawable = ResourcesCompat.getDrawable(resources, resourceId, null)

        if (imageViewDrawable == null || resourceDrawable == null) {
            return false
        }

        val imageViewBitmap = (imageViewDrawable as BitmapDrawable).bitmap
        val resourceBitmap = (resourceDrawable as BitmapDrawable).bitmap

        return imageViewBitmap.sameAs(resourceBitmap)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "id.ac.ukdw.workout_tracker.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(null)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            ImageUri = Uri.fromFile(this)
        }
    }

}