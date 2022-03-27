package fernando.com.superheroes.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import fernando.com.superheroes.Models.SuperHeroIdModel
import fernando.com.superheroes.R
import fernando.com.superheroes.Views.SuperHeroProfile

class ViewModelSuperHeroList(contexto: Context) : RecyclerView.Adapter<ViewModelSuperHeroList.MyViewHolder>() {

    var items = ArrayList<SuperHeroIdModel>()
    var mContext : Context?   = contexto

    fun setListData(data: ArrayList<SuperHeroIdModel>) {
        this.items = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return MyViewHolder(inflater,mContext!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }



    class MyViewHolder(
        view: View,
        contexto: Context
    ): RecyclerView.ViewHolder(view) {

        val img_superheroe = view.findViewById<CircleImageView>(R.id.img_superheroe)
        val txt_nombre = view.findViewById<TextView>(R.id.txt_nombre)
        val btn_ir = view.findViewById<ImageButton>(R.id.btn_ir)
        val micontexto = contexto


        @SuppressLint("ResourceType")
        fun bind(data: SuperHeroIdModel) {

            /*** Lectura datos Reverso ****/
            txt_nombre.text = data.name
            Picasso.with(micontexto).load(data.image.url).fit().centerCrop()
                    .into(img_superheroe)

            btn_ir.setOnClickListener{
                var intent = Intent(micontexto, SuperHeroProfile::class.java)
                intent.putExtra("id_superheroe",data.id.toString())
                intent.putExtra("nombre_superheroe",data.name)
                intent.putExtra("imagen_perfil",data.image.url)
                micontexto.startActivity(intent)
            }
        }


    }

}