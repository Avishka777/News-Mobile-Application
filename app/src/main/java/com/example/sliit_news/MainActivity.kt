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

    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: MainActivityData

    private val homeFragment = HomeFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button)
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

        // Observe LiveData from the ViewModel
        viewModel.data.observe(this) {
            adapter = NewsAdapter(it, repository, viewModel)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        // Load data from the repository and update the ViewModel
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllNewsItems()

            runOnUiThread {
                viewModel.setData(data)
            }
        }

        val addItem: Button = findViewById(R.id.btnAddItem)

        addItem.setOnClickListener {
            displayAlert(repository)
        }
    }

    private fun loadHome() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            // If the fragment is not already added, add it
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, homeFragment).commit()
        } else {
            // If the fragment is already added, replace it
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit()
        }
    }

    private fun loadSettings() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment == null) {
            // If the fragment is not already added, add it
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, settingsFragment).commit()
        } else {
            // If the fragment is already added, replace it
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, settingsFragment).commit()
        }
    }

    private fun displayAlert(repository: NewsRepository) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getText(R.string.alertTitle))
        builder.setMessage("Enter News Below:")

        val formLayout = layoutInflater.inflate(R.layout.form_layout, null)
        val etItem = formLayout.findViewById<EditText>(R.id.etItem)
        val etDescription = formLayout.findViewById<EditText>(R.id.etDescription)
        val etDate = formLayout.findViewById<EditText>(R.id.etDate)

        // Set the form layout as the view for the AlertDialog
        builder.setView(formLayout)

        builder.setPositiveButton("OK") { dialog, which ->
            // Retrieve input values
            val item = etItem.text.toString()
            val description = etDescription.text.toString()
            val date = etDate.text.toString()

            // Insert the new item into the repository
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(News(item, description, date))
                val data = repository.getAllNewsItems()
                runOnUiThread {
                    viewModel.setData(data)
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

}
