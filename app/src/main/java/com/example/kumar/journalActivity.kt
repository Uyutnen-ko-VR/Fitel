package com.example.kumar

import Exercise
import ExerciseAdapter
import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class journalActivity : AppCompatActivity() {
    private lateinit var exerciseSpinner: Spinner
    private lateinit var repsEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var addButton: Button
    private lateinit var resultsTextView: TextView

    private var prevExercise: List<Exercise> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)

        exerciseSpinner = findViewById(R.id.exercise_spinner)
        repsEditText = findViewById(R.id.reps_editText)
        weightEditText = findViewById(R.id.weight_editText)
        addButton = findViewById(R.id.add_button)
        resultsTextView = findViewById(R.id.textView1)

        addButton.setOnClickListener { addResult() }
    }

    private fun addResult() {
        val exerciseText = exerciseSpinner.selectedItem.toString()
        val reps = repsEditText.text.toString().toIntOrNull() ?: 0
        val weight = weightEditText.text.toString().toIntOrNull() ?: 0

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val key = "$exerciseText $reps $weight"
        println(key)
        editor.putBoolean(key, true)
        editor.apply()

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        val exercise = Exercise(exerciseText, reps, weight)

        prevExercise = prevExercise + exercise

        // Создайте адаптер
        val adapter = ExerciseAdapter(prevExercise)

        // Привяжите адаптер к RecyclerView
        recyclerView.adapter = adapter

        // Установите менеджер макета
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }
}
