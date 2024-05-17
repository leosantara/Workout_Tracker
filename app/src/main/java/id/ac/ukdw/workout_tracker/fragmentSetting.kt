package id.ac.ukdw.workout_tracker

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import id.ac.ukdw.workout_tracker.databinding.FragmentSettingBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class fragmentSetting : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var ImageUri: Uri
    private lateinit var currentUserUid: String
    private lateinit var user: User
    private lateinit var databaseRef: DatabaseReference
    private val REQUEST_IMAGE_CAPTURE = 1
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = FragmentSettingBinding.inflate(inflater, container, false)
            if (_binding != null) {
                auth = FirebaseAuth.getInstance()
                if (auth != null) {
                    currentUserUid = auth.currentUser?.uid.toString()
                    if (currentUserUid != null) {
                        val storageRefe =
                            FirebaseStorage.getInstance().getReference(currentUserUid + "/profil")

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
                            if (it.exists()) {
                                var nama = it.child("nama").value
                                binding.txtNamaBaru.hint = nama.toString()
                            }
                        }
                        var userRef = databaseRef.child(currentUserUid)
                        binding.btnSimpanData.setOnClickListener {
                            var nama = binding.txtNamaBaru.text.toString().trim()
                            if (nama.isEmpty()) {
                                binding.txtNamaBaru.error = "Nama tidak boleh kosong"
                                binding.txtNamaBaru.requestFocus()
                            } else {
                                val userData = hashMapOf<String, Any>(
                                    "nama" to nama
                                )
                                userRef.updateChildren(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            requireContext(),
                                            "Berhasil Mengganti Nama",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.txtNamaBaru.text.clear()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            requireContext(),
                                            "Gagal Mengganti Nama",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }

                        binding.btnKamera.setOnClickListener {
                            dispatchTakePictureIntent()
                        }

                        binding.btnBack.setOnClickListener {
                            Intent(context, MainActivity::class.java).also {
                                it.putExtra("fragmentType", "FragmentLainnya")
                                it.putExtra("selectedItemId", R.id.btnLainnya)
                                it.putExtra("selectedItemIdd", R.id.btnLainnya)
                                startActivity(it)
                            }
                        }

                        binding.btnGaleri.setOnClickListener {
                            selectImage()
                        }
                        binding.btnSimpanFoto.setOnClickListener {
                            uploadImage()
                        }

                        binding.btnGantiPass.setOnClickListener {
                            buttonChangePassowrd()
                        }

                        binding.btnLogout.setOnClickListener {
                            logoutAccount()
                        }
//                        }

                    }
                }
            }
            // Inflate the layout for this fragment
        }
        return binding.root
    }

    private fun buttonChangePassowrd() {
        binding.cardVerifyPass.visibility = View.VISIBLE
        binding.btnCancel.setOnClickListener {
            binding.cardVerifyPass.visibility = View.GONE
        }

        binding.btnCekPass.setOnClickListener btncek@{
            val pass = binding.txtPassNow.text.toString()

            if (pass.isEmpty()){
                binding.txtPassNow.error = "Password is requered"
                binding.txtPassNow.requestFocus()
                return@btncek
            }

//
            val user = auth.currentUser

            user.let {
                val credential = EmailAuthProvider.getCredential(auth.currentUser!!.email!!, pass)
                user?.reauthenticate(credential)?.addOnCompleteListener { task ->
                    when{
                        task.isSuccessful -> {
                            binding.cardVerifyPass.visibility = View.GONE
                            binding.cardChangePass.visibility = View.VISIBLE
                        }
                        task.exception is FirebaseAuthInvalidCredentialsException -> {
                            binding.txtPassNow.error = "Invalid Passowrd"
                            binding.txtPassNow.requestFocus()
                        }
                        else ->{
                            Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            binding.btnUpdatePass.setOnClickListener {
                val newPass = binding.txtPassBaru1.text.toString()
                val confirmPass = binding.txtPassBaru2.text.toString()

                if (newPass.isEmpty()){
                    binding.txtPassBaru1.error = "Password is required"
                    binding.txtPassBaru1.requestFocus()
                    return@setOnClickListener
                }
                if (confirmPass.isEmpty()){
                    binding.txtPassBaru2.error = "Password is required"
                    binding.txtPassBaru2.requestFocus()
                    return@setOnClickListener
                }
                if (newPass.length < 6){
                    binding.txtPassBaru1.error = "Password must be at least 6 Characters"
                    binding.txtPassBaru1.requestFocus()
                    return@setOnClickListener
                }

                if (confirmPass.length < 6){
                    binding.txtPassBaru2.error = "Password must be at least 6 Characters"
                    binding.txtPassBaru2.requestFocus()
                    return@setOnClickListener
                }

                if (newPass != confirmPass){
                    binding.txtPassBaru2.error = "Password doesn't match"
                    binding.txtPassBaru2.requestFocus()
                    return@setOnClickListener
                }

                user.let{
                    it?.updatePassword(newPass)
                        ?.addOnCompleteListener{
                            if (it.isSuccessful){
                                Toast.makeText(requireContext(), "Password Update", Toast.LENGTH_SHORT).show()
                                binding.cardVerifyPass.visibility = View.GONE
                                binding.cardChangePass.visibility = View.GONE

                            }
                        }
                }

            }
        }
    }

    private fun logoutAccount() {
        auth.signOut()
        Intent(requireContext(), LoginActivity::class.java).also {
            startActivity(it)
        }
        activity?.finish()
        Toast.makeText(requireActivity(), "Silahkan Login Kembali", Toast.LENGTH_SHORT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            ImageUri = data?.data!!
            binding.imgFotoUser.setImageURI(ImageUri)
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            binding.imgFotoUser.setImageURI(ImageUri)
        }
    }


    private fun uploadImage(){
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Mengupdate Data...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageRef = FirebaseStorage.getInstance().getReference(currentUserUid+"/profil")
        storageRef.putFile(ImageUri).addOnSuccessListener {
            binding.imgFotoUser.setImageURI(ImageUri)
            Toast.makeText(requireActivity(), "Berhasil Mengganti Foto", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()
        }.addOnFailureListener{
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(requireActivity(), "Gagal Mengganti Foto", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}