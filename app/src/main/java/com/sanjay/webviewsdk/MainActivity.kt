package com.sanjay.webviewsdk





import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.diy.smartframesdk.SmartFrameSdkLauncher
import com.sanjay.webviewsdk.databinding.ActivityMainBinding



class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null

    companion object{
        const val TAG = "MainActivity"
        const val URL = "param_url"
        @JvmStatic
        fun start(context: Context,url:String?) {
            val starter = Intent(context, MainActivity::class.java)
            starter.apply {
                putExtra(URL,url)
            }
            context.startActivity(starter)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            lifecycleOwner = this@MainActivity
            executePendingBindings()
        }
//        binding?.viewModel = mViewModel
        init()
    }

    override fun initArguments() {
    }

    override fun initViews() {
//        binding?.main?.let {
//            SmartFrameLoader.loadIframe(this,
//                it, "https://register.saasboomi.org/sbannual25/embed/gjdgxt-saasboomi-annual-25?embed=true")
//        }
        window.statusBarColor = Color.BLACK

        binding?.btnOpen?.setOnClickListener {
            SmartFrameSdkLauncher.launch(
                this,
                url = "https://register.saasboomi.org/sbannual25/embed/gjdgxt-saasboomi-annual-25?embed=true"
            )
        }



    }

    override fun setupListener() {
    }

    override fun loadData() {

    }


}