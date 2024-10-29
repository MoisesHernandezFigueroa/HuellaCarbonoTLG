package com.example.huellacarbono2.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.huellacarbono2.R
import com.example.huellacarbono2.databinding.FragmentLifestyleBinding

class LifestyleFragment: Fragment() {
    private var _binding: FragmentLifestyleBinding? = null

    var selectedItemSpHobby : String? = null
    var calculoFinalHuellaCarbono = 0.0
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLifestyleBinding.inflate(inflater, container, false)
        val root: View = binding.root


        //Spinner que indica tipo de hobbie
        val hobbiesOptions = resources.getStringArray(R.array.hobby_options)
        val spHobby = root.findViewById<Spinner>(R.id.spHobby)
        val adapterHobby = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,hobbiesOptions)
        adapterHobby.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spHobby.adapter = adapterHobby

        spHobby.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemSpHobby = parent?.getItemAtPosition(position).toString() //Aqui se almacena seleccion de tipo de auto
                Toast.makeText(requireContext(), "Seleccionaste: $selectedItemSpHobby", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val btnCalcular = root.findViewById<Button>(R.id.btnCalcular)
        btnCalcular.setOnClickListener {
            calculateCarbonFootprint()
            findNavController().navigate(LifestyleFragmentDirections.actionNavLifestyleToNavResult(result = calculoFinalHuellaCarbono.toFloat()))
        }

        return root
    }

    private fun calculateCarbonFootprint() {
        val etHobbyHours = binding.etHobbyHours.text.toString().toInt()
        val etSodaConsumption = binding.etSodaConsumption.text.toString().toInt()
        val etAlcoholConsumption = binding.etAlcoholConsumption.text.toString().toInt()
        val etMeatConsumption = binding.etMeatConsumption.text.toString().toInt()

        var opcitonSpHobbyConsumption = 0.0
        when(selectedItemSpHobby){
            resources.getStringArray(R.array.hobby_options)[0] -> opcitonSpHobbyConsumption = 0.9
            resources.getStringArray(R.array.hobby_options)[1] -> opcitonSpHobbyConsumption = 0.2
            resources.getStringArray(R.array.hobby_options)[2] -> opcitonSpHobbyConsumption = 0.4
            resources.getStringArray(R.array.hobby_options)[3] -> opcitonSpHobbyConsumption = 0.5
            resources.getStringArray(R.array.hobby_options)[4] -> opcitonSpHobbyConsumption = 0.3
            resources.getStringArray(R.array.hobby_options)[5] -> opcitonSpHobbyConsumption = 0.6
            resources.getStringArray(R.array.hobby_options)[6] -> opcitonSpHobbyConsumption = 0.8
        }

        calculoFinalHuellaCarbono = (etHobbyHours * opcitonSpHobbyConsumption) + (etSodaConsumption * 0.5 )+ (etAlcoholConsumption * 0.7) + (etMeatConsumption * 1.5)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}