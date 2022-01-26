package uz.gita.profmaktabuz.presentation.screens

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
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
import uz.gita.profmaktabuz.databinding.ScreenMainNavBinding
import uz.gita.profmaktabuz.domen.StateLanguage
import uz.gita.profmaktabuz.domen.observersData.EventObserver
import uz.gita.profmaktabuz.models.Utils
import uz.gita.profmaktabuz.presentation.viewModels.MainScreenViewModel
import uz.gita.profmaktabuz.presentation.viewModels.impl.MainScreenViewModelImpl

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main_nav) {

    private val binding by viewBinding(ScreenMainNavBinding::bind)
    private val viewModel: MainScreenViewModel by viewModels<MainScreenViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private var path = ""
    private var isFirst = false

    @SuppressLint("FragmentLiveDataObserve")
    private var isLoadingText = ""
    private var loadingErrorText = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.navView.itemIconTintList = null

        binding.include.menuButton.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        path = Utils.getRootDirPath(requireContext())
        val config = PRDownloaderConfig.newBuilder()
            .setReadTimeout(30000)
            .setConnectTimeout(30000)
            .build()
        PRDownloader.initialize(requireContext(), config)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.idWork -> {
                    viewModel.loadUrl("https://prof.maktab.uz/professions")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.idResume -> {
                    viewModel.loadUrl("https://prof.maktab.uz/intro-resume")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                }
                R.id.idTraining -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    viewModel.loadUrl("https://prof.maktab.uz/trainings")
                }
                R.id.idSources -> {
                    viewModel.openSourceScreen()
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.idProject -> {
                    viewModel.loadUrl("https://prof.maktab.uz/about-us")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
            true
        }

        binding.include.langLayout.setOnClickListener {
            val languages = arrayOf("O`zbek", "Русский", "Qaraqalpaq")
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setSingleChoiceItems(languages, -1) { dialog, which ->
                when (which) {
                    0 -> {
//                        viewModel.saveLang("")
//                        requireActivity().recreate()
                        viewModel.changeLang(StateLanguage.UZ)
                        dialog.dismiss()
                    }
                    1 -> {
//                        viewModel.saveLang("ru")
//                        requireActivity().recreate()
                        viewModel.changeLang(StateLanguage.RUS)

                        dialog.dismiss()
                    }
                    2 -> {
//                        viewModel.saveLang("kaa")
//                        requireActivity().recreate()
                        viewModel.changeLang(StateLanguage.QAR)
                        dialog.dismiss()
                    }
                }
            }
            dialog.show()
        }

        viewModel.openSourceScreenLiveData.observe(this, openSourceScreenObserver)
        viewModel.openWebViewScreenLiveData.observe(this, openWebViewScreenObserver)
        viewModel.showViewLiveData.observe(viewLifecycleOwner, showViewObserver)
        viewModel.hideViewLiveData.observe(viewLifecycleOwner, hideViewObserver)
        viewModel.screenStateLiveData.observe(viewLifecycleOwner, screenStateObserver)
        viewModel.showDialogLiveData.observe(this, showDialogObserver)
        viewModel.languageLiveData.observe(viewLifecycleOwner, changeLangObserver)
        binding.include.idDoTest.setOnClickListener {
            viewModel.loadUrl("https://prof.maktab.uz/start-test")
        }

        binding.include.idCalculator.setOnClickListener {
            viewModel.loadUrl("https://prof.maktab.uz/calculator")
        }

        binding.include.idSourceButton.setOnClickListener {
            viewModel.openSourceScreen()
        }

        binding.include.currentWorkButton.setOnClickListener {
            viewModel.loadUrl("https://prof.maktab.uz/professions")
        }

        binding.include.instagram.setOnClickListener {
            viewModel.loadUrl("https://www.instagram.com/itsm_uz/")
        }
        binding.include.facebook.setOnClickListener {
            viewModel.loadUrl("https://www.facebook.com/itsc.uz/")
        }
        binding.include.youtube.setOnClickListener {
            viewModel.loadUrl("https://www.youtube.com/c/Innovatsiyatexnologiyavastrategiyamarkaziitsm")
        }

    }

    private val changeLangObserver = Observer<List<String>> {
        val menu = binding.navView.menu

        val menuWork = menu.findItem(R.id.idWork)
        menuWork.title = it[0]
        val menuResume = menu.findItem(R.id.idResume)
        menuResume.title = it[1]
        val menuTraining = menu.findItem(R.id.idTraining)
        menuTraining.title = it[2]
        val menuSource = menu.findItem(R.id.idSources)
        menuSource.title = it[3]
        val menuProject = menu.findItem(R.id.idProject)
        menuProject.title = it[4]

        binding.include.idFindWork.text = it[5]
        binding.include.idDoTest.text = it[6]
        binding.include.idCalculator.text = it[7]
        binding.include.infoText.text = it[8]
        binding.include.largeText.text = it[10]
        binding.include.langText.text = it[15]

        binding.include.currentWorkButton.paint.isUnderlineText = true
        binding.include.currentWorkButton.text = it[16]
        binding.include.idSourceButton.text = it[3]
        val header = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.infoTextNav)
        header.findViewById<TextView>(R.id.infoTextNav).text = it[8]
        isLoadingText = it[17]
        loadingErrorText = it[18]

    }
    private val openWebViewScreenObserver = EventObserver<String> {
        val bundle = Bundle()
        bundle.putString("url", it)
        navController.navigate(R.id.webViewScreen, bundle)
    }
    private val openSourceScreenObserver = EventObserver<Unit> {
        navController.navigate(R.id.pdfViewScreen)
    }

    private val showViewObserver = Observer<Unit> {
        binding.include.progressText.visibility = View.VISIBLE
    }

    private val hideViewObserver = Observer<Unit> {
        binding.include.progressText.visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    private val screenStateObserver = Observer<Boolean> {
        if (it) {
            val uri = "https://asror.worknet.uz/prof.pdf"
            val file = "prof.pdf"
            PRDownloader.download(
                uri, activity?.baseContext?.filesDir?.absolutePath, file
            ).build()
                .setOnProgressListener {
                    val str = Utils.getProgressDisplayLine(it.currentBytes, it.totalBytes)
                    binding.include.progressText.text = "$isLoadingText : $str"
                }
                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        val url = "$path/prof.pdf"
                        viewModel.saveUrl(url)
                        viewModel.openSourceScreen()
                    }

                    override fun onError(error: Error?) {
                        Snackbar.make(binding.include.layout, loadingErrorText, Snackbar.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private val showDialogObserver = Observer<Boolean> {
        if (it) {
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Tarmoq bilan aloqa yo`q!")
                .setMessage("Qo`llanmadan foydalanish uchun dastlab uni tarmoqdan yuklab olishingiz zarur! Tarmoqqa ulanib qayta urinib ko`ring!")
                .setPositiveButton("Ok") { p0, p1 -> }
                .show()
        }
    }


}