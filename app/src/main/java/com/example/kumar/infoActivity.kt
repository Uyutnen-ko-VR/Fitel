package com.company.fitel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class infoActivity : AppCompatActivity() {

    private lateinit var nicknameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var daysEditText: EditText
    private lateinit var genderText: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        // Инициализация элементов пользовательского интерфейса
        nicknameEditText = findViewById(R.id.nicknameEditText)
        ageEditText = findViewById(R.id.ageEditText)
//        genderEditText = findViewById(R.id.genderEditText)
        weightEditText = findViewById(R.id.weightEditText)
        heightEditText = findViewById(R.id.heightEditText)
        locationEditText = findViewById(R.id.locationEditText)
        daysEditText = findViewById(R.id.daysEditText)

        // Инициализация кнопки "Сохранить"
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            saveUserData()
        }

        val backButton: Button = findViewById(R.id.nextButton)
        backButton.setOnClickListener {
            GoMain()
        }

        val genderSpinner: Spinner = findViewById(R.id.spinner_gender)
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val gender = parent.getItemAtPosition(position).toString()
                genderText = gender;

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

    }

    private fun saveUserData() {
        // Получение данных из EditText
        val nickname = nicknameEditText.text.toString()
        val age = ageEditText.text.toString().toIntOrNull()
        val gender = genderText
        val weight = weightEditText.text.toString().toDoubleOrNull()
        val height = heightEditText.text.toString().toDoubleOrNull()
        val location = locationEditText.text.toString()
        val days = daysEditText.text.toString()

        // Сохранение данных в базу данных или в другой источник данных


        // Для тестирования выводим полученные данные в консоль
        println("Ник: $nickname")
        println("Возраст: $age")
        println("Пол: $gender")
        println("Вес: $weight")
        println("Рост: $height")
        println("Место тренировок: $location")
        println("Дни тренировок: $days")
    }

    private fun GoMain()
    {
        val intent = Intent(this, mainActivity::class.java)
        startActivity(intent)
    }
}
