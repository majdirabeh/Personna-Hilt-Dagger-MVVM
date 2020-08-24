package fr.dev.majdi.personnahiltmvvm.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fr.dev.majdi.personna.model.Result
import fr.dev.majdi.personnahiltmvvm.App
import fr.dev.majdi.personnahiltmvvm.R
import fr.dev.majdi.personnahiltmvvm.ui.adapter.PersonnaAdapter
import fr.dev.majdi.personnahiltmvvm.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 *Created by Majdi RABEH on 24/08/2020
 *Email = "m.rabeh.majdi@gmail.com")
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), App.OnInternetConnectivity {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: PersonnaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        //setupObserver()
        visibilityLoader(true)
    }

    override fun onResume() {
        super.onResume()
        App.setOnInternetConnectivity(this)
    }

    private fun setupUI() {
        recycler.layoutManager = LinearLayoutManager(this)
        adapter = PersonnaAdapter(arrayListOf())
        recycler.addItemDecoration(
            DividerItemDecoration(
                this,
                (recycler.layoutManager as LinearLayoutManager).orientation
            )
        )
        recycler.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getUsers()
        mainViewModel.listOfUsers.observe(this, Observer {
            Log.e("MainActivity", it.size.toString())
            if (it.isNotEmpty()) {
                renderList(it)
                visibilityRecycle(true)
                visibilityLoader(false)
            } else {
                visibilityRecycle(false)
                visibilityLoader(false)
            }
        })
    }

    private fun visibilityRecycle(setVisible: Boolean) {
        if (setVisible) {
            recycler.visibility = View.VISIBLE
            no_data.visibility = View.GONE
        } else {
            recycler.visibility = View.GONE
            no_data.visibility = View.VISIBLE
        }
    }

    private fun visibilityLoader(setVisible: Boolean) {
        if (setVisible) {
            progressBar.visibility = View.VISIBLE
            no_data.visibility = View.GONE
            recycler.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun renderList(users: List<Result>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
        adapter.setItemClickListener(object :
            PersonnaAdapter.ItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "Clicked User gender is ${users[position].gender}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        App.setOnInternetConnectivity(null)
    }

    override fun observeInternetConnectivity(isConnected: Boolean) {
        setupObserver()
    }

}