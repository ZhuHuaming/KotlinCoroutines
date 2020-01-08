package com.mhsb.kotlincoroutines

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            val drawable = getImg()
            img.setImageDrawable(drawable)
        }

    }

    private suspend fun getImg(): Drawable = withContext(Dispatchers.IO) {
        System.out.println(Thread.currentThread().name)
        val httpUrlConnection: HttpURLConnection =
            URL("http://h.atstudy.com/atstudy/cpython2/p/img/logo.png").openConnection() as HttpURLConnection
        var bitmapDrawable: Drawable? = null
        //知识点补充 httpUrlConnection的connect方法不一定需要显示去调用   在getInputStream时候会判断是否已经请求过，没请求就会自动调用connect
        /*httpUrlConnection.connect()*/
        if (httpUrlConnection.responseCode == 200) {
            bitmapDrawable =
                BitmapDrawable.createFromStream(httpUrlConnection.inputStream, "sdf.png")
        }
        bitmapDrawable!!
    }
}
