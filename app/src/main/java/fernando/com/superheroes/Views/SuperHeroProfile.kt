package fernando.com.superheroes.Views

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bilcom.inamiki.DB.RetroInstance
import com.bilcom.inamiki.DB.RetroService
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import fernando.com.superheroes.Models.SuperHeroFeatures
import fernando.com.superheroes.Models.SuperHeroIdModel
import fernando.com.superheroes.R
import fernando.com.superheroes.ViewModel.ViewModelSuperHeroFeatures
import fernando.com.superheroes.ViewModel.ViewModelSuperHeroList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.awaitResponse


class SuperHeroProfile : AppCompatActivity() {

    private var ln_back : LinearLayout? = null
    private var img_perfil : CircleImageView? = null;
    private var txt_nombre: TextView? = null
    private var recyclerViewPowerStats: RecyclerView? = null;  var recyclerViewBiography: RecyclerView? = null;
    var recyclerViewAppearance: RecyclerView? = null; var recyclerViewWork: RecyclerView? = null; var recyclerViewConnections: RecyclerView? = null
    private lateinit var recyclerViewModel: ViewModelSuperHeroFeatures; lateinit var recyclerViewBiographyModel: ViewModelSuperHeroFeatures;
    lateinit var recyclerViewAppearanceModel: ViewModelSuperHeroFeatures;lateinit var recyclerViewWorkModel: ViewModelSuperHeroFeatures;
    lateinit var recyclerViewConnectionsModel: ViewModelSuperHeroFeatures;
    private var  ListaCaracteristicas = ArrayList<SuperHeroFeatures>()
    private var scroll_vista  : ScrollView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_hero_profile)
        ln_back = findViewById(R.id.ln_back)
        img_perfil = findViewById(R.id.img_perfil)
        txt_nombre = findViewById(R.id.txt_nombre)
        recyclerViewPowerStats = findViewById(R.id.recyclerViewPowerStats)
        recyclerViewPowerStats?.setNestedScrollingEnabled(false);
        recyclerViewBiography = findViewById(R.id.recyclerViewBiography)
        recyclerViewBiography?.setNestedScrollingEnabled(false);
        recyclerViewAppearance = findViewById(R.id.recyclerViewAppearance)
        recyclerViewAppearance?.setNestedScrollingEnabled(false);
        recyclerViewWork = findViewById(R.id.recyclerViewWork)
        recyclerViewWork?.setNestedScrollingEnabled(false);
        recyclerViewConnections = findViewById(R.id.recyclerViewConnections)
        recyclerViewConnections?.setNestedScrollingEnabled(false);
        scroll_vista = findViewById(R.id.scroll_vista)
        scroll_vista?.fullScroll(ScrollView.FOCUS_UP);
        initRecyclerView()

        /***Obtenemos valores de la vista anterior ***/
        txt_nombre?.text = intent.getStringExtra("nombre_superheroe")
        Picasso.with(applicationContext).load(intent.getStringExtra("imagen_perfil")).fit().centerCrop()
            .into(img_perfil)
        getSuperHeroFeatures(intent.getStringExtra("id_superheroe")?.toInt(),"powerstats")
        getSuperHeroFeatures(intent.getStringExtra("id_superheroe")?.toInt(),"biography")
        getSuperHeroFeatures(intent.getStringExtra("id_superheroe")?.toInt(),"appearance")
        getSuperHeroFeatures(intent.getStringExtra("id_superheroe")?.toInt(),"work")
        getSuperHeroFeatures(intent.getStringExtra("id_superheroe")?.toInt(),"connections")

        /** Finalizamos actividad actual **/
        ln_back?.setOnClickListener{
            finish()
        }
    }


    private fun initRecyclerView() {


        recyclerViewPowerStats?.apply {
            layoutManager = LinearLayoutManager(this@SuperHeroProfile)

            recyclerViewModel = ViewModelSuperHeroFeatures(this@SuperHeroProfile)
            adapter = recyclerViewModel

            val decoration = DividerItemDecoration(applicationContext,
                StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(decoration)
        }

        recyclerViewBiography?.apply {
            layoutManager = LinearLayoutManager(this@SuperHeroProfile)

            recyclerViewBiographyModel = ViewModelSuperHeroFeatures(this@SuperHeroProfile)
            adapter = recyclerViewBiographyModel

            val decoration = DividerItemDecoration(applicationContext,
                StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(decoration)
        }

        recyclerViewAppearance?.apply{
            layoutManager = LinearLayoutManager(this@SuperHeroProfile)

            recyclerViewAppearanceModel = ViewModelSuperHeroFeatures(this@SuperHeroProfile)
            adapter = recyclerViewAppearanceModel

            val decoration = DividerItemDecoration(applicationContext,
                    StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(decoration)
        }

        recyclerViewWork?.apply{
            layoutManager = LinearLayoutManager(this@SuperHeroProfile)

            recyclerViewWorkModel = ViewModelSuperHeroFeatures(this@SuperHeroProfile)
            adapter = recyclerViewWorkModel

            val decoration = DividerItemDecoration(applicationContext,
                    StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(decoration)
        }

        recyclerViewConnections?.apply{
            layoutManager = LinearLayoutManager(this@SuperHeroProfile)

            recyclerViewConnectionsModel = ViewModelSuperHeroFeatures(this@SuperHeroProfile)
            adapter = recyclerViewConnectionsModel

            val decoration = DividerItemDecoration(applicationContext,
                    StaggeredGridLayoutManager.VERTICAL
            )
            addItemDecoration(decoration)
        }

    }


    private fun getSuperHeroFeatures(Id:Int?, type:String?) {
      //  progressBar!!.visibility = View.VISIBLE
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = retroInstance.GetSuperHeroFeatures(RetroInstance.baseURL +Id+"/"+type).awaitResponse()
                Log.w("Respuesta_Ws", response.raw().toString())

                if (response.isSuccessful) {
                    val jsonObject = JSONObject(response.body()!!.string())
                    Log.w("Respuesta_jsonObjects", jsonObject.toString())

                    withContext(Dispatchers.Main) {

                        when(type){
                             "powerstats"->{
                                 ListaCaracteristicas = ArrayList<SuperHeroFeatures>()
                                 ListaCaracteristicas.add(SuperHeroFeatures("intelligence:",jsonObject.get("intelligence").toString()))
                                 ListaCaracteristicas.add(SuperHeroFeatures("strength:",jsonObject.get("strength").toString()))
                                 ListaCaracteristicas.add(SuperHeroFeatures("speed:",jsonObject.get("speed").toString()))
                                 ListaCaracteristicas.add(SuperHeroFeatures("durability:",jsonObject.get("durability").toString()))
                                 ListaCaracteristicas.add(SuperHeroFeatures("power:",jsonObject.get("power").toString()))
                                 ListaCaracteristicas.add(SuperHeroFeatures("combat:",jsonObject.get("combat").toString()))
                                 recyclerViewModel.setListData(ListaCaracteristicas)
                                 recyclerViewModel.notifyDataSetChanged()
                                 recyclerViewPowerStats!!.adapter = recyclerViewModel
                            }
                            "biography"->{
                                ListaCaracteristicas = ArrayList<SuperHeroFeatures>()
                                ListaCaracteristicas.add(SuperHeroFeatures("full-name:",jsonObject.get("full-name").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("alter-egos:",jsonObject.get("alter-egos").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("aliases:",jsonObject.get("aliases").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("place-of-birth:",jsonObject.get("place-of-birth").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("first-appearance:",jsonObject.get("first-appearance").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("publisher:",jsonObject.get("publisher").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("alignment:",jsonObject.get("alignment").toString()))
                                recyclerViewBiographyModel.setListData(ListaCaracteristicas)
                                recyclerViewBiographyModel.notifyDataSetChanged()
                                recyclerViewBiography!!.adapter = recyclerViewBiographyModel
                            }"appearance"->{
                                ListaCaracteristicas = ArrayList<SuperHeroFeatures>()
                                ListaCaracteristicas.add(SuperHeroFeatures("gender:",jsonObject.get("gender").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("race:",jsonObject.get("race").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("height:",jsonObject.get("height").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("weight:",jsonObject.get("weight").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("eye-color:",jsonObject.get("eye-color").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("hair-color:",jsonObject.get("hair-color").toString()))
                                recyclerViewAppearanceModel.setListData(ListaCaracteristicas)
                                recyclerViewAppearanceModel.notifyDataSetChanged()
                                recyclerViewAppearance!!.adapter = recyclerViewAppearanceModel
                            }"work"->{
                                ListaCaracteristicas = ArrayList<SuperHeroFeatures>()
                                ListaCaracteristicas.add(SuperHeroFeatures("occupation:",jsonObject.get("occupation").toString()))
                                recyclerViewWorkModel.setListData(ListaCaracteristicas)
                                recyclerViewWorkModel.notifyDataSetChanged()
                                recyclerViewWork!!.adapter = recyclerViewWorkModel
                            }"connections"->{
                                ListaCaracteristicas = ArrayList<SuperHeroFeatures>()
                                ListaCaracteristicas.add(SuperHeroFeatures("group-affiliation:",jsonObject.get("group-affiliation").toString()))
                                ListaCaracteristicas.add(SuperHeroFeatures("relatives:",jsonObject.get("relatives").toString()))
                                recyclerViewConnectionsModel.setListData(ListaCaracteristicas)
                                recyclerViewConnectionsModel.notifyDataSetChanged()
                                recyclerViewConnections!!.adapter = recyclerViewConnectionsModel
                            }
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




    fun eliminar_progess(){
     //   progressBar!!.visibility = View.GONE
    }
}