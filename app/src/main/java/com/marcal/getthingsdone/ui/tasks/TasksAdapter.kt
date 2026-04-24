package com.marcal.getthingsdone.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.marcal.getthingsdone.data.Task
import com.marcal.getthingsdone.databinding.ItemTaskBinding

class TasksAdapter(private val listener: TaskUpdateListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    private var tasks: List<Task> = listOf()
    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(tasks[position])
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textViewTaskTitle.text = task.title
            binding.textViewTaskDetails.text = task.description
            binding.checkBox.isChecked = task.isComplete
            if (task.isComplete) {
                binding.textViewTaskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewTaskDetails.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewTaskTitle.paintFlags = 0
                binding.textViewTaskDetails.paintFlags = 0
            }
            binding.toggleStar.isChecked = task.isStarred

            binding.toggleStar.setOnClickListener {
                val updatedTask = task.copy(isStarred = binding.toggleStar.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
            binding.checkBox.setOnClickListener {
                val updatedTask = task.copy(isComplete = binding.checkBox.isChecked)
                listener.onTaskUpdated(updatedTask)
            }
        }

    }

    interface TaskUpdateListener {
        fun onTaskUpdated(task: Task)
    }


}