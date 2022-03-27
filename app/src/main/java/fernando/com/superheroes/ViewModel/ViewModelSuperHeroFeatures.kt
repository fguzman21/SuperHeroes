package fernando.com.superheroes.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import fernando.com.superheroes.Models.SuperHeroFeatures
import fernando.com.superheroes.R

class ViewModelSuperHeroFeatures(contexto: Context) : RecyclerView.Adapter<ViewModelSuperHeroFeatures.MyViewHolder>() {

    var items = ArrayList<SuperHeroFeatures>()
    var mContext : Context?   = contexto

    fun setListData(data: ArrayList<SuperHeroFeatures>) {
        this.items = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero_feature, parent, false)
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

        val txt_nombre = view.findViewById<TextView>(R.id.txt_nombre)
        val txt_caracteristica = view.findViewById<TextView>(R.id.txt_caracteristica)
        val micontexto = contexto


        @SuppressLint("ResourceType")
        fun bind(data: SuperHeroFeatures) {

            /*** Lectura datos  ****/
            txt_nombre.text = data.name
            txt_caracteristica.text = data.feature
        }


    }

}