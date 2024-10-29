package com.example.huellacarbono2.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.huellacarbono2.R
import com.example.huellacarbono2.databinding.FragmentResultBinding


class ResultFragment : Fragment(){
    private var _binding: FragmentResultBinding? = null
    val args: ResultFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val result = args.result

        val tvCarbonFootprintResult = binding.tvCarbonFootprintResult
        tvCarbonFootprintResult.text = result.toString() + " Toneladas de CO2"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val btnBackToMenu = root.findViewById<Button>(R.id.btnBackToMenu)

        btnBackToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_nav_result_to_nav_home)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}