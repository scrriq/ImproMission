package com.example.impromission.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.impromission.R
import com.example.impromission.databinding.FragmentTasksBinding
import com.example.impromission.tasks.adapter.TaskAdapter
import com.example.impromission.tasks.db.TaskEntity
import com.example.impromission.tasks.viewModel.TasksViewModel

class TasksFragment : Fragment() {
    lateinit var binding: FragmentTasksBinding
    private val vm: TasksViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = TaskAdapter(emptyList()) { taskId ->
            findNavController().navigate(
                R.id.action_tasksFragment_to_taskDetailFragment,
                bundleOf("taskId" to taskId)
            )
        }

        binding.addTaskButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                R.id.action_tasksFragment_to_taskDetailFragment
            )
        }

        binding.taskRecyclerView.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TasksFragment.adapter
        }

        adapter.attachSwipeToDelete(binding.taskRecyclerView){
            task -> vm.deleteTask(task)
        }

        vm.allTasks.observe(viewLifecycleOwner){
            list -> adapter.submitList(list)
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = TasksFragment()
    }
}