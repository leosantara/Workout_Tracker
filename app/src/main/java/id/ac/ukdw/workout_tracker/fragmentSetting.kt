package id.ac.ukdw.workout_tracker

import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import id.ac.ukdw.workout_tracker.databinding.FragmentSettingBinding
import java.io.File


class fragmentSetting : Fragment() {
    private var _binding : FragmentSettingBinding? = null
    private lateinit var auth : FirebaseAuth
    private lateinit var ImageUri : Uri
    private lateinit var currentUserUid: String
    private lateinit var databaseRef: DatabaseReference
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        currentUserUid = auth.currentUser?.uid.toString()
        val storageRefe = FirebaseStorage.getInstance().getReference(currentUserUid+"/profil")

        val  localfile = File.createTempFile("profil", "jpg")
        storageRefe.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgFotoUser.setImageBitmap(bitmap)
        }

        databaseRef = FirebaseDatabase.getInstance().getReference("users")
        var userRef = databaseRef.child(currentUserUid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val namaa = snapshot.child("nama").getValue(String::class.java)
                binding.txtNamaBaru.hint = namaa
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
                Toast.makeText(requireContext(), "Database Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)


        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_activity, fragmentLainnya()).commit()
        }

         binding.btnGaleri.setOnClickListener {
             selectImage()
         }
        binding.btnSimpanFoto.setOnClickListener{
            uploadImage()
        }

        binding.btnGantiPass.setOnClickListener{
            buttonChangePassowrd()
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
                            Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
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
                                Toast.makeText(context, "Password Update", Toast.LENGTH_SHORT).show()
                                binding.cardVerifyPass.visibility = View.GONE
                                binding.cardChangePass.visibility = View.GONE
                                logoutAccount()
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

        if (requestCode == 100 && resultCode == RESULT_OK){
            ImageUri = data?.data!!
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




}











