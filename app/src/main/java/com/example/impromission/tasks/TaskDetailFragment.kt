package com.example.impromission.tasks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.impromission.R
import com.example.impromission.databinding.FragmentTaskDetailBinding
import com.example.impromission.databinding.FragmentTasksBinding
import com.example.impromission.tasks.db.TaskEntity
import com.example.impromission.tasks.viewModel.TasksViewModel
import kotlinx.coroutines.launch

class TaskDetailFragment : Fragment() {
    private lateinit var binding: FragmentTaskDetailBinding
    private val vm: TasksViewModel by viewModels()
    private var task: TaskEntity? = null
    private var isNewTask = false
    private var isTextChangedProgrammatically = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId = arguments?.getLong("taskId", -1L) ?: -1L
        if(taskId == -1L){
            task = TaskEntity(title = "", description = "")
            isNewTask = true
            setupTextWatchers()
        }else{
            vm.getTaskById(taskId).observe(viewLifecycleOwner) {task ->
                this.task = task
                task?.let {
                    isTextChangedProgrammatically = true
                    binding.editTitle.setTextKeepState(it.title)
                    binding.editDescription.setTextKeepState(it.description)
                    isTextChangedProgrammatically = false
                    setupTextWatchers()
                }
            }
        }
    }

    private fun setupTextWatchers(){
        val watcher = object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if (!isTextChangedProgrammatically) {
                    updateTask()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {}
        }
        binding.editTitle.addTextChangedListener(watcher)
        binding.editDescription.addTextChangedListener(watcher)
    }

    private fun updateTask() {
        val title = binding.editTitle.text.toString().trim()
        val description = binding.editDescription.text.toString().trim()

        viewLifecycleOwner.lifecycleScope.launch {
            if (isNewTask) {
                // Создаем новый объект каждый раз
                val newTask = TaskEntity(title = title, description = description)
                vm.addTask(newTask).observe(viewLifecycleOwner) { newId ->
                    // Обновляем ID после вставки
                    task = newTask.copy(id = newId)
                    isNewTask = false
                }
            } else {
                task?.let {
                    it.title = title
                    it.description = description
                    vm.updateTask(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(isNewTask && task?.title?.isEmpty() == true && task?.description?.isEmpty() == true){
            task?.let { vm.deleteTask(it) }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TaskDetailFragment()
    }
}