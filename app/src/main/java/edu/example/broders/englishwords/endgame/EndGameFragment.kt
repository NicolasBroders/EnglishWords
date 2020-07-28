package edu.example.broders.englishwords.endgame

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
import edu.example.broders.englishwords.databinding.EndGameFragmentBinding

class EndGameFragment : Fragment() {

    private lateinit var viewModel: EndGameViewModel
    private lateinit var viewModelFactory: EndGameViewModelFactory
    private lateinit var binding: EndGameFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.end_game_fragment,
                container,
                false
        )

        viewModelFactory = EndGameViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(EndGameViewModel::class.java)

        binding.endGameViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(EndGameFragmentDirections.actionEndGameToHome())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}