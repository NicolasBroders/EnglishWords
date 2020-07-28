package edu.example.broders.englishwords.repository

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
import edu.example.broders.englishwords.databinding.RepositoryFragmentBinding

class RepositoryFragment : Fragment() {

    private lateinit var viewModel: RepositoryViewModel
    private lateinit var viewModelFactory: RepositoryViewModelFactory
    private lateinit var binding: RepositoryFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.repository_fragment,
                container,
                false
        )

        viewModelFactory = RepositoryViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(RepositoryViewModel::class.java)

        binding.repositoryViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(RepositoryFragmentDirections.actionRepositoryToAddWord())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}