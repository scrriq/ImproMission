package com.example.impromission.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
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

        adapter = TaskAdapter(emptyList(), ::showEditDialog)
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

        binding.addTaskButton.setOnClickListener{
            showAddDialog()
        }
    }
    private fun showAddDialog(){
        val edit = EditText(requireContext())
        AlertDialog.Builder(requireContext())
            .setTitle("Новая задача")
            .setView(edit)
            .setPositiveButton("Добавить"){
                _, _, ->
                val text = edit.text.toString().trim()
                if(text.isNotEmpty()) vm.addTask(text)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showEditDialog(task: TaskEntity){
        val edit = EditText(requireContext()).apply { setText(task.text) }
        AlertDialog.Builder(requireContext())
            .setTitle("Редактировать задачу")
            .setView(edit)
            .setPositiveButton("Сохранить"){
                _, _, ->
                val newText = edit.text.toString().trim()
                if(newText.isNotEmpty() && newText != task.text){
                    task.text = newText
                    vm.updateTask(task)
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }



    companion object {
        @JvmStatic
        fun newInstance() = TasksFragment()
    }
}