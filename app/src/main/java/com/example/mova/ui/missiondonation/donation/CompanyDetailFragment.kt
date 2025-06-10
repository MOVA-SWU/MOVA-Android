package com.example.mova.ui.missiondonation.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mova.data.source.remote.network.RetrofitClient
import com.example.mova.data.source.remote.repository.DonationRepository
import com.example.mova.databinding.FragmentCompanyDetailBinding
import com.example.mova.ui.extensions.load
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CompanyDetailFragment : Fragment() {

    private var _binding: FragmentCompanyDetailBinding? = null
    private val binding get() = _binding!!

    private val companyMovieAdapter = CompanyMovieAdapter()

    private val args: CompanyDetailFragmentArgs by navArgs()

    private val viewModel: DonationViewModel by viewModels {
        DonationViewModelFactory(DonationRepository(RetrofitClient.retrofitService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        binding.btnCompanyDetailBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnCompanyDetailDonate.setOnClickListener {
            findNavController().navigateUp()
        }
        setAdapter()
        setViewModel()
        binding.btnCompanyDetailDonate.setOnClickListener {
            putDonation()
        }
    }

    private fun setAdapter() {
        binding.rvCompanyDetailMovie.adapter = companyMovieAdapter
    }

    private fun setViewModel() {
        val companyId = args.company?.companyId
        companyId?.let {
            viewModel.loadCompanyDetail(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.companyDetailResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            val response = it.getOrNull()
                            with(binding) {
                                ivCompanyDetail.load(response?.bannerImage)
                                tvCompanyDetailName.text = args.company?.name
                                tvCompanyDetailContent.text = response?.explainText
                                btnCompanyDetailDonate.text = "${response?.supportCost} P"
                                companyMovieAdapter.submitList(response?.productionImages)
                            }
                        } else {
                            Toast.makeText(context, "제작 영화 불러오기 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    private fun putDonation() {
        val companyId = args.company?.companyId
        companyId?.let {
            viewModel.donationComplete(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.donationCompleteResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            Toast.makeText(context, "후원 되었습니다", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "포인트가 부족합니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}