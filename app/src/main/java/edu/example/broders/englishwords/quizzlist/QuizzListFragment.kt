package edu.example.broders.englishwords.quizzlist

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
import edu.example.broders.englishwords.databinding.QuizzListFragmentBinding

class QuizzListFragment : Fragment() {

    private lateinit var viewModel: QuizzListViewModel
    private lateinit var viewModelFactory: QuizzListModelFactory
    private lateinit var binding: QuizzListFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.quizz_list_fragment,
                container,
                false
        )

        viewModelFactory = QuizzListModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizzListViewModel::class.java)

        binding.quizzListViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(QuizzListFragmentDirections.actionQuizzListToAddQuizz())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}