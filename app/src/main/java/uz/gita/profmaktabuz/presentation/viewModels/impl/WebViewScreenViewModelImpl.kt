package uz.gita.profmaktabuz.presentation.viewModels.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.profmaktabuz.domen.StateLanguage
import uz.gita.profmaktabuz.domen.repositories.LanguageRepository
import uz.gita.profmaktabuz.domen.repositories.SharedPreferenceRepository
import uz.gita.profmaktabuz.presentation.viewModels.WebViewScreenViewModel
import uz.gita.profmaktabuz.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class WebViewScreenViewModelImpl @Inject constructor(private val pref : SharedPreferenceRepository, private val languageRepository: LanguageRepository): ViewModel(), WebViewScreenViewModel {
    override val showProgressBarLiveData = MutableLiveData<Unit>()
    override val hideProgressBarLiveData = MutableLiveData<Unit>()
    override val urlLiveData = MutableLiveData<String>()
    override val netScreenLiveData = MutableLiveData<Boolean>()
    override val languageLiveData = MutableLiveData<List<String>>()

    init {
        netScreenLiveData.value = !isConnected()
        val lang = if (pref.getLang() == "ru") StateLanguage.RUS else if (pref.getLang() == "qar") StateLanguage.QAR else StateLanguage.UZ
        languageLiveData.value =  languageRepository.getLangList(lang)
    }

    override fun loadUrl(url: String) {
        urlLiveData.value = url
    }
    override fun checkNet() {
        viewModelScope.launch {
            netScreenLiveData.value = !isConnected()
        }
    }

    override fun showProgressBar() {
        showProgressBarLiveData.value = Unit
    }

    override fun hideProgressBar() {
        hideProgressBarLiveData.value = Unit
    }

}