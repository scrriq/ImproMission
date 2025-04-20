package com.example.impromission

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.impromission.databinding.FragmentNotificationListBinding
import kotlinx.coroutines.flow.observeOn

class NotificationListFragment : Fragment() {
    private lateinit var viewModel: NotificationViewModel
    lateinit var binding: FragmentNotificationListBinding
    private val adapter = NotificationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRcView()
        initDb()
    }

    private fun initRcView(){
        binding.apply{
            notificationRcView.layoutManager = LinearLayoutManager(activity)
            notificationRcView.adapter = adapter
        }
    }

    private fun initDb(){
        val dao = NotificationDatabase.getDb(requireContext()).notificationDao() // init Dao
        val repo = NotificationRepository(dao)
        viewModel = ViewModelProvider(
            this,
            NotificationViewModelFactory(repo)
        )[NotificationViewModel::class.java]

        viewModel.notifications.observe(viewLifecycleOwner){list ->
            adapter.submitList(list)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationListFragment()
    }
}