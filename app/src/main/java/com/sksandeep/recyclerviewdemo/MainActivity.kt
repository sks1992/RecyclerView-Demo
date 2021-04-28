package com.sksandeep.recyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //create Forcastrepository referanse
    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodEditText:EditText = findViewById(R.id.etZipCode)
        val enterButton:Button = findViewById(R.id.btnZipCode)

        enterButton.setOnClickListener {
            val zipcode:String = zipcodEditText.text.toString()

            if(zipcode.length !=5){
                val msg1 = getString(R.string.zipcode_entry_error)
                Toast.makeText(this,msg1,Toast.LENGTH_SHORT).show()
            } else {
                forecastRepository.loadForecast(zipcode)
            }
         }

        //create RecyclerView referance
        val forecastList:RecyclerView = findViewById(R.id.rvForcastlist)

        //create layoutmanager
        forecastList.layoutManager = LinearLayoutManager(this)

        val dailyForecastAdapter = DailyForecastAdapter(){ forecastItem ->
            val msg = getString(R.string.forecast_clicked_foremat,forecastItem.temp,forecastItem.description)
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()

        }
        forecastList.adapter = dailyForecastAdapter

        //create observer
        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            //update our list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        //Lets add observer that show us update
        forecastRepository.weeklyForecast.observe(this,weeklyForecastObserver)
    }
}