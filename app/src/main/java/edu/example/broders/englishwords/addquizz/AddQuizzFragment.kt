package edu.example.broders.englishwords.addquizz

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
import edu.example.broders.englishwords.databinding.AddQuizzFragmentBinding


class AddQuizzFragment : Fragment() {

    private lateinit var viewModel: AddQuizzViewModel
    private lateinit var viewModelFactory: AddQuizzViewModelFactory
    private lateinit var binding: AddQuizzFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.add_quizz_fragment,
                container,
                false
        )

        viewModelFactory = AddQuizzViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddQuizzViewModel::class.java)

        binding.addQuizzViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(AddQuizzFragmentDirections.actionAddQuizzToQuizzList())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}