package com.marcal.getthingsdone.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcal.getthingsdone.data.Task
import com.marcal.getthingsdone.databinding.ItemTaskBinding

class TasksAdapter(val tasks: List<Task>) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

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


    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.textViewTaskTitle.text = task.title
            binding.textViewTaskDetails.text = task.description
        }

    }

}