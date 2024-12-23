package com.diy.smartframesdk

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout


import android.app.Dialog
import android.graphics.Bitmap
import android.os.Message
import android.webkit.WebSettings



class IframeWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var webView: WebView

    init {
        orientation = VERTICAL
        webView = WebView(context)
        webView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(webView)

        // Configure WebView settings
        webView.settings.apply {
            javaScriptEnabled = true
            allowContentAccess = true
            domStorageEnabled = true
//            setAppCacheEnabled(true)
//            setAppCachePath(context.cacheDir.absolutePath)
            setCacheMode(WebSettings.LOAD_DEFAULT)
            layoutAlgorithm = android.webkit.WebSettings.LayoutAlgorithm.NORMAL
            useWideViewPort = true
            loadWithOverviewMode = true
            mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            javaScriptCanOpenWindowsAutomatically = true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
        }

        // Set WebViewClient and WebChromeClient
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                val popupWebView = WebView(context)
                popupWebView.settings.javaScriptEnabled = true
                popupWebView.webChromeClient = this
                popupWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        view?.loadUrl(url ?: "")
                        return true
                    }
                }

                // Open the WebView inside a Dialog or a new layout if needed
                val dialog = Dialog(context)
                dialog.setContentView(popupWebView)
                dialog.show()

                // Pass the new WebView back to the WebView's JavaScript
                val transport = resultMsg?.obj as WebView.WebViewTransport
                transport.webView = popupWebView
                resultMsg.sendToTarget()

                return true
            }
        }
    }

    fun loadUrlInIframe(url: String) {
        webView.loadUrl(url)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        webView.destroy()
    }
}

object SmartFrameLoader {
    fun loadIframe(context: Context, container: LinearLayout, url: String) {
        val iframeWebView = IframeWebView(context)
        container.addView(iframeWebView)
        iframeWebView.loadUrlInIframe(url)
    }
}
