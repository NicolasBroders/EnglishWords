package edu.example.broders.englishwords.addword

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
import edu.example.broders.englishwords.databinding.AddWordFragmentBinding

class AddWordFragment : Fragment() {

    private lateinit var viewModel: AddWordViewModel
    private lateinit var viewModelFactory: AddWordViewModelFactory
    private lateinit var binding: AddWordFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.add_word_fragment,
                container,
                false
        )

        viewModelFactory = AddWordViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddWordViewModel::class.java)

        binding.addWordViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(AddWordFragmentDirections.actionAddWordToWordList())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}