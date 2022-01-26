package uz.gita.profmaktabuz.presentation.viewModels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.profmaktabuz.presentation.viewModels.SplashScreenViewModel
import uz.gita.profmaktabuz.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModelImpl @Inject constructor() : ViewModel(), SplashScreenViewModel {
    override val openMainScreenLiveData = MutableLiveData<Unit>()


    init {

        viewModelScope.launch {
            delay(1500)
            openMainScreenLiveData.value = Unit
        }

    }
}