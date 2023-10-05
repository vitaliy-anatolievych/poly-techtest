package com.testtask.polytech.presentation.screens.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.testtask.polytech.R
import com.testtask.polytech.databinding.FragmentWebBinding
import com.testtask.polytech.presentation.contracts.OnBackPressedDelegation
import com.testtask.polytech.presentation.utils.OnBackPressedDelegationImpl

class WebScreen : Fragment(R.layout.fragment_web),
    OnBackPressedDelegation by OnBackPressedDelegationImpl() {

    private val binding: FragmentWebBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
        setUpProgressBar()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() = with(binding) {
        webMain.webViewClient = WebViewClient()
        with(webMain.settings) {
            blockNetworkImage = false
            blockNetworkLoads = false
            builtInZoomControls = true
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        val url = arguments?.getString(LINK) as String
        webMain.loadUrl(url)

        registerOnBackPressedDelegation(activity, this@WebScreen.lifecycle) {
            if (webMain.canGoBack()) {
                webMain.goBack()
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun setUpProgressBar() = with(binding) {
        pbLoadPage.max = 100

        webMain.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    pbLoadPage.visibility = View.GONE
                } else {
                    pbLoadPage.visibility = View.VISIBLE
                    pbLoadPage.progress = newProgress
                }
            }
        }
    }

    companion object {
        private const val LINK = "link"

        @JvmStatic
        fun newInstance(url: String) = WebScreen().apply {
            arguments = Bundle().apply {
                putString(LINK, url)
            }
        }
    }
}