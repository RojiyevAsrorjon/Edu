package uz.gita.profmaktabuz.domen.repositories.impl

import uz.gita.profmaktabuz.domen.DataList
import uz.gita.profmaktabuz.domen.StateLanguage
import uz.gita.profmaktabuz.domen.repositories.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(private val dataList : DataList) : LanguageRepository {
    override fun getLangList(lang: StateLanguage): List<String> {
        return when (lang) {
            StateLanguage.QAR -> {
                dataList.qar
            }
            StateLanguage.RUS -> {
                dataList.rus
            }
            else -> dataList.uz
        }
    }
}