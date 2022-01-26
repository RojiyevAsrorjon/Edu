package uz.gita.profmaktabuz.domen.repositories

import uz.gita.profmaktabuz.domen.StateLanguage

interface LanguageRepository {
    fun getLangList(lang:StateLanguage) : List<String>
}