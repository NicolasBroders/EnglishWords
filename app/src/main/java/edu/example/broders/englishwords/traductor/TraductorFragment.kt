package edu.example.broders.englishwords.traductor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.example.broders.englishwords.R
import edu.example.broders.englishwords.databinding.TraductorFragmentBinding

class TraductorFragment : Fragment() {
    private lateinit var viewModel: TraductorViewModel
    private lateinit var viewModelFactory: TraductorViewModelFactory
    private lateinit var binding: TraductorFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.traductor_fragment,
                container,
                false
        )

        viewModelFactory = TraductorViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(TraductorViewModel::class.java)

        binding.traductorViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(TraductorFragmentDirections.actionTraductorToHome())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}