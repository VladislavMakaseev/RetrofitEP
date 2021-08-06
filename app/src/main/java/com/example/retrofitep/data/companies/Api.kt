package com.example.retrofitep.data.companies

import com.example.retrofitep.data.companies.pojo.Company
import com.example.retrofitep.data.companies.pojo.DetailCompany
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("test_task/test.php")
    fun getCompanies(): Call<List<Company>>

    @GET("test_task/test.php")
    fun getDetailCompanyById(@Query("id") id: Long): Call<List<DetailCompany>>

}