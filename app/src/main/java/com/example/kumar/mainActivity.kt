package com.example.kumar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class mainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val saveButton: Button = findViewById(R.id.backButton)
        saveButton.setOnClickListener {
            GoInfo()
        }

        val journalButton: Button = findViewById(R.id.journalButton)
        journalButton.setOnClickListener {
            GoJournal()
        }

        val mapButton: Button = findViewById(R.id.mapButton)
        journalButton.setOnClickListener {
            GoMap()
        }
    }

    private fun GoInfo()
    {
        val intent = Intent(this, infoActivity::class.java)
        startActivity(intent)
    }

    private fun GoJournal()
    {
        val intent = Intent(this, journalActivity::class.java)
        startActivity(intent)
    }

    private fun GoMap()
    {
        val intent = Intent(this, mapsActivity::class.java)
        startActivity(intent)
    }
}