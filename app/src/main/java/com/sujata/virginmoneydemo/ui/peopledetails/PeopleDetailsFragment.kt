package com.sujata.virginmoneydemo.ui.peopledetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.sujata.virginmoneydemo.databinding.FragmentPeoplesDetailsBinding
import com.sujata.virginmoneydemo.loadImageFromUrl
import com.sujata.virginmoneydemo.ui.peoples.PeoplesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PeopleDetailsFragment : Fragment() {

    private var _binding: FragmentPeoplesDetailsBinding? = null

    private val viewModel: PeoplesViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeoplesDetailsBinding.inflate(inflater, container, false)
/*
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
*/
        /*val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateData()
    }

    private fun populateData() {
        val args: PeopleDetailsFragmentArgs by navArgs()
        viewModel.peoplesDataResponse.value?.run {
            data?.get(args.position)?.run {
                binding.newsImageIV.loadImageFromUrl(this.avatar)
                binding.authorNameTV.text = "$firstName $lastName"
                binding.publishedDateTV.text = email

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}