package com.example.retrofitep.data.companies

import com.example.retrofitep.domain.companies.CompaniesDataSource
import com.example.retrofitep.domain.companies.Company
import com.example.retrofitep.domain.companies.DetailCompany
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.example.retrofitep.data.companies.pojo.Company as PojoCompany
import com.example.retrofitep.data.companies.pojo.DetailCompany as PojoDetailCompany

class CompaniesRepository(
    private val api: Api,
) : CompaniesDataSource {

    override suspend fun getCompanies(): List<Company> {
        val listOfPojoCompanies: List<PojoCompany> = suspendCoroutine { coroutine ->
            val call = api.getCompanies()
            call.enqueue(object : Callback<List<PojoCompany>> {
                override fun onResponse(
                    call: Call<List<PojoCompany>>,
                    response: Response<List<PojoCompany>>
                ) {
                    if (response.isSuccessful) {
                        val companies = response.body()
                        companies?.let {
                            coroutine.resume(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<PojoCompany>>, t: Throwable) {
                    Timber.d("kek:: Error $t")
                }
            })
        }

        return listOfPojoCompanies.map {
            Company(
                id = it.id?.toLong(),
                name = it.name,
                img = "https://lifehack.studio/test_task/${it.img}"
            )
        }
    }

    override suspend fun getDetailCompany(id: Long): DetailCompany {
        val call = api.getDetailCompanyById(id)
        val listOfPojoDetailCompany: List<PojoDetailCompany> = suspendCoroutine { coroutine ->
            call.enqueue(object : Callback<List<PojoDetailCompany>> {
                override fun onResponse(
                    call: Call<List<PojoDetailCompany>>,
                    response: Response<List<PojoDetailCompany>>
                ) {
                    if (response.isSuccessful) {
                        val detailCompany = response.body()
                        detailCompany?.let {
                            coroutine.resume(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<PojoDetailCompany>>, t: Throwable) {
                    Timber.d("kek:: Error $t")
                }

            })
        }

        return if (listOfPojoDetailCompany.isNotEmpty()) {
            DetailCompany(
                id = listOfPojoDetailCompany.first().id?.toLong(),
                name = listOfPojoDetailCompany.first().name,
                img = "https://lifehack.studio/test_task/${listOfPojoDetailCompany.first().img}",
                description = listOfPojoDetailCompany.first().description,
                lat = listOfPojoDetailCompany.first().lat,
                lon = listOfPojoDetailCompany.first().lon,
                www = listOfPojoDetailCompany.first().www,
                phone = listOfPojoDetailCompany.first().phone
            )
        } else {
            DetailCompany(
                id = 0,
                name = "",
                img = "",
                description = "",
                lat = 0f,
                lon = 0f,
                www = "",
                phone = ""
            )
        }

    }

}