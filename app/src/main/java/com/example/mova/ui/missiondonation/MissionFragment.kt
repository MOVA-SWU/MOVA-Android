package com.example.mova.ui.missiondonation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.data.model.Mission
import com.example.mova.databinding.FragmentMissionBinding
import com.example.mova.ui.home.MovieClickListener

class MissionFragment : Fragment() {

    private var  _binding: FragmentMissionBinding? = null
    private val binding get() = _binding!!

    private val missionAdapter = MissionListAdapter(object: MovieClickListener {
        override fun onMovieClick(movieId: String) {
            findNavController().navigate(R.id.action_mission_to_movie_detail)
        }
    })

    val dummyMissions = listOf(
        Mission(1, "잔디 청소하고", 50),
        Mission(2, "달나라 쓰레기 줍고", 20),
        Mission(3, "흙 치우기하고", 300),
        Mission(4, "꽃 심어주고", 20),
        Mission(5, "수영장 청소하고", 50),
        Mission(6, "수영장 청소하고", 50),
        Mission(7, "수영장 청소하고", 50),
        Mission(8, "수영장 청소하고", 50),
        Mission(9, "수영장 청소하고", 50)
    )

    val dummyMissions2 = listOf(
        Mission(1, "길거리 쓰레기 줍고", 50),
        Mission(2, "부모님과 대화하고", 20)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        setAdapter()
        setRadioButton()
    }

    private fun setAdapter() {
        binding.tvMissionListNull.isVisible = dummyMissions.isEmpty()
        binding.rvMissionList.adapter = missionAdapter
        missionAdapter.submitList(dummyMissions)
    }

    private fun setRadioButton() {
        binding.btnRadioMission.setOnCheckedChangeListener { _, checkedId ->
            val missionList = if (checkedId == R.id.btn_radio_possible) {
                dummyMissions
            } else {
                dummyMissions2
            }

            missionAdapter.submitList(missionList)

            binding.tvMissionListNull.isVisible =
                (checkedId == R.id.btn_radio_possible && missionList.isEmpty())
            binding.tvMissionListCompleteNull.isVisible =
                (checkedId == R.id.btn_radio_impossible && missionList.isEmpty())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}