package com.example.distancecalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.distancecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mySpinner: Spinner = findViewById(R.id.mySpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.converter_lists,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mySpinner.adapter = adapter
        }

        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selected = parent?.getItemAtPosition(position).toString()
                if(selected == "KM to Miles") {
                    findViewById<TextView>(R.id.tv1).text = "Km"
                    findViewById<TextView>(R.id.tv2).text = "Miles"
                } else {
                    findViewById<TextView>(R.id.tv1).text = "Miles"
                    findViewById<TextView>(R.id.tv2).text = "Km"
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val input = findViewById<EditText>(R.id.raw_distance)
        val convertButton = findViewById<Button>(R.id.submit_btn)
        convertButton.setOnClickListener() {
            val inputDouble = input.text.toString().toDoubleOrNull()
            if(inputDouble != null) {
                val selected = mySpinner.selectedItem.toString()
                if(selected == "KM to Miles") {
                    val res = inputDouble * 0.621
                    findViewById<TextView>(R.id.value_result).text = "${res.toString()} Miles"
                    findViewById<TextView>(R.id.tv1).text = "Km"
                    findViewById<TextView>(R.id.tv2).text = "Miles"
                } else {
                    val res = inputDouble * 1.609
                    findViewById<TextView>(R.id.value_result).text = "${res.toString()} Kilometres"
                    findViewById<TextView>(R.id.tv1).text = "Miles"
                    findViewById<TextView>(R.id.tv2).text = "Km"
                }
            } else {
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show()
            }

        }
        input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed for this method
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if s is not null and not empty
                convertButton.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                // No implementation needed for this method
            }
        })

//        setSupportActionBar(binding.toolbar)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}