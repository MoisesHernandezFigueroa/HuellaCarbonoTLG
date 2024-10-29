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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.huellacarbono2.R
import com.example.huellacarbono2.databinding.FragmentTransportBinding

class TransportFragment : Fragment() {

    private var _binding: FragmentTransportBinding? = null
    var selectedItemSpTransportType: String? = null
    var calculoFinalHuellaCarbono = 0.0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTransportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Spinner para obtener el tipo de transporte que utiliza
        //Spineer de si se tiene bomba de agua o no
        val transportOptions = listOf("Auto propio", "Taxi", "Motocicleta", "Camion", "Metrobus", "Bicicleta")
        val spTransportType = root.findViewById<Spinner>(R.id.spTransportType)
        val adapterTransportType = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,transportOptions)
        adapterTransportType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTransportType.adapter = adapterTransportType

        spTransportType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemSpTransportType = parent?.getItemAtPosition(position).toString() //Aqui se almacena seleccion de tipo de auto
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        val btnCalcular = root.findViewById<Button>(R.id.btnCalcular)
        btnCalcular.setOnClickListener {
            calculateCarbonFootprint()
            findNavController().navigate(TransportFragmentDirections.actionNavTransportToNavResult(result = calculoFinalHuellaCarbono.toFloat()))
        }

        return root
    }

    private fun calculateCarbonFootprint() {
        val etHoursTransport = binding.etHoursTransport.text.toString().toInt()

        var optiontransportTipeConsumption = 0.0
        when (selectedItemSpTransportType) {
            "Auto propio" -> optiontransportTipeConsumption = 0.9
            "Taxi" -> optiontransportTipeConsumption = 0.6
            "Motocicleta" -> optiontransportTipeConsumption = 0.3
            "Camion" -> optiontransportTipeConsumption = 0.5
            "Metrobus" -> optiontransportTipeConsumption = 0.7
            "Bicicleta" -> optiontransportTipeConsumption = 0.1
        }

        calculoFinalHuellaCarbono = etHoursTransport * optiontransportTipeConsumption

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}