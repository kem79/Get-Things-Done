package com.marcal.getthingsdone

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.marcal.getthingsdone.data.GetThingsDoneDatabase
import com.marcal.getthingsdone.data.Task
import com.marcal.getthingsdone.databinding.ActivityMainBinding
import com.marcal.getthingsdone.databinding.DialogAddTaskBinding
import com.marcal.getthingsdone.ui.tasks.TasksFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: GetThingsDoneDatabase
    private val taskDao by lazy { database.getTaskDao() }
    private val taskFragment: TasksFragment = TasksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Activity ready")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = "Tasks"
        }.attach()

        binding.fab.setOnClickListener { displayAddTaskDialog() }

        database = GetThingsDoneDatabase.getDatabase(this)

    }

    private fun displayAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.buttonShowDetails.setOnClickListener {
            dialogBinding.editTextTaskDetails.visibility =
                if (dialogBinding.editTextTaskDetails.isVisible) View.GONE else View.VISIBLE
        }

        dialogBinding.buttonSave.setOnClickListener {
            val task = Task(
                title = dialogBinding.editTextTaskTitle.text.toString(),
                description = dialogBinding.editTextTaskDetails.text.toString()
            )
            thread {
                taskDao.createTask(task)
            }
            dialog.dismiss()
            taskFragment.fetchAllTasks()
        }

        dialog.show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun createFragment(position: Int): Fragment {
            return taskFragment
        }

        override fun getItemCount() = 1

    }
}