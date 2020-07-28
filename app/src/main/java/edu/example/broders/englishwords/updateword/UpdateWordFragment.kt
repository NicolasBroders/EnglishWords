package edu.example.broders.englishwords.updateword

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
import edu.example.broders.englishwords.databinding.UpdateWordFragmentBinding

class UpdateWordFragment : Fragment() {

    private lateinit var viewModel: UpdateWordViewModel
    private lateinit var viewModelFactory: UpdateWordViewModelFactory
    private lateinit var binding: UpdateWordFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.update_word_fragment,
                container,
                false
        )

        viewModelFactory = UpdateWordViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(UpdateWordViewModel::class.java)

        binding.updateWordViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(UpdateWordFragmentDirections.actionUpdateWordToWordList())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}