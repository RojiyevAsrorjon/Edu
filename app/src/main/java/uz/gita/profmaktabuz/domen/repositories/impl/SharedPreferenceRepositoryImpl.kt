package uz.gita.profmaktabuz.domen.repositories.impl

import uz.gita.profmaktabuz.data.local.LocalDatabase
import uz.gita.profmaktabuz.domen.repositories.SharedPreferenceRepository
import javax.inject.Inject

class SharedPreferenceRepositoryImpl @Inject constructor(private val pref : LocalDatabase) : SharedPreferenceRepository{
    override fun isFirstTime(): Boolean = pref.isFirstTime

    override fun setIsFirstTime() {
        pref.isFirstTime = true
    }

    override fun setUrl(url: String) {
        pref.url = url
    }

    override fun getUrl(): String = pref.url
    override fun getLang(): String = pref.lang

    override fun setLang(lang: String) {
        pref.lang = lang
    }
}