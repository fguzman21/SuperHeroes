package com.bilcom.inamiki.DB

import com.google.gson.JsonObject
import fernando.com.superheroes.Models.SuperHeroIdModel
import retrofit2.Call
import retrofit2.http.*


interface RetroService {


    /*** CreateAccount ****/
    @GET
    fun GetSuperHeroId(@Url  url:String?): Call<SuperHeroIdModel>

}