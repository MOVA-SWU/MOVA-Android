package com.example.mova.ui.missiondonation.mission

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.mova.R
import com.example.mova.data.model.response.MissionListResponse
import com.example.mova.data.source.remote.network.RetrofitClient
import com.example.mova.data.source.remote.repository.MissionRepository
import com.example.mova.databinding.FragmentMissionBinding
import com.example.mova.ui.missiondonation.MissionDonationFragmentDirections
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MissionFragment : Fragment() {

    private var  _binding: FragmentMissionBinding? = null
    private val binding get() = _binding!!

    private val missionAdapter = MissionListAdapter(object: MissionClickListener {
        override fun onMissionClick(mission: MissionListResponse) {
            val action = MissionDonationFragmentDirections.actionMissionToMovieDetail(null, mission = mission)
            (requireActivity() as AppCompatActivity)
                .findNavController(R.id.container_home)
                .navigate(action)
        }
    })

    private val viewModel: MissionViewModel by viewModels {
        MissionViewModelFactory(MissionRepository(RetrofitClient.retrofitService))
    }

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
        binding.rvMissionList.adapter = missionAdapter
        observeMissionList()
        viewModel.loadMissionAvailableList()
    }

    private fun setRadioButton() {
        binding.btnRadioMission.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.btn_radio_possible) {
                missionAdapter.isAvailableMode = true
                viewModel.loadMissionAvailableList()
            } else {
                missionAdapter.isAvailableMode = false
                viewModel.loadMissionCompleteList()
            }
        }
    }

    private fun observeMissionList() {
        viewModel.loadPointSum()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.missionListResponse.collectLatest { result ->
                        val list = result.getOrNull()
                        missionAdapter.submitList(list)

                        if (binding.btnRadioPossible.isChecked) {
                            if (list.isNullOrEmpty()) {
                                binding.tvMissionListNull.visibility = View.VISIBLE
                                binding.rvMissionList.visibility = View.GONE
                            } else {
                                binding.tvMissionListNull.visibility = View.GONE
                                binding.rvMissionList.visibility = View.VISIBLE
                            }
                        } else {
                            if (list.isNullOrEmpty()) {
                                binding.tvMissionListCompleteNull.visibility = View.VISIBLE
                                binding.rvMissionList.visibility = View.GONE
                            } else {
                                binding.tvMissionListCompleteNull.visibility = View.GONE
                                binding.rvMissionList.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                launch {
                    viewModel.pointSumResponse.collectLatest { result ->
                        result?.let {
                            if (it.isSuccess) {
                                binding.tvMissionPointField.text = "${it.getOrNull()?.totalPoints} P"
                            } else {
                                Toast.makeText(context, "포인트 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
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