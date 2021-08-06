package com.example.retrofitep.presentation.companies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.retrofitep.R
import com.example.retrofitep.base.event.EventObserver
import com.example.retrofitep.databinding.FragmentCompaniesBinding
import com.example.retrofitep.domain.companies.Company
import com.example.retrofitep.presentation.companies.delegate.CompanyDelegate
import org.koin.androidx.viewmodel.ext.android.viewModel

class CompaniesFragment : Fragment() {

    private var _binding: FragmentCompaniesBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }
    private val viewModel: CompaniesViewModel by viewModel()

    private lateinit var adapter: CompaniesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompaniesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        adapter = CompaniesAdapter()
        adapter.delegatesManager
            .addDelegate(
                CompanyDelegate(
                    requireContext(),
                    object : CompanyDelegate.OnClickListener {
                        override fun onItemClick(company: Company, position: Int) {
                            company.id?.let {
                                navController.navigate(
                                    CompaniesFragmentDirections.actionCompaniesFragmentToDetailCompanyFragment(
                                        it
                                    )
                                )
                            }
                        }
                    })
            )
        binding.rvCompanies.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, { isLoading ->
            showOrHideLoading(isLoading)
        })
        viewModel.companiesLiveData.observe(viewLifecycleOwner, { items ->
            adapter.clear()
            adapter.add(items)
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver { message ->
            showError(message)
        })
    }

    private fun showError(message: String?) {
        val errorMessage = message ?: getString(R.string.error_unknown)
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun showOrHideLoading(isLoading: Boolean, @IdRes resId: Int = R.id.progressBar) {
        val progressBar = view?.findViewById<ProgressBar>(resId)
        if (isLoading) {
            progressBar?.show()
        } else {
            progressBar?.hide()
        }
    }

}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}