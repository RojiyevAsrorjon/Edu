package uz.gita.profmaktabuz.presentation.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.profmaktabuz.R
import uz.gita.profmaktabuz.databinding.ScreenWebViewBinding
import uz.gita.profmaktabuz.presentation.viewModels.WebViewScreenViewModel
import uz.gita.profmaktabuz.presentation.viewModels.impl.WebViewScreenViewModelImpl

@AndroidEntryPoint
class WebViewScreen : Fragment(R.layout.screen_web_view) {
    private val binding by viewBinding(ScreenWebViewBinding::bind)
    private val viewModel: WebViewScreenViewModel by viewModels<WebViewScreenViewModelImpl>()
    private var isFinished = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        arguments?.let {
            val url = it.getString("url","").toString()
            viewModel.loadUrl(url)
            setTitle(url)

        }

        binding.webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                viewModel.showProgressBar()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                isFinished = true
                viewModel.hideProgressBar()
            }
        }

        binding.retryButton.setOnClickListener {
            viewModel.checkNet()
        }
        binding.backInvisible.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.webView.overScrollMode = View.OVER_SCROLL_NEVER

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true

        binding.webView.isFocusable = true
        binding.webView.isFocusableInTouchMode = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        binding.webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        binding.webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.allowContentAccess = true
        binding.webView.isClickable = true

        binding.webView.settings.databaseEnabled = true
        binding.webView.settings.setSupportMultipleWindows(false)

        viewModel.showProgressBarLiveData.observe(viewLifecycleOwner, showProgressBarObserver)
        viewModel.hideProgressBarLiveData.observe(viewLifecycleOwner, hideProgressBarObserver)
        viewModel.netScreenLiveData.observe(viewLifecycleOwner, showNoScreenObserver)
        viewModel.urlLiveData.observe(viewLifecycleOwner, urlObserver)
        viewModel.languageLiveData.observe(viewLifecycleOwner, changeLangObserver)

    }

    private fun setTitle(url: String) {
        if (url.endsWith("professions")) {
            binding.titleWeb.text = "Kasblar"
        } else if (url.endsWith("intro-resume")) {
            binding.titleWeb.text = "Resume"
        }else if (url.endsWith("trainings")) {
            binding.titleWeb.text = "Traininglar"
        }else if (url.endsWith("about-us")) {
            binding.titleWeb.text = "Ma`lumot"
        } else if (url.endsWith("start-test")) {
            binding.titleWeb.text = "Test topshirish"
        } else if (url.endsWith("calculator")) {
            binding.titleWeb.text = "Hisoblash"
        } else if (url.contains("instagram")) {
            binding.titleWeb.text = "Instagram"
        } else if (url.contains("facebook")) {
            binding.titleWeb.text = "Facebook"
        } else if (url.contains("youtube")) {
            binding.titleWeb.text = "Youtube"
        }
    }

    private val changeLangObserver = Observer<List<String>>{
        binding.redText.text = it[11]
        binding.longText.text = it[12]
        binding.retryButton.text = it[14]
    }

    private val showProgressBarObserver = Observer<Unit>{
        binding.progressBar.show()
    }

    private val hideProgressBarObserver = Observer<Unit>{
        binding.progressBar.hide()
    }
    private val urlObserver = Observer<String>{ url ->
        binding.webView.loadUrl(url)
    }

    private val showNoScreenObserver = Observer<Boolean>{
        if (it) {
            binding.conLayout.visibility = View.VISIBLE
            binding.progressBar1.show()
            lifecycleScope.launch {
                delay(1500)
                binding.progressBar1.hide()
            }
            binding.backButton.isClickable = false
        } else{
            binding.conLayout.visibility = View.GONE
            binding.webView.reload()
            binding.progressBar1.show()
            lifecycleScope.launch {
                if (isFinished) {
                    delay(2000)
                    binding.conLayout.visibility = View.GONE
                    binding.progressBar1.hide()
                }
            }
            binding.backButton.isClickable = true
        }
    }
}