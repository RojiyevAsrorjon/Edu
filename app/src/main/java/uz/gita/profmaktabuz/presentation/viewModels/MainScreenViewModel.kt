package uz.gita.profmaktabuz.presentation.viewModels

import androidx.lifecycle.LiveData
import uz.gita.profmaktabuz.domen.StateLanguage
import uz.gita.profmaktabuz.domen.observersData.Event

interface MainScreenViewModel {
    val openWebViewScreenLiveData : LiveData<Event<String>>
    val openSourceScreenLiveData : LiveData<Event<Unit>>
    val errorMessageLiveData : LiveData<String>
    val showDialogLiveData : LiveData<Boolean>
    val screenStateLiveData : LiveData<Boolean>
    val showViewLiveData : LiveData<Unit>
    val languageLiveData : LiveData<List<String>>
    val hideViewLiveData : LiveData<Unit>
    fun loadUrl(url : String)
    fun openSourceScreen()
    fun openSourceScreenAfterSaved()
    fun saveUrl(url : String)
    fun saveLang(lang : String)
    fun changeLang(lang : StateLanguage)
}