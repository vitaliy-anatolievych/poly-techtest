package com.testtask.polytech.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mylibrary.core.network.NetworkHandler
import com.testtask.polytech.R
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.presentation.contracts.NavigatorMain
import com.testtask.polytech.presentation.screens.details.CategoriesDetailScreen
import com.testtask.polytech.presentation.screens.start.StartScreen
import com.testtask.polytech.presentation.screens.web.WebScreen
import com.testtask.polytech.presentation.utils.FragmentManager
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavigatorMain {

    private val networkHandler: NetworkHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            goToStartPage()
        }
    }

    override fun goToStartPage() {
        FragmentManager
            .launchFragment(this, StartScreen.newInstance())
    }

    override fun goToCategoryDetails(category: CategoryModel) {
        FragmentManager
            .launchFragment(this, CategoriesDetailScreen.newInstance(category), true)
    }

    override fun goToBookDetails(url: String) {
        if (networkHandler.isConnected) {
            FragmentManager
                .launchFragment(this, WebScreen.newInstance(url), true)
        } else {
            Toast.makeText(this, getString(R.string.ure_not_online), Toast.LENGTH_SHORT).show()
        }
    }
}