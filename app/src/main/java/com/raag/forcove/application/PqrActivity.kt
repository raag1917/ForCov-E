package com.raag.forcove.application

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.raag.forcove.databinding.ActivityPqrBinding

class PqrActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPqrBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPqrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = "https://forms.office.com/Pages/ResponsePage.aspx?id=F1JIkX4qJkGqaoBz5PEcdJ6-xju4DZtAqyGktrsY6P1UQjhBQ0tYTjZKWVBCSVdWMlVTSzNERkw5Mi4u"
        binding.webView.webChromeClient = object : WebChromeClient() {

                    }
        binding.webView.webViewClient = object : WebViewClient() {
                    }
        val settings = binding.webView.settings
        settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)

    }
}