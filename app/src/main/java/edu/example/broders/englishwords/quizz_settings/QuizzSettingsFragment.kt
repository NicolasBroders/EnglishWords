package edu.example.broders.englishwords.quizz_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import edu.example.broders.englishwords.R
import edu.example.broders.englishwords.database.RepertoireDatabase
import edu.example.broders.englishwords.database.RepertoireDatabaseDao
import edu.example.broders.englishwords.databinding.QuizzSettingFragmentBinding


class QuizzSettingsFragment: Fragment() {

    private lateinit var viewModel: QuizzSettingsViewModel
    private lateinit var viewModelFactory : QuizzSettingsViewModelFactory
    private lateinit var binding: QuizzSettingFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.quizz_setting_fragment,
                container,
                false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = RepertoireDatabase.getInstance(application).repertoireDatabaseDao

        viewModelFactory = QuizzSettingsViewModelFactory(dataSource,application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(QuizzSettingsViewModel::class.java)

        binding.quizzSettingsViewModel = viewModel
        binding.setLifecycleOwner(this)

        /*
        viewModel.eventQuizzSetting.observe(viewLifecycleOwner, Observer { goToQuizzSetting ->
            if (goToQuizzSetting) {

                //findNavController().navigate(HomeDirections.actionHomeToQuizzSettings())
                viewModel.onPlayQuizzComplete()
            }
        })
*/
        return binding.root
    }
}