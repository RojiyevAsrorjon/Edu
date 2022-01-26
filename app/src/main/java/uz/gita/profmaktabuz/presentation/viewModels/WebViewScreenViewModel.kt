package uz.gita.profmaktabuz.presentation.viewModels

import androidx.lifecycle.LiveData

interface WebViewScreenViewModel {
    val showProgressBarLiveData: LiveData<Unit>
    val hideProgressBarLiveData: LiveData<Unit>
    val urlLiveData : LiveData<String>
    val netScreenLiveData : LiveData<Boolean>
    val languageLiveData : LiveData<List<String>>
    fun loadUrl(url : String)
    fun checkNet()
    fun showProgressBar()
    fun hideProgressBar()
}