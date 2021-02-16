package com.bintangshavira.appkantinugm.retrofit

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface API {

    @POST("appkantin/register.php")
    @FormUrlEncoded
    fun registeruser (@Field("username") username:String,
                      @Field("name") name:String,
                      @Field("password") password:String): Observable<String>


    @POST("index.php/user/login")
    @FormUrlEncoded
    fun loginUser (@Field("username") username:String,
                   @Field("password") password:String): Call<ResponseLogin>

    @GET("index.php/User/saldo_c/{key}")
    fun getsaldo(
            @Path("key") key: String?
    ): retrofit2.Call<ResponseSaldo>
}