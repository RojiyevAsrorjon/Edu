package uz.gita.profmaktabuz.di.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.profmaktabuz.domen.repositories.LanguageRepository
import uz.gita.profmaktabuz.domen.repositories.SharedPreferenceRepository
import uz.gita.profmaktabuz.domen.repositories.impl.LanguageRepositoryImpl
import uz.gita.profmaktabuz.domen.repositories.impl.SharedPreferenceRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getSharedRepository(sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl): SharedPreferenceRepository

    @Binds
    @Singleton
    fun getLanguageRepository(languageRepositoryImpl: LanguageRepositoryImpl): LanguageRepository

}