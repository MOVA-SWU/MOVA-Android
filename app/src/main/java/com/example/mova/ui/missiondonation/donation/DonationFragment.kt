package com.example.mova.ui.missiondonation.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.mova.R
import com.example.mova.data.model.response.CompanyListResponse
import com.example.mova.databinding.FragmentDonationBinding
import com.example.mova.ui.missiondonation.MissionDonationFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DonationFragment : Fragment() {

    private var  _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    private val companyAdapter = CompanyListAdapter(object: CompanyClickListener {
        override fun onCompanyClick(company: CompanyListResponse) {
            val action = MissionDonationFragmentDirections.actionDonationToCompanyDetail(company = company)
            (requireActivity() as AppCompatActivity)
                .findNavController(R.id.container_home)
                .navigate(action)
        }
    })

    private val viewModel: DonationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        setAdapter()
    }

    private fun setAdapter() {
        binding.rvDonationList.adapter = companyAdapter
        viewModel.loadMovieList()
        setViewModel()
    }

    private fun setViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.companyListResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    val list = result.getOrNull()
                    companyAdapter.submitList(list)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}