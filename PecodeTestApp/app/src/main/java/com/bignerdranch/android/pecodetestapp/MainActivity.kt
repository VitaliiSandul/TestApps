package com.bignerdranch.android.pecodetestapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(){
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: CustomAdapter

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelId ="pecodetestapp"
    private val description ="Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagesFromFile = readFromFile()?.toInt()
        Log.d("pagesFromFile", pagesFromFile.toString())

        hideNavBar()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        adapter = CustomAdapter(supportFragmentManager)
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        viewPager.adapter = adapter

        val mIntent = intent
        val pagesFromPendingIntent = mIntent.getIntExtra("pages", 0)
        Log.d("pagesFromPendingIntent", pagesFromPendingIntent.toString())


        if(pagesFromPendingIntent > 0){
            for (i in 1..pagesFromPendingIntent) {
                adapter.addFrag(SimpleFragment(i))
                Log.d("fragment added", i.toString())
            }
        }
        else if(pagesFromFile != 0){
            for (i in 1..pagesFromFile!!) {
                adapter.addFrag(SimpleFragment(i))
                Log.d("fragment added", i.toString())
            }
        }
        else{
            adapter.addFrag(SimpleFragment(adapter.getCount() + 1))
        }
        adapter.notifyDataSetChanged()
        viewPager.currentItem = adapter.getCount() - 1

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        hideNavBar()
    }

    override fun onStop() {
        super.onStop()
        writeToFile(adapter.getCount().toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        removeAllNotification()
    }

    private fun hideNavBar() {
        val decorator = window.decorView
        val uiOption = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorator.systemUiVisibility = uiOption
    }

    fun sendNotification(){
        Log.d("viewPager.currentItem", (viewPager.currentItem + 1).toString())

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("pages", viewPager.currentItem + 1)
        val pendingIntent = PendingIntent.getActivity(this, viewPager.currentItem + 1, intent, 0)
        val contentView = RemoteViews(packageName, R.layout.notification_layout)
        contentView.setTextViewText(R.id.notif_title, "You create a notification")
        contentView.setTextViewText(R.id.notif_content, "Notification " + (viewPager.currentItem + 1))

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                    .setSmallIcon(R.drawable.notify_icon)
                    .setContent(contentView)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
        }
        else{
            builder = Notification.Builder(this)
                    .setSmallIcon(R.drawable.notify_icon)
                    .setContent(contentView)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
        }

        notificationManager.notify(viewPager.currentItem, builder.build())
    }

    fun removeNotification(notifId: Int){
        notificationManager.cancel(notifId)
    }

    fun removeAllNotification(){
        notificationManager.cancelAll()
    }

    fun addFragment(){
        adapter.addFrag(SimpleFragment(adapter.getCount() + 1))
        adapter.notifyDataSetChanged()
        viewPager.currentItem = adapter.getCount() - 1
    }

    fun removeFragment(){
        adapter.removeFrag()
        removeNotification(adapter.getCount())
        adapter.notifyDataSetChanged()
        viewPager.currentItem = adapter.getCount() - 1
    }

    fun checkFragCount(): Int{
        return adapter.getCount()
    }

    fun updateUI(){
        adapter.notifyDataSetChanged()
    }

    private fun writeToFile(data: String) {
        try {
            val fileOutputStream: FileOutputStream
            fileOutputStream = openFileOutput("pagenumber.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        } catch (e: FileNotFoundException) {
            Log.e("Exception", e.toString())
        }
        catch (e: Exception) {
            Log.e("Exception", e.toString())
        }
    }

    private fun readFromFile(): String? {
        var result = "0"

        try{

            var fileInputStream: FileInputStream? = null
            fileInputStream = openFileInput("pagenumber.txt")

            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while({ text = bufferedReader.readLine(); text} () != null){
                stringBuilder.append(text)
            }

            result = stringBuilder.toString()
        }
        catch (e: FileNotFoundException) {
            Log.d("Exception", e.toString())
        }
        catch (e: Exception) {
            Log.d("Exception", e.toString())
        }

        return result
    }
}
