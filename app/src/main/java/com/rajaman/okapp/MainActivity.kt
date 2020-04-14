package com.rajaman.okapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var firstName = ""
    var lastName = ""
    var gender :String = ""
    var year = 0
    var month = 0
    var day = 0
    var source = ""
    var des = ""
    var fare = 0
    lateinit var sourceArr :Array<String>
    lateinit var desArr :Array<String>
    var mfb :FirebaseAnalytics ?= null
    lateinit var db :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = FirebaseDatabase.getInstance().reference
        mfb = FirebaseAnalytics.getInstance(this)

        sourceArr = resources.getStringArray(R.array.sources)
        desArr=  resources.getStringArray(R.array.dess)

        if (spinnerSource != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sourceArr)
            spinnerSource.adapter = adapter
        }
        spinnerSource.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "Please Select One!", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                source = sourceArr[position]
            }
        }

        if (spinnerDes != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, desArr)
            spinnerDes.adapter = adapter
        }
        spinnerDes.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "Please Select One!", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                des = desArr[position]
            }
        }

        buttonSubmit.setOnClickListener {
            fillData()
            addTask()
        }

    }

    fun fillData(){
        firstName = editTextFN.text.toString()
        lastName = editTextLN.text.toString()
        if (radioButtonMale.isActivated)
            gender = "Male"
        if (radioButtonFemale.isActivated)
            gender = "Female"
        year = editTextYear.text.toString().toInt()
        month = editTextMonth.text.toString().toInt()
        day = editTextDay.text.toString().toInt()
        fare = 100
        textViewFare.text = fare.toString()
    }

    fun addTask(){
        val task = Task.create()

        task.firstName = firstName
        task.lastName = lastName
        task.gender = gender
        task.year = year
        task.month = month
        task.day = day
        task.source = source
        task.destination = des
        task.fare = fare

        val newTask = db.child(Statics.FIREBASE_TASK).push()
        task.objectId = newTask.key

        newTask.setValue(task)
        Toast.makeText(this, "data added!", Toast.LENGTH_SHORT).show()

    }
}
