package edu.example.broders.englishwords.home

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
class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: HomeFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.home_fragment,
                container,
                false
        )

        viewModelFactory = HomeViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if(goToQuizzSetting){

                findNavController().navigate(HomeFragmentDirections.actionHomeToQuizzSettings())
                viewModel.onPlayQuizzComplete()
            }
        })

        return binding.root
    }
}