package com.example.retrofitep.di

import com.example.retrofitep.data.companies.CompaniesRepository
import com.example.retrofitep.data.companies.NetworkService
import com.example.retrofitep.domain.companies.CompaniesDataSource
import com.example.retrofitep.domain.companies.GetCompanies
import com.example.retrofitep.domain.companies.GetDetailCompany
import com.example.retrofitep.presentation.companies.CompaniesViewModel
import com.example.retrofitep.presentation.detailcompany.DetailCompanyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val companyModule = module {

    single {
        NetworkService.createApi()
    }

    single<CompaniesDataSource> {
        CompaniesRepository(
            api = get()
        )
    }

    single {
        GetCompanies(
            companiesDataSource = get()
        )
    }

    single {
        GetDetailCompany(
            companiesDataSource = get()
        )
    }

    viewModel {
        CompaniesViewModel(
            getCompanies = get()
        )
    }

    viewModel { (id: Long) ->
        DetailCompanyViewModel(
            id = id,
            getDetailCompany = get()
        )
    }

}