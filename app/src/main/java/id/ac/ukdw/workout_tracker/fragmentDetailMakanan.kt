package id.ac.ukdw.workout_tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import id.ac.ukdw.workout_tracker.databinding.FragmentDetailMakananBinding

class fragmentDetailMakanan : Fragment() {

    private var _binding : FragmentDetailMakananBinding? = null
    private val binding get() = _binding!!
    private var makan: MakananClass? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMakananBinding.inflate(inflater, container, false)
        arguments?.let { args ->
            // Mengecek apakah data dengan key "notif" ada dalam argument
            if (args.containsKey("makanan")) {

                // Mengambil objek NotifClass dari argument
                makan = args.getParcelable("makanan")

                binding.txtJudul.text = makan?.Judul
                binding.txtCara.text = makan?.Cara
                binding.txtBahan.text = makan?.Bahan
                Picasso.get().load(makan?.Foto).into(binding.imgMakanan)


            }
        }
        binding.btnBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.makanan_activity, fragmentResepMakanan()).commit()
        }



        return binding.root
    }


}