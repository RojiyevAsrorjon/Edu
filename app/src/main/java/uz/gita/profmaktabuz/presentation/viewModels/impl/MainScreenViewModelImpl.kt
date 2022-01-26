package uz.gita.profmaktabuz.presentation.viewModels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.profmaktabuz.domen.StateLanguage
import uz.gita.profmaktabuz.domen.observersData.Event
import uz.gita.profmaktabuz.domen.repositories.LanguageRepository
import uz.gita.profmaktabuz.domen.repositories.SharedPreferenceRepository
import uz.gita.profmaktabuz.presentation.viewModels.MainScreenViewModel
import uz.gita.profmaktabuz.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModelImpl @Inject constructor(private val repository : SharedPreferenceRepository, private val languageRepository: LanguageRepository) : ViewModel(), MainScreenViewModel {
    override val openWebViewScreenLiveData = MutableLiveData<Event<String>>()
    override val openSourceScreenLiveData = MutableLiveData<Event<Unit>>()
    override val errorMessageLiveData = MutableLiveData<String>()
    override val screenStateLiveData = MutableLiveData<Boolean>()
    override val showViewLiveData = MutableLiveData<Unit>()
    override val languageLiveData = MutableLiveData<List<String>>()
    override val hideViewLiveData = MutableLiveData<Unit>()
    override val showDialogLiveData  = MutableLiveData<Boolean>()

    init {
        val lang = if (repository.getLang() == "ru") StateLanguage.RUS else if (repository.getLang() == "qar") StateLanguage.QAR else StateLanguage.UZ
        languageLiveData.value = languageRepository.getLangList(lang)
    }

    override fun loadUrl(url: String) {
        openWebViewScreenLiveData.value = Event(url)
    }



    override fun openSourceScreen() {
        if (!repository.isFirstTime()) {
            if (!isConnected()) {
                showDialogLiveData.value = true
            } else{
                showViewLiveData.value = Unit
                screenStateLiveData.value = true
                showDialogLiveData.value = false
            }
        }
        else{
            screenStateLiveData.value = false
            hideViewLiveData.value = Unit
            openSourceScreenLiveData.value = Event(Unit)
        }
    }

    override fun openSourceScreenAfterSaved() {
        openSourceScreenLiveData.value = Event(Unit)
    }

    override fun saveUrl(url: String) {
        repository.setUrl(url)
        repository.setIsFirstTime()
    }

    override fun saveLang(lang: String) {
        repository.setLang(lang)
    }

    override fun changeLang(lang: StateLanguage) {
        languageLiveData.value = languageRepository.getLangList(lang)
        val repLang = if (lang == StateLanguage.RUS) "ru" else if(lang == StateLanguage.QAR) "qar" else "uz"
        repository.setLang(repLang)
    }
}