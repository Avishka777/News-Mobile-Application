package com.example.sliit_news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sliit_news.database.News
import com.example.sliit_news.database.NewsDatabase
import com.example.sliit_news.database.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter:NewsAdapter
    private lateinit var viewModel:MainActivityData

    private val homeFragment = HomeFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button:Button = findViewById(R.id.button)
        val button2: Button = findViewById(R.id.button2)

        button.setOnClickListener {
            loadHome()
        }
        button2.setOnClickListener {
            loadSettings()
        }


        val recyclerView: RecyclerView = findViewById(R.id.rvNewsList)
        val repository = NewsRepository(NewsDatabase.getInstance(this))

        viewModel = ViewModelProvider(this)[MainActivityData::class.java]

        viewModel.data.observe(this){
            adapter = NewsAdapter(it,repository,viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllNewsItems()

            runOnUiThread{
                viewModel.setData(data)
            }
        }

        val addItem: Button = findViewById(R.id.btnAddItem)

        addItem.setOnClickListener{
            displayAlert(repository)
        }


    }

    private fun loadHome(){
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null){

            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,homeFragment).commit()
        }else{

            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,homeFragment).commit()
        }
    }

    private fun loadSettings() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, settingsFragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, settingsFragment)
                .commit()
        }
    }


    private fun displayAlert(repository: NewsRepository){
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getText(R.string.alertTitle))
        builder.setMessage("Enter News Below:")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, which ->
        // Get the input text and display a Toast message
            val item = input.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(News(item))
                val data = repository.getAllNewsItems()
                runOnUiThread{
                    viewModel.setData(data)
                }
            }
        }

        // Set the negative button action
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        // Create and show the alert dialog
        val alertDialog = builder.create()
        alertDialog.show()

    }

}