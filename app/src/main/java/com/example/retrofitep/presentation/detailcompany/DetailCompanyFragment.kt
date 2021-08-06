package com.example.retrofitep.presentation.detailcompany

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.retrofitep.R
import com.example.retrofitep.base.event.EventObserver
import com.example.retrofitep.databinding.FragmentDetailCompanyBinding
import com.example.retrofitep.domain.companies.DetailCompany
import com.example.retrofitep.presentation.companies.hide
import com.example.retrofitep.presentation.companies.show
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailCompanyFragment : Fragment() {

    private val args: DetailCompanyFragmentArgs by navArgs()
    private val viewModel: DetailCompanyViewModel by viewModel { parametersOf(args.id) }

    private var _binding: FragmentDetailCompanyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner, { isLoading ->
            showOrHideLoading(isLoading)
        })
        viewModel.detailCompanyLiveData.observe(viewLifecycleOwner, { items ->
            setupViews(items)
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver { message ->
            showError(message)
        })
    }

    private fun setupViews(detailCompany: DetailCompany) {
        with(binding) {
            tvName.text = detailCompany.name
            Glide.with(requireContext())
                .load(detailCompany.img)
                .error(android.R.drawable.stat_notify_error)
                .into(ivImage)
            tvDescription.text = detailCompany.description
            tvLat.text = detailCompany.lat.toString()
            tvLon.text = detailCompany.lon.toString()
            tvSite.text = detailCompany.www
            tvPhone.text = detailCompany.phone
        }
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
