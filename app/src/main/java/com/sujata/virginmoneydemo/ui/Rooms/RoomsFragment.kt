package com.sujata.virginmoneydemo.ui.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sujata.virginmoneydemo.commonUtils.ConnectivityUtil
import com.sujata.virginmoneydemo.databinding.FragmentRoomsBinding
import com.sujata.virginmoneydemo.framework.api.Status
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomsFragment : Fragment(), RoomsRecyclerAdapter.ItemClickListener {

    private var _binding: FragmentRoomsBinding? = null
    private var isConnected: Boolean = true

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val roomsViewModel: RoomsViewModel by activityViewModels()

    @Inject
    lateinit var adapter: RoomsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.itemClickListener = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        setupViews()
        roomsViewModel.roomsDataResponse.observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { roomsData ->
                            adapter.setListData(roomsData)
                            binding.recyclerviewRooms.adapter = adapter
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        val root: View = binding.root

        /*val textView: TextView = binding.textDashboard
        roomsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isConnected = ConnectivityUtil.isConnected(context)
        if (isConnected) {
            roomsViewModel.fetchRoomsData()
        } else {
            Toast.makeText(context, "No Internet Available", Toast.LENGTH_LONG).show()
        }

    }

    private fun setupViews() {
        val mLayoutManager = LinearLayoutManager(context)
        with(binding.recyclerviewRooms){
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    mLayoutManager.orientation
                )
            )
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int) {

    }
}