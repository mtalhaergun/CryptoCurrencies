package com.example.cryptocurrencies.ui.home

import android.os.Bundle
import android.widget.Toast
import com.example.cryptocurrencies.R
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.cryptocurrencies.base.BaseFragment
import com.example.cryptocurrencies.databinding.FragmentHomeBinding
import com.example.cryptocurrencies.model.home.Data
import com.example.cryptocurrencies.utils.Constants.API_KEY
import com.example.cryptocurrencies.utils.Constants.LIMIT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getData(API_KEY, LIMIT)

    }

    override fun onCreateFinished() {
    }

    override fun initializeListeners() {
    }

    override fun observeEvents() {
        with(viewModel){
            cryptoResponse.observe(viewLifecycleOwner, Observer {
                it?.let {
                    setRecycler(it.data as List<Data>)
                }
            })

            isLoading.observe(viewLifecycleOwner, Observer {
                handleViews(it)
            })

            onError.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
            })
        }

    }



    private fun setRecycler(data : List<Data>){
        val mAdapter = HomeRecyclerAdapter(object : ItemClickListener{
            override fun onItemClick(coin: Data) {
                if (coin.symbol != null){
                    val navigation = HomeFragmentDirections.actionHomeFragmentToDetailFragment(coin.symbol)
                    Navigation.findNavController(requireView()).navigate(navigation)
                }
            }

        })
        binding.rvHome.adapter = mAdapter
        mAdapter.setList(data)
    }

    private fun handleViews(isLoading : Boolean = false){
        binding.rvHome.isVisible = !isLoading
        binding.pbHome.isVisible = isLoading
    }

}