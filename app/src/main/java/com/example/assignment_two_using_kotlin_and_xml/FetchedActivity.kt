package com.example.assignment_two_using_kotlin_and_xml





import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.HashMap
import java.util.Map

class FetchedActivity : AppCompatActivity() {

    private lateinit var typedMobileNo: TextView
    private lateinit var storedMobileNo: TextView
    private lateinit var storedPassword: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetched)

        typedMobileNo = findViewById(R.id.typedMobileNo)

        val intentForData: Intent = intent
        typedMobileNo.text = intentForData.getStringExtra("mobileNo")

        val phoneNumber = intentForData.getStringExtra("mobileNo")

        button = findViewById(R.id.button)
        button.setOnClickListener {
            fetchDataFromServer(phoneNumber)
        }

        storedPassword = findViewById(R.id.fetchedPasswordEditText)
        storedMobileNo = findViewById(R.id.fetchedPhoneEditText)
    }

    private fun fetchDataFromServer(whichNumber: String?) {
        // Instantiate the RequestQueue.
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.29.135/membershipApp/getDetails.php" // Replace with your actual API endpoint URL

        // Create a StringRequest
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    // Convert the response string to a JSON object
                    val jsonResponse = JSONObject(response)

                    // Check the 'status' key in the JSON response
                    val serverPassword = jsonResponse.getString("password")
                    val serverMobileNo = jsonResponse.getString("mobileNo")

                    storedPassword.text = serverPassword
                    storedMobileNo.text = serverMobileNo

                    Toast.makeText(applicationContext, "data fetching successful", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Error ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                // Handle errors
                Toast.makeText(applicationContext, "Error: " + error.message, Toast.LENGTH_SHORT).show()
            })
        {
            override fun getParams(): MutableMap<String, String>? {
                // Set the parameters to be sent to the server
                val params: MutableMap<String, String> = HashMap()
                params["mobileNo"] = whichNumber.toString()
                return params
            }
        }

        // Add the request to the RequestQueue.
       queue.add(stringRequest)
    }
}
