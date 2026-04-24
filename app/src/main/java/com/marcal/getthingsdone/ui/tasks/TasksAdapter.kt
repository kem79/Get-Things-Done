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
            }
            binding.toggleStar.isChecked = task.isStarred
            binding.checkBox.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> task.copy(isComplete = true)
                    else -> task.copy(isComplete = false)
                }
                listener.onTaskUpdated(updatedTask)
            }
            binding.toggleStar.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when(state) {
                    MaterialCheckBox.STATE_CHECKED -> task.copy(isStarred = true)
                    else -> task.copy(isStarred = false)
                }
                listener.onTaskUpdated(updatedTask)
            }
        }

    }

    interface TaskUpdateListener {
        fun onTaskUpdated(task: Task)
    }


}