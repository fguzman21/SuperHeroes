package fernando.com.superheroes.Models



/**** getSuper Hero Id*****/
data class SuperHeroIdModel(val response:String, val id:Int?, val name:String?, val image: SuperHeroIdImage)
data class SuperHeroIdImage(val url: String?)



