package com.sumplier.app.presentation.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.api.managers.TicketApiManager
import com.sumplier.app.data.api.managers.UserApiManager
import com.sumplier.app.data.database.PreferencesHelper
import com.sumplier.app.data.enums.ConfigKey
import com.sumplier.app.data.enums.ConfigState
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.presentation.adapter.TicketAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        
        setupBackButton()
        setupViews()
        setupRecyclerView()
        setupSearch()
        
        // Test için örnek veriler
        loadData()
    }

    private fun setupBackButton() {
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViews() {
        searchEditText = findViewById(R.id.etSearch)
        recyclerView = findViewById(R.id.rvTickets)
    }

    private fun setupRecyclerView() {
        ticketAdapter = TicketAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = ticketAdapter
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                ticketAdapter.filterTickets(s.toString())
            }
        })
    }

    private fun loadData() {
        val ticketApiManager = TicketApiManager()
        val currentCompany = Config.getInstance().getCurrentCompany()

        currentCompany.let { company ->
            ticketApiManager.getTicketAll(
                companyCode = company.companyCode,
                resellerCode = company.resellerCode
            ) { tickets ->
                tickets?.takeUnless { it.isEmpty() }?.let { validTickets ->
                    ticketAdapter.setTickets(validTickets)
                }
            }
        }
    }
}