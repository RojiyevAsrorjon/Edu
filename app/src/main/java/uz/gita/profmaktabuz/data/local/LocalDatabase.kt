package uz.gita.profmaktabuz.data.local

import android.content.Context
import uz.gita.profmaktabuz.utils.BooleanPreference
import uz.gita.profmaktabuz.utils.StringPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDatabase @Inject constructor(context: Context) {
    private val sharedPref = context.getSharedPreferences("prof.maktab.uz", Context.MODE_PRIVATE)

    var isFirstTime : Boolean by BooleanPreference(sharedPref)
    var url : String by StringPreference(sharedPref)

    var lang : String by StringPreference(sharedPref)
}