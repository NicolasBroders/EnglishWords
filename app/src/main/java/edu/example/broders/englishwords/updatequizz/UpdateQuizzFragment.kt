package edu.example.broders.englishwords.updatequizz

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
import edu.example.broders.englishwords.databinding.UpdateQuizzFragmentBinding

class UpdateQuizzFragment : Fragment() {

    private lateinit var viewModel: UpdateQuizzViewModel
    private lateinit var viewModelFactory: UpdateQuizzViewModelFactory
    private lateinit var binding: UpdateQuizzFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.update_quizz_fragment,
                container,
                false
        )

        viewModelFactory = UpdateQuizzViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(UpdateQuizzViewModel::class.java)

        binding.updateQuizzViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(UpdateQuizzFragmentDirections.actionUpdateQuizzToQuizzList())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}