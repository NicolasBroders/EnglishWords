package edu.example.broders.englishwords.quizzgame

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
import edu.example.broders.englishwords.databinding.HomeFragmentBinding
import edu.example.broders.englishwords.databinding.QuizzGameFragmentBinding

class QuizzGameFragment : Fragment() {

    private lateinit var viewModel: QuizzGameViewModel
    private lateinit var viewModelFactory: QuizzGameViewModelFactory
    private lateinit var binding: QuizzGameFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.home_fragment,
                container,
                false
        )

        viewModelFactory = QuizzGameViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizzGameViewModel::class.java)

        binding.quizzGameViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(QuizzGameFragmentDirections.actionQuizzGameToEndGame())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}