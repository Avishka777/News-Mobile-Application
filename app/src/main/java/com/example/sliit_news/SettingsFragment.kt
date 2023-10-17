
package com.example.sliit_news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_settings,container,false)
        val spinner: Spinner = rootView.findViewById(R.id.spinner)
        val saveButton: Button = rootView.findViewById(R.id.button3)

        val viewModel = ViewModelProvider(requireActivity())[FragmentViewModel::class.java]

        saveButton.setOnClickListener {
            when (spinner.selectedItem){
                "blank" -> {}
                "Background 1" -> {viewModel.setBackground(R.color.background1)}
                "Background 2" -> {viewModel.setBackground(R.color.background2)}
                "Background 3" -> {viewModel.setBackground(R.color.background3)}
                "Background 4" -> {viewModel.setBackground(R.color.background4)}
                "Background 5" -> {viewModel.setBackground(R.color.background5)}
            }
        }
        return rootView
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}