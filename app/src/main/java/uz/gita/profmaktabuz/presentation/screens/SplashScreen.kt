package uz.gita.profmaktabuz.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.profmaktabuz.R
import uz.gita.profmaktabuz.databinding.ScreenSplashBinding
import uz.gita.profmaktabuz.models.Utils
import uz.gita.profmaktabuz.models.Utils.Companion.getProgressDisplayLine
import uz.gita.profmaktabuz.presentation.viewModels.SplashScreenViewModel
import uz.gita.profmaktabuz.presentation.viewModels.impl.SplashScreenViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashScreenViewModel by viewModels<SplashScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }



    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openMainScreenLiveData.observe(this, openMainScreenObserver)



        viewModel.openMainScreenLiveData.observe(this, openMainScreenObserver)

    }

    private val openMainScreenObserver = Observer<Unit> {
        navController.navigate(R.id.action_splashScreen_to_mainScreen)
    }




}