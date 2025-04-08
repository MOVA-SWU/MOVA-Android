package com.example.mova.ui.missiondonation.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.data.model.Company
import com.example.mova.databinding.FragmentDonationBinding

class DonationFragment : Fragment() {

    private var  _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!

    private val companyAdapter = CompanyListAdapter(object: CompanyClickListener {
        override fun onCompanyClick(companyId: String) {
            (requireActivity() as AppCompatActivity)
                .findNavController(R.id.container_home).navigate(R.id.action_donation_to_company_detail)
        }
    })

    val dummyCompanies = listOf(
        Company(1, "월트디즈니컴퍼니코리아 유한책임회사", "디즈니컴퍼니코리아 유한책임회사는 월트디즈니컴퍼니디즈니 코리아 유한책임회사는 월트디즈니컴퍼니코리아 유한책임회사사는월트디즈니컴퍼니코리아 유한책임회사는", "")
    )

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
        companyAdapter.submitList(dummyCompanies)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}