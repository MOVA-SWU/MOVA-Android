package com.example.mova.ui.missiondonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.mova.databinding.FragmentMissionDonationBinding
import com.example.mova.ui.extensions.dp
import com.google.android.material.tabs.TabLayoutMediator

class MissionDonationFragment : Fragment() {

    private var _binding: FragmentMissionDonationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissionDonationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTabLayout()
    }

    private fun setTabLayout() {
        val adapter = MissionDonationPagerAdapter(requireActivity())
        binding.viewpagerMissionDonation.adapter = adapter

        TabLayoutMediator(
            binding.tabMissionDonation, binding.viewpagerMissionDonation
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "미션"
                1 -> "후원"
                else -> ""
            }
        }.attach()
        setTabMargin()
    }

    private fun setTabMargin() {
        for (i in 0 until binding.tabMissionDonation.tabCount) {
            val tabStrip = binding.tabMissionDonation.getChildAt(0) as? LinearLayout
            val tabView = tabStrip?.getChildAt(i)
            val layoutParams = tabView?.layoutParams as? ViewGroup.MarginLayoutParams

            if (layoutParams != null) {
                layoutParams.marginStart = if (i == 0) 0 else 3.dp(requireContext())
                layoutParams.marginEnd =
                    if (i == binding.tabMissionDonation.tabCount - 1) 0 else 3.dp(requireContext())
                tabView.layoutParams = layoutParams
                tabView.requestLayout()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}