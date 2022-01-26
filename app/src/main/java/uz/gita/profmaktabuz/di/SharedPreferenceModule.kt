package uz.gita.profmaktabuz.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.profmaktabuz.data.local.LocalDatabase
import uz.gita.profmaktabuz.domen.DataList
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferenceModule {

    @Provides
    @Singleton
    fun getSharedPreferences(@ApplicationContext context: Context) : LocalDatabase = LocalDatabase(context)


    @Provides
    @Singleton
    fun getDataList() = DataList()
}