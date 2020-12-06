package com.merttoptas.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import com.merttoptas.myapplication.databinding.ActivityMainBinding
import com.merttoptas.myapplication.manager.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var userManager: UserManager
    var name = ""
    var age = 0
    var isRemember = ""

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Context connection
        userManager = UserManager(this)
        observeData()
        initViews()
    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {
            name = binding.etName.text.toString()
            age = binding.etAge.text.toString().toInt()
            val isRemember = binding.switchRemember.isChecked

            GlobalScope.launch {
                userManager.storeUser(age, name, isRemember)
            }
        }
    }

    private fun observeData() {
        userManager.userNameFlow.asLiveData().observe(this, {
            name = it
            binding.tvName.text = it.toString()
        })

        userManager.userAgeFLow.asLiveData().observe(this, {
            age = it
            binding.tvAge.text = it.toString()
        })
        userManager.userRememberFlow.asLiveData().observe(this, {
            isRemember = if (it) "Remember" else "Not Remember"
            binding.tvRemember.text = isRemember
        })
    }
}