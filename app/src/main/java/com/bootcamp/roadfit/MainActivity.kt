package com.bootcamp.roadfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.bootcamp.roadfit.LoginActivity
import com.bootcamp.roadfit.LoginActivity.Companion.useremail
import com.google.android.material.navigation.NavigationView
import me.tankery.lib.circularseekbar.CircularSeekBar

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mHandler: Handler? = null
    private var mInterval = 1000
    private var timeInSecond = 0L
    private var startButtonClicked = false




    private lateinit var drawer: DrawerLayout
    private lateinit var swIntervalMode: Switch
    private lateinit var swChallenges: Switch
    private lateinit var swVolumes: Switch

    private lateinit var npChallengeDistance: NumberPicker
    private lateinit var npChallengeDurationHH: NumberPicker
    private lateinit var npChallengeDurationMM: NumberPicker
    private lateinit var npChallengeDurationSS: NumberPicker

    private var challengeDistance: Float = 0f
    private var challengeDuration: Int = 0

    private lateinit var tvChrono: TextView


    private lateinit var csbRunWalk: CircularSeekBar





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    initToolbar()
        initNavigationView()

    }
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            signOut()
        }
    }

    fun initToolbar(){
        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initNavigationView() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener (this)

        val headerView: View = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView, false)
        navigationView.removeHeaderView(headerView)
        navigationView.addHeaderView(headerView)

        val tvUser: TextView = headerView.findViewById(R.id.tvUser)
        tvUser.text = useremail

    }


    fun callSignOut(view: View){
        signOut()
    }
    private fun signOut(){
        LoginActivity.useremail = ""

        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_record -> callRecordActivity()
            R.id.nav_item_signout -> signOut()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun callRecordActivity(){
        val intent = Intent(this, RecordActivity::class.java)
        startActivity(intent)
    }

    private var chronometer : Runnable = object : Runnable {
        override fun run() {
            try {
                timeInSecond += 1
                updateStopWatchView()
            }finally {
                mHandler!!.postDelayed(this, mInterval.toLong())
            }
        }

    }

    private fun updateStopWatchView() {
        TODO("Not yet implemented")
    }

    //   private fun updateStopWatchView(){
   //     tvChrono.text = getFormattedStopWatch(timeInSecond * 1000)
    //}

}
