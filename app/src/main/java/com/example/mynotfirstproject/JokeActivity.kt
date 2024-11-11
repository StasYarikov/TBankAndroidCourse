package com.example.mynotfirstproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mynotfirstproject.databinding.ActivityJokeBinding
import com.example.mynotfirstproject.view_model.jokes_list.JokeListFragment

class JokeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, JokeListFragment())
                .commit()
        }
    }
}
