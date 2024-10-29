package com.example.huellacarbono2.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.huellacarbono2.R
import com.example.huellacarbono2.databinding.FragmentFamiliarBinding

class FamiliarFragment : Fragment() {

    private var _binding: FragmentFamiliarBinding? = null

    var selectedItemSpWaterPump : String? = null
    var selectedItemSpWaterHeaterType: String? = null
    var selectedItemSpOvenType: String? = null

    var calculoFinalHuellaCarbono = 0.0


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFamiliarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Spineer de si se tiene bomba de agua o no
        val spWaterPump = root.findViewById<Spinner>(R.id.spWaterPump)
        val adapterWaterPump = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,listOf("Si", "No"))
        adapterWaterPump.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spWaterPump.adapter = adapterWaterPump

        spWaterPump.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemSpWaterPump = parent?.getItemAtPosition(position).toString() //Aqui se almacena seleccion de si tiene bomba de agua o no
                //Toast.makeText(requireContext(), "Seleccionaste: $selectedItemSpWaterPump", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        //Spineer de tipo de agua
        val spWaterHeaterType = root.findViewById<Spinner>(R.id.spWaterHeaterType)
        val adapterWaterHeaterType = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,listOf("Gas", "Electrico","Solar","No tengo"))
        adapterWaterHeaterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spWaterHeaterType.adapter = adapterWaterHeaterType

        spWaterHeaterType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemSpWaterHeaterType = parent?.getItemAtPosition(position).toString() //Aqui se almacena seleccion de tipo de agua
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        //Spinner de tipo de horno
        val spOvenType = root.findViewById<Spinner>(R.id.spOvenType)
        val adapterOvenType = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,listOf("Gas", "Electrico","Microondas","No tengo"))
        adapterOvenType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spOvenType.adapter = adapterOvenType

        spOvenType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemSpOvenType = parent?.getItemAtPosition(position).toString() //Aqui se almacena seleccion de Tipo de horno
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        //Elemento de boton calcular
        val btnCalcular = root.findViewById<Button>(R.id.btnCalcular)

        btnCalcular.setOnClickListener {
            calculateCarbonFootprint()
            findNavController().navigate(FamiliarFragmentDirections.actionNavFamiliarToNavResult(result = calculoFinalHuellaCarbono.toFloat()))
        }

        return root
    }

    private fun calculateCarbonFootprint() {
        val etNumPeople = binding.etNumPeople.text.toString().toInt()
        val etNumLights = binding.etNumLights.text.toString().toInt()
        val etNumTVs = binding.etNumTVs.text.toString().toInt()

        var optionOvenBoolConsumption = 0.0
        when (selectedItemSpWaterPump) {
            "Si" -> optionOvenBoolConsumption= 0.65
            "No" -> optionOvenBoolConsumption= 0.0
        }

        var optionWaterHeaterTypeConsumption = 0.0
        when (selectedItemSpWaterHeaterType) {
            "Gas" -> optionWaterHeaterTypeConsumption = 0.4
            "Electrico" -> optionWaterHeaterTypeConsumption = 0.2
            "Solar" -> optionWaterHeaterTypeConsumption = 0.1
            "No tengo" -> optionWaterHeaterTypeConsumption = 0.0
        }

        var optionOvenTypeConsumption = 0.0
        when (selectedItemSpOvenType) {
            "Gas" -> optionOvenTypeConsumption = 0.9
            "Electrico" -> optionOvenTypeConsumption = 1.2
            "Microondas" -> optionOvenTypeConsumption = 0.7
            "No tengo" -> optionOvenTypeConsumption = 0.0
        }

        calculoFinalHuellaCarbono = (etNumPeople * 0.3) + (etNumLights * 0.2) + (etNumTVs * 0.1) + optionOvenBoolConsumption + optionWaterHeaterTypeConsumption + optionOvenTypeConsumption
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}