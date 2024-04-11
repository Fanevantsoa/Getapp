package com.tco.getscan

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("student")
    fun getStudent():Call<List<DataStudent>>

    @GET("student/{id}")
    fun getOneStudent(@Path("id")id:String):Call<List<DataStudent>>

    @GET("event")
    fun getStudentPresence():Call<List<DataStudentPresence>>

    @GET("event/{id}")
    fun getOneStudentPresence(@Path("id")id:String):Call<List<DataStudentPresence>>
}