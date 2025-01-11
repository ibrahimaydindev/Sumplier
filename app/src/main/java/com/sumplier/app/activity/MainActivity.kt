package com.sumplier.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sumplier.app.R
import com.sumplier.app.api.TicketApiManager
import com.sumplier.app.api.UserApiManager
import com.sumplier.app.app.Config
import com.sumplier.app.model.Ticket
import com.sumplier.app.model.User

class MainActivity : AppCompatActivity() {


    private var currentUser: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentUser = Config.getInstance().getCurrentUser()

        Log.d("MainActivity", "currentUser Name: ${currentUser?.name}")

    }
}