package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var addTaskViewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = getString(R.string.add_course)

        val factory = AddViewModelFactory.createFactory(this)
        addTaskViewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
        addTaskViewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                Toast.makeText(this, "Add Course Schedule Success", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Course Name or Time can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                val courseName = findViewById<EditText>(R.id.add_ed_course).text.toString()
                val dayPosition = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
                val startTime = findViewById<TextView>(R.id.tv_start_time).text.toString()
                val endTime = findViewById<TextView>(R.id.tv_end_time).text.toString()
                val lecturer = findViewById<EditText>(R.id.add_ed_lecturer).text.toString()
                val note = findViewById<EditText>(R.id.add_ed_note).text.toString()

                addTaskViewModel.insertCourse(courseName, dayPosition, startTime, endTime, lecturer, note)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showStartTimePicker(view: View) {
        val timeFragment = TimePickerFragment()
        timeFragment.show(supportFragmentManager, "startTimePicker")
    }

    fun showEndTimePicker(view: View) {
        val timeFragment = TimePickerFragment()
        timeFragment.show(supportFragmentManager, "endTimePicker")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val startTimeTextView = findViewById<TextView>(R.id.tv_start_time)
        val endTimeTextView = findViewById<TextView>(R.id.tv_end_time)

        if (tag == "endTimePicker") {
            endTimeTextView.text = timeFormat.format(calendar.time)
        } else {
            startTimeTextView.text = timeFormat.format(calendar.time)
        }
    }
}