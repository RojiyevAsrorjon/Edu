package uz.gita.profmaktabuz.domen.repositories

interface SharedPreferenceRepository {
    fun isFirstTime() : Boolean
    fun setIsFirstTime()
    fun setUrl(url : String)
    fun getUrl() : String

    fun getLang() : String
    fun setLang(lang : String)
}