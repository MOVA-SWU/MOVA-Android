package com.example.mova.ui.missiondonation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mova.ui.missiondonation.donation.DonationFragment
import com.example.mova.ui.missiondonation.mission.MissionFragment

class MissionDonationPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MissionFragment()
            1 -> DonationFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}