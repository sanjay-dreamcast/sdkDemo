package com.diy.smartframesdk

import android.app.Dialog
import android.os.Bundle

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Message
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sanjay.smartframesdk.databinding.ActivitySmartFrameBinding


class SmartFrameSdkActivity : BaseActivity() {
    private var binding: ActivitySmartFrameBinding? = null


    companion object{
        const val TAG = "SmartFrameSdkActivity"
        const val URL = "param_url"
        @JvmStatic
        fun start(context: Context,url:String?) {
            val starter = Intent(context, SmartFrameSdkActivity::class.java)
            starter.apply {
                putExtra(URL,url)
//                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmartFrameBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            lifecycleOwner = this@SmartFrameSdkActivity
            executePendingBindings()
        }
//        binding?.viewModel = mViewModel
        init()
    }

    override fun initArguments() {
        window.statusBarColor = Color.BLACK
    }

    override fun initViews() {
        binding?.webview?.fitsSystemWindows = true

    }

    override fun setupListener() {
//        binding?.headerToolbar?.universalToolbar?.setNavigationOnClickListener {
//            onBackPressed()
//        }
    }

    override fun loadData() {
        binding?.webview?.settings?.apply {
            javaScriptEnabled = true
            allowContentAccess = true
            domStorageEnabled = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
            useWideViewPort = true
            loadWithOverviewMode = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            javaScriptCanOpenWindowsAutomatically = true
        }

        binding?.webview?.settings?.allowFileAccess = true
        binding?.webview?.settings?.allowContentAccess = true
        binding?.webview?.settings?.setSupportMultipleWindows(true)

        binding?.webview?.clearCache(true)
        binding?.webview?.clearHistory()

        binding?.webview?.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                val popupWebView = WebView(this@SmartFrameSdkActivity)
                popupWebView.settings.javaScriptEnabled = true
                popupWebView.settings.setSupportMultipleWindows(true)
                popupWebView.webChromeClient = this  // Set WebChromeClient for popup as well
                popupWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

//                        if (url!!.contains("whatsapp")) {
//                            if (handleWhatsAppUrl(url, this@MainActivity)) {
//                                return true // URL was handled
//                            }
//                        }
                        view?.loadUrl(url ?: "")


                        return true
                    }
                }

                // Show the popup WebView in a dialog or another layout
                val dialog = Dialog(this@SmartFrameSdkActivity)
                dialog.setContentView(popupWebView)
                dialog.show()

                // Pass the new WebView back to the WebView's JavaScript
                val transport = resultMsg?.obj as WebView.WebViewTransport
                transport.webView = popupWebView
                resultMsg.sendToTarget()

                return true
            }
        }
        binding?.webview?.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                Log.d("TAG", "shouldOverrideUrlLoading: $url")
                view?.loadUrl(url)
                return true
            }

            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: android.graphics.Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                Log.d("@@url", url.toString())

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d("@@url", url.toString())
            }
        }


        val iframeHTML = """
<html>
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
iframe {
  max-width: 100%;
  width: 100%;
  min-height: 90vh;
}
</style>

</head>
<body>
<iframe id="childIframe" src="https://register.saasboomi.org/sbannual25/embed/gjdgxt-saasboomi-annual-25?embed=true" width='100%' frameBorder='0' style='border:1px solid #000;border-radius:10px;height:100vh;'></iframe>
                       <script>
                          const handleBeforeUnload = () => {
                            const iframe = document.getElementById("childIframe");
                            if (iframe && iframe.contentWindow) {
                              iframe.contentWindow.postMessage(
                                "parentReloaded",
                                "https://register.saasboomi.org"
                              );
                            }
                          };
                          window.addEventListener("beforeunload", handleBeforeUnload);
                          const cleanup = () => {
                            window.removeEventListener("beforeunload", handleBeforeUnload);
                          };
                          window.addEventListener("unload", cleanup);
                        </script>

                        </body>
</html>
""".trimIndent()
//        binding?.webview?.loadDataWithBaseURL(null, iframeHTML, "text/html", "UTF-8", null) }
        // Load the URL (instead of iframe HTML content)
        val urlToLoad =
            "https://register.saasboomi.org/sbannual25/embed/gjdgxt-saasboomi-annual-25?embed=true"
        binding?.webview?.loadUrl(urlToLoad)


    }

}

// Utility or Companion Object for launching SmartFrameSdkActivity
object SmartFrameSdkLauncher {

    // Simple launch function
    fun launch(context: Context, url: String?) {
        SmartFrameSdkActivity.start(context, url)
    }
}
