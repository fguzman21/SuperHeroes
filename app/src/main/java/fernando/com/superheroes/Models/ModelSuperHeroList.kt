package fernando.com.superheroes.Models



/**** getSuperHero Id*****/
data class SuperHeroIdModel(val response:String, val id:Int?, val name:String?, val image: SuperHeroIdImage)
data class SuperHeroIdImage(val url: String?)


/**** getListSuperHero Values*****/
data class SuperHeroFeatures(val name:String?, val feature: String?)



