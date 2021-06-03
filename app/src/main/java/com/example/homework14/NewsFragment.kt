package com.example.homework14

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework14.databinding.FragmentMainBinding


class NewsFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val myAdapter = NewsRecyclerAdapter()

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

    }

    private fun init() {
        setupRecyclerView()
        newsViewModel.init()
        listeners()

        binding.mySwipe.setOnRefreshListener {
            myAdapter.clearData()
            newsViewModel.init()
        }
    }

    private fun listeners() {
        newsViewModel.newsDataLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResponseHandler.Loading -> binding.mySwipe.isRefreshing = it.loading
                is ResponseHandler.Success -> myAdapter.setData(it.data!!.toMutableList())
                is ResponseHandler.Error -> Toast.makeText(
                        requireActivity(),
                        "${it.errorMessage}",
                        Toast.LENGTH_SHORT
                ).show()
            }
        })

        newsViewModel.newGeneratedNews.observe(viewLifecycleOwner, {
            myAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    private fun setupRecyclerView() {
        binding.myRecycler.apply {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}