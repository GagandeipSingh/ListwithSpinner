package com.example.listwithspinner

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doOnTextChanged
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.listwithspinner.databinding.ActivityMainBinding
import com.example.listwithspinner.databinding.AdddialogBinding
import com.example.listwithspinner.fragments.Items

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController
    lateinit var arrayList:ArrayList<Items>
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHost = supportFragmentManager.findFragmentById(R.id.host) as NavHostFragment
        navController = navHost.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)
        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.custombg))
        arrayList = ArrayList()
        arrayList.add(Items("Water","5"))
        arrayList.add(Items("Coke","19"))
        arrayList.add(Items("Soda","9"))
                binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.items ->
                    if(navController.currentDestination?.id != R.id.firstFragment)
                    navController.navigate(R.id.action_secondFragment_to_firstFragment)
                R.id.order ->
                    if(navController.currentDestination?.id != R.id.secondFragment)
                    navController.navigate(R.id.action_firstFragment_to_secondFragment)
            }
            return@setOnItemSelectedListener true
        }
        binding.fab.setOnClickListener {
            val addBinding = AdddialogBinding.inflate(layoutInflater)
            val addDialog = Dialog(this@MainActivity)
            addDialog.setContentView(addBinding.root)
            addDialog.show()
            addBinding.positiveButton.setOnClickListener {
                if(addBinding.etName.text.trim().isEmpty()){
                    addBinding.textInputLayout1.error = "Enter Item Name.."
                }
                else if(addBinding.etQuantity.text.trim().isEmpty()){
                    addBinding.textInputLayout2.error = "Enter Quantity.."
                }
                else{
                    arrayList.add(Items(addBinding.etName.text.trim().toString(),
                        addBinding.etQuantity.text.trim().toString()))
                    Toast.makeText(this@MainActivity,"New Item Added..", Toast.LENGTH_SHORT).show()
                    addDialog.dismiss()
                }
                addBinding.etName.doOnTextChanged { _, _, _, _ ->
                    addBinding.textInputLayout1.isErrorEnabled = false
                }
                addBinding.etQuantity.doOnTextChanged { _, _, _, _ ->
                    addBinding.textInputLayout2.isErrorEnabled = false
                }
            }
        }

            navController.addOnDestinationChangedListener{ _,destination ,_ ->
            when(destination.id){
                R.id.firstFragment ->{
                    binding.bottomNav.menu.findItem(R.id.items).isChecked = true
                    binding.fab.visibility = View.VISIBLE
                }
                R.id.secondFragment -> {
                    binding.bottomNav.menu.findItem(R.id.order).isChecked = true
                    binding.fab.visibility = View.GONE
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()  ||  super.onSupportNavigateUp()
    }
}