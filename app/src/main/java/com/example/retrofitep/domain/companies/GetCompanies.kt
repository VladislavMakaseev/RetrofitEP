package com.example.retrofitep.domain.companies

import com.example.retrofitep.domain.OutputUseCase

class GetCompanies(
    private val companiesDataSource: CompaniesDataSource
) : OutputUseCase<List<Company>> {

    override suspend fun execute(): List<Company> {
        return companiesDataSource.getCompanies()
    }

}