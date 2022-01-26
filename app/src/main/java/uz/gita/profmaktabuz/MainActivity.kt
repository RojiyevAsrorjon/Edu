package uz.gita.profmaktabuz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.profmaktabuz.data.local.LocalDatabase
import uz.gita.profmaktabuz.domen.MyContextWrapper

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager.findFragmentById(R.id.fragmentController) as NavHostFragment
        val graph = host.navController.navInflater.inflate(R.navigation.navigation)
        host.navController.graph = graph
    }

    override fun attachBaseContext(newBase: Context?) {
        val pref = LocalDatabase(newBase!!)
        val lang = pref.lang
        super.attachBaseContext(MyContextWrapper.wrap(newBase, lang))
    }

}