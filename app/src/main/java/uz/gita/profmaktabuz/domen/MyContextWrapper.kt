package uz.gita.profmaktabuz.domen

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.*

class MyContextWrapper {
    companion object {
        fun wrap(newContext: Context?, language: String): Context? {
            var context = newContext
            val resources = context?.resources
            val configuration = resources?.configuration ?: return newContext
            val locale = Locale(language)
            Locale.setDefault(locale)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
            }
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            context = context?.createConfigurationContext(configuration)

            return context
        }
    }
}