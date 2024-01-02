package com.example.assignment_two_using_kotlin_and_xml



import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextPhone: EditText
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPhone = findViewById(R.id.editTextPhone)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            val phoneNo = editTextPhone.text.toString().trim()
            checkDataFromDatabase(phoneNo)
        }
    }

    private fun checkDataFromDatabase(phoneNo: String) {
        val intentForData = Intent(this, FetchedActivity::class.java)
        intentForData.putExtra("mobileNo", phoneNo)
        startActivity(intentForData)
    }
}