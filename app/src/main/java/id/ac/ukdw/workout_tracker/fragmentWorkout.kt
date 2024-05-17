package id.ac.ukdw.workout_tracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.ac.ukdw.workout_tracker.databinding.FragmentWorkoutBinding


class fragmentWorkout : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!
    private var listType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listType = it.getString(ARG_LIST_TYPE)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = when (listType) {
            "A" ->
                arrayListOf("Jumping Jacks",
                    "Abdominal Crunches",
                    "Russian Twist",
                    "Mountain Climber",
                    "Leg Raises",
                    "Plank",
                    "drawable/img_absworkout_mini1"
                    , "drawable/img_absworkout_mini2"
                    , "drawable/img_absworkout_mini3"
                    , "drawable/img_absworkout_mini4"
                    , "drawable/img_absworkout_mini5"
                    , "drawable/img_absworkout_mini6"
                ,"ABS Workout")

            "B" -> arrayListOf("Tricep Dips", "Push Up", "Side Plank", "Burpees", "Triceps Stretch", "Biceps Stretch",
                "drawable/img_armworkout_mini1"
                ,  "drawable/img_armworkout_mini2"
                ,  "drawable/img_armworkout_mini3"
                ,  "drawable/img_armworkout_mini4"
                ,  "drawable/img_armworkout_mini5"
                ,  "drawable/img_armworkout_mini6"
            , "ARM Workout")
            "C" -> arrayListOf("Jumping Jacks", "Incline Push-ups", "Wide Arm Push-ups", "Decline Push-ups", "Knee Push-ups", "Chest Stretch",
                "drawable/img_absworkout_mini1"
                ,"drawable/img_chest_mini2"
                ,"drawable/img_chest_mini3"
                ,"drawable/img_chest_mini4"
                ,"drawable/img_chest_mini5"
                ,"drawable/img_chest_mini6"
            ,"Chest Workout")
            else -> arrayListOf()
        }

        binding.txtGerakan1.text = dataList[0]
        binding.txtGerakan2.text = dataList[1]
        binding.txtGerakan3.text = dataList[2]
        binding.txtGerakan4.text = dataList[3]
        binding.txtGerakan5.text = dataList[4]
        binding.txtGerakan6.text = dataList[5]
        binding.btnGerakan1.setImageResource(getDrawableId(dataList[6]))
        binding.btnGerakan2.setImageResource(getDrawableId(dataList[7]))
        binding.btnGerakan3.setImageResource(getDrawableId(dataList[8]))
        binding.btnGerakan4.setImageResource(getDrawableId(dataList[9]))
        binding.btnGerakan5.setImageResource(getDrawableId(dataList[10]))
        binding.btnGerakan6.setImageResource(getDrawableId(dataList[11]))
        binding.txtJudul.text = dataList[12]


        binding.btnBack.setOnClickListener {
            Intent(getContext(), MainActivity::class.java).also {
                it.putExtra("fragmentType", "FragmentHome")
                startActivity(it)
            }


        }
        binding.btnStart.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.workout_activity, fragmentStartWorkout.newInstancet(listType!!)).commit()
        }
    }
    private fun getDrawableId(name: String): Int {
        return resources.getIdentifier(name, "drawable", context?.packageName ?:null )
    }
    companion object {
        private const val ARG_LIST_TYPE = "list_type"

        @JvmStatic
        fun newInstance(listType: String) =
            fragmentWorkout().apply {
                arguments = Bundle().apply {
                    putString(ARG_LIST_TYPE, listType)
                }
            }
    }
}