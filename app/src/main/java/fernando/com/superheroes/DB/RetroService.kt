package com.bilcom.inamiki.DB

import fernando.com.superheroes.Models.SuperHeroIdModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface RetroService {


    /*** GetSuperHeroId ****/
    @GET
    fun GetSuperHeroId(@Url  url:String?): Call<SuperHeroIdModel>



    /*** GetSuperHeroFeatures ****/
    @GET
    fun GetSuperHeroFeatures(@Url  url:String?): Call<ResponseBody>



}