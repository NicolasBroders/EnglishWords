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
import edu.example.broders.englishwords.databinding.QuizzListFragmentAltBinding
import edu.example.broders.englishwords.databinding.QuizzListFragmentBinding

class QuizzListFragment : Fragment() {

    private lateinit var viewModel: QuizzListViewModel
    private lateinit var viewModelFactory: QuizzListModelFactory
    private lateinit var binding: QuizzListFragmentAltBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.quizz_list_fragment_alt,
                container,
                false
        )

        viewModelFactory = QuizzListModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizzListViewModel::class.java)

        val adapter = QuizzAdapter()
        binding.setLifecycleOwner(this)
        binding.wordList.adapter = adapter

        viewModel.quizz.observe(viewLifecycleOwner, Observer {
            adapter.data = it
        })

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(QuizzListFragmentDirections.actionQuizzListToAddQuizz())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}