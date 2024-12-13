package com.capstone.golap.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.golap.R
import com.capstone.golap.databinding.FragmentDashboardBinding
import com.capstone.golap.ui.Result
import com.capstone.golap.ui.ViewModelFactory
import com.capstone.golap.ui.detail.DashboardAdapter

class DashboardFragment : Fragment() {

    private lateinit var etBudget: EditText
    private lateinit var btnRecommend: Button
    private lateinit var tvNoResults: TextView
    private lateinit var rvLaptops: RecyclerView
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext(), "REST")
    }
    private lateinit var laptopAdapter: DashboardAdapter  // Use RecomResponseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        etBudget = binding.etBudget
        btnRecommend = binding.btnRecommend
        tvNoResults = binding.tvNoResults
        rvLaptops = binding.rvLaptops

        rvLaptops.layoutManager = LinearLayoutManager(requireContext())
        laptopAdapter = DashboardAdapter() // Initialize the adapter for RecomResponse
        rvLaptops.adapter = laptopAdapter

        btnRecommend.setOnClickListener {
            val budget = etBudget.text.toString().toIntOrNull()
            if (budget != null && budget > 0) {
                viewModel.fetchRecommendations(budget)
            } else {
                etBudget.error = getString(R.string.invalid_budget)
                rvLaptops.visibility = View.GONE
                tvNoResults.visibility = View.VISIBLE
                tvNoResults.text = getString(R.string.no_result)
            }
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.recommendations.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    rvLaptops.visibility = View.GONE
                    tvNoResults.visibility = View.GONE
                }
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        laptopAdapter.submitList(result.data)
                        rvLaptops.visibility = View.VISIBLE
                        tvNoResults.visibility = View.GONE
                    } else {
                        rvLaptops.visibility = View.GONE
                        tvNoResults.visibility = View.VISIBLE
                        tvNoResults.text = getString(R.string.no_result)
                    }
                }
                is Result.Error -> {
                    rvLaptops.visibility = View.GONE
                    tvNoResults.visibility = View.VISIBLE
                    tvNoResults.text = result.error
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


