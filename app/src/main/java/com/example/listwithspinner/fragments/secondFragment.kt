package com.example.listwithspinner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.listwithspinner.MainActivity
import com.example.listwithspinner.R
import com.example.listwithspinner.databinding.FragmentSecondBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [secondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class secondFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var spinnerAdapter : BaseAdapter
    private lateinit var listAdapter: ListAdapter
    private var countTotal : Int = 0
    private var countCurrent : Int = 0
    private var selectedPos : Int = 0
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        spinnerAdapter = SpinnerAdapter(mainActivity.arrayList)
        binding.spinnerItem.adapter = spinnerAdapter
        binding.spinnerItem.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedPos = position
                countTotal = mainActivity.arrayList[position].quantity.toInt()
                countCurrent = 0
                binding.counting.text = countCurrent.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        countCurrent = binding.counting.text.toString().toInt()
        binding.dec.setOnClickListener {
            if(countCurrent >0)
            countCurrent -= 1
            binding.counting.text = countCurrent.toString()
        }
        binding.inc.setOnClickListener {
            if(countCurrent < countTotal)
                countCurrent += 1
            binding.counting.text = countCurrent.toString()
            if(mainActivity.arrayList[selectedPos].quantity.toInt() == 0){
                Toast.makeText(requireContext(),"Sorry, Item is not in Stock..",Toast.LENGTH_SHORT).show()
            }
        }
        binding.Order.setOnClickListener {
            if(countCurrent == 0)
                Toast.makeText(requireContext(),"Select Quantity..",Toast.LENGTH_SHORT).show()
            else{
                mainActivity.arrayList[selectedPos].quantity = (mainActivity.arrayList[selectedPos].quantity.toInt() - countCurrent).toString()
                spinnerAdapter.notifyDataSetChanged()
                listAdapter = ListAdapter(mainActivity.arrayList)
                Toast.makeText(requireContext(),"Order Placed..",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
            }

        }
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment secondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            secondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}