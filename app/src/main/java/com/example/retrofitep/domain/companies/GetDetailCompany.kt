package com.example.retrofitep.domain.companies

import com.example.retrofitep.domain.InputOutputUseCase

class GetDetailCompany(
    private val companiesDataSource: CompaniesDataSource
) : InputOutputUseCase<GetDetailCompany.Params, DetailCompany> {

    override suspend fun execute(params: Params): DetailCompany {
        return companiesDataSource.getDetailCompany(params.id)
    }

    class Params(
        val id: Long
    )

}