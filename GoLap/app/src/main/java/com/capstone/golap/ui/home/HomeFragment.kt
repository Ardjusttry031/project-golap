package com.capstone.golap.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.golap.databinding.FragmentHomeBinding
import com.capstone.golap.ui.Result
import com.capstone.golap.ui.ViewModelFactory
import com.capstone.golap.ui.dashboard.LaptopAdapter

class HomeFragment : Fragment() {

    private lateinit var imagePagerAdapter: ImagePagerAdapter
    private lateinit var laptopAdapter: LaptopAdapter
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext(), "REST")
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        imagePagerAdapter = ImagePagerAdapter(emptyList())
        binding.imageSlider.adapter = imagePagerAdapter

        laptopAdapter = LaptopAdapter(emptyList()) { laptop -> }
        binding.rvLaptops.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLaptops.adapter = laptopAdapter

        viewModel.fetchImageLinks()
        viewModel.fetchLaptops()

        viewModel.imageLinks.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    imagePagerAdapter = ImagePagerAdapter(result.data)
                    binding.imageSlider.adapter = imagePagerAdapter
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(result.error)
                }
            }
        })

        viewModel.laptops.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    laptopAdapter.updateLaptops(result.data)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showError(result.error)
                }
            }
        })

        return binding.root
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
