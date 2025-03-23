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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.api.managers.TicketApiManager
import com.sumplier.app.data.api.managers.UserApiManager
import com.sumplier.app.data.database.PreferencesHelper
import com.sumplier.app.data.enums.ConfigKey
import com.sumplier.app.data.enums.ConfigState
import com.sumplier.app.data.enums.DateFormat
import com.sumplier.app.data.model.Ticket
import com.sumplier.app.presentation.adapter.TicketAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class HistoryActivity : AppCompatActivity() {
    private lateinit var ticketAdapter: TicketAdapter
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        
        setupBackButton()
        setupViews()
        setupRecyclerView()
        setupSearch()
        setupSwipeRefresh()

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
        swipeRefreshLayout = findViewById(R.id.swipeRefresh)
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

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
    }

    private fun loadData() {
        val ticketApiManager = TicketApiManager()
        val currentCompany = Config.getInstance().getCurrentCompany()

        val dateFormat =
            SimpleDateFormat(DateFormat.CLOUD_REQUEST_FORMAT.pattern, Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("Europe/Istanbul")
            }

        // Start: Yesterday 00.00
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 1)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startDateTime = dateFormat.format(calendar.time)

        // End: Today current Time
        val endDateTime = dateFormat.format(Calendar.getInstance().time)

        ticketApiManager.getTicketByUserCode(
            companyCode = currentCompany.companyCode,
            resellerCode = currentCompany.resellerCode,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            userCode = Config.getInstance().getCurrentUser().userCode
        ) { tickets ->
            // SwipeRefresh'i durdur
            swipeRefreshLayout.isRefreshing = false
            
            tickets?.takeUnless { it.isEmpty() }?.let { validTickets ->
                ticketAdapter.setTickets(validTickets)
            }
        }
    }
}