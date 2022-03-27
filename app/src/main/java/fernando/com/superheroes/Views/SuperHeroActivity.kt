package fernando.com.superheroes.Views

import android.os.Bundle
import android.util.Log
import android.view.View
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

    private var progressBar: ProgressBar? = null
    private var recyclerViewVista: RecyclerView? = null
    private lateinit var recyclerViewModel: ViewModelSuperHeroList
    private var  ListaSuperHeroes = ArrayList<SuperHeroIdModel>()
    private var Inicio : Int? = 1
    private var Fin : Int? = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_hero)
        recyclerViewVista = findViewById(R.id.recyclerViewVista)
        progressBar = findViewById(R.id.progressBar)
        initRecyclerView()
        recyclerViewVista!!.adapter = recyclerViewModel
        /*Función para traer a los superheroes con los parametros iniciales*/
        getSuperHeroGeneral(Inicio,Fin)




        recyclerViewVista!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {

                    Log.w("Inicio",Inicio.toString());
                    Log.w("Inicio_Fin",Fin.toString());
                    /*Función para traer a los superheroes con los nuevos parametros*/
                    getSuperHeroGeneral(Inicio,Fin)
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

    private fun  getSuperHeroGeneral(inicio_request: Int?, fin_request: Int?) {
        var lugar = 1
        for(id_superhero in inicio_request!!..fin_request!!){
                getSuperHeroId(id_superhero,lugar)
                lugar++
        }
        this.Inicio = this.Inicio?.plus(5)
        this.Fin = this.Fin?.plus(5)
    }

    private fun getSuperHeroId(Id: Int?, lugar: Int) {
        progressBar!!.visibility = View.VISIBLE
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = retroInstance.GetSuperHeroId(baseURL+Id).awaitResponse()
                Log.w("Respuesta_Ws"+Id, response.toString())
                if (response.isSuccessful) {

                    val data_ws = response.body()!!
                    Log.w("Respuesta_WsData", data_ws.toString())

                    withContext(Dispatchers.Main) {

                        if(data_ws.response.equals("success")){
                            /**** Se agrega nuevo superheroe a la lista para despues mostrarlo **/
                            ListaSuperHeroes.add(SuperHeroIdModel(data_ws.response,data_ws.id,data_ws.name,
                                SuperHeroIdImage(data_ws.image.url)
                            ))
                            /*** Se ordena la lista de superheroes por ID **/
                            OrdenarLista(lugar!!)
                        }


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

    fun OrdenarLista(lugar: Int){
        ListaSuperHeroes.sortBy { list -> list.id }

        if(lugar == 5){
            recyclerViewModel.setListData(ListaSuperHeroes)
            recyclerViewModel.notifyDataSetChanged()
            progressBar!!.visibility = View.GONE
        }


    }
}