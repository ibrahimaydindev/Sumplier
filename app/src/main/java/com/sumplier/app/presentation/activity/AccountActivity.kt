package com.sumplier.app.presentation.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.R
import com.sumplier.app.app.Config
import com.sumplier.app.data.model.CompanyAccount
import com.sumplier.app.presentation.adapter.AccountAdapter
import android.widget.EditText
import android.widget.ImageView

class AccountActivity : AppCompatActivity() {

    private var accounts: List<CompanyAccount>? = Config.getInstance().getCompanyAccounts()
    private lateinit var adapter: AccountAdapter
    private lateinit var searchEditText: EditText
    private lateinit var accountsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)

        setupViews()
        setupRecyclerView()
        setupSearch()
    }

    private fun setupViews() {
        searchEditText = findViewById(R.id.searchEditText)
        accountsRecyclerView = findViewById(R.id.accountsRecyclerView)
        
        findViewById<ImageView>(R.id.backButton).setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = AccountAdapter(
            accounts ?: emptyList(),
            onEditClick = { account -> handleEditClick(account) },
            onCashClick = { account -> handleCashClick(account) },
            onItemClick = { account -> handleItemClick(account) }
        )

        accountsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AccountActivity)
            adapter = this@AccountActivity.adapter
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
        })
    }

    private fun handleEditClick(account: CompanyAccount) {
        // TODO: Edit işlemlerini burada gerçekleştir
    }

    private fun handleCashClick(account: CompanyAccount) {
        // TODO: Cash işlemlerini burada gerçekleştir
    }

    private fun handleItemClick(account: CompanyAccount) {
        // TODO: Item tıklama işlemlerini burada gerçekleştir
    }
}