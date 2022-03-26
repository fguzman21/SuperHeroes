package fernando.com.superheroes.Views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bilcom.inamiki.DB.RetroInstance
import com.bilcom.inamiki.DB.RetroInstance.Companion.baseURL
import com.bilcom.inamiki.DB.RetroService
import fernando.com.superheroes.Models.SuperHeroIdImage
import fernando.com.superheroes.Models.SuperHeroIdModel
import fernando.com.superheroes.R
import fernando.com.superheroes.ViewModel.ViewModelSuperHeroList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class SuperHeroActivity : AppCompatActivity() {

    private var ln_back : LinearLayout? = null
    private var progressBar: ProgressBar? = null
    private var recyclerViewVista: RecyclerView? = null
    private lateinit var recyclerViewModel: ViewModelSuperHeroList
    private var  ListaSuperHeroes = ArrayList<SuperHeroIdModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_hero)
        recyclerViewVista = findViewById(R.id.recyclerViewVista)
        progressBar = findViewById(R.id.progressBar)
        initRecyclerView()
        getSuperHeroId(1)
        getSuperHeroId(2)
        getSuperHeroId(3)
        getSuperHeroId(4)

        recyclerViewVista!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getSuperHeroId(5)
                    getSuperHeroId(6)
                    getSuperHeroId(7)
                    getSuperHeroId(8)
                    getSuperHeroId(9)
                }
            }
        })
    }

    private fun initRecyclerView() {

        ListaSuperHeroes = ArrayList<SuperHeroIdModel>()
        recyclerViewVista?.apply {
            layoutManager = LinearLayoutManager(this@SuperHeroActivity)


            recyclerViewModel = ViewModelSuperHeroList(this@SuperHeroActivity)
            adapter = recyclerViewModel

            val decoration = DividerItemDecoration(applicationContext,
                StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(decoration)
        }
    }


    private fun getSuperHeroId(Id:Int?) {
        progressBar!!.visibility = View.VISIBLE
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = retroInstance.GetSuperHeroId(baseURL+Id).awaitResponse()
                Log.w("Respuesta_Ws", response.toString())
                if (response.isSuccessful) {

                    val data_ws = response.body()!!
                    Log.w("Respuesta_WsData", data_ws.toString())

                    withContext(Dispatchers.Main) {

                        /**** Listo General  General **/
                        ListaSuperHeroes.add(SuperHeroIdModel("200",1,data_ws.name,
                            SuperHeroIdImage(data_ws.image.url)
                        ))
                        recyclerViewModel.setListData(ListaSuperHeroes)
                        recyclerViewModel.notifyDataSetChanged()
                        recyclerViewVista!!.adapter = recyclerViewModel
                        Log.w("Respuesta_Nombre",data_ws.name!!)
                        eliminar_progess()

                    }

                }
            } catch (e: Exception) {
                Log.w("ErrrFavoritos", e.toString())
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        applicationContext,
                        "Seems like something went wrong...",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }


    fun eliminar_progess(){
        progressBar!!.visibility = View.GONE
    }
}