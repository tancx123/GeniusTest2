package com.example.test.ui.course

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.test.Class.Course
import com.example.test.R
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_computer_science.*
import kotlinx.android.synthetic.main.activity_course_content.*

class CourseContent : AppCompatActivity() {

    lateinit var mDatabase : DatabaseReference
    lateinit var tvOverview: TextView
    var idCourse = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_content)

        val toolbar = findViewById<View>(R.id.toolbar2) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)


        mDatabase = FirebaseDatabase.getInstance().getReference("Course")


        readCourse()


        /*Testing for button click to another page*/


        val btnStartCourse = findViewById<Button>(R.id.btnStartCourse)

        btnStartCourse.setOnClickListener {
            val intent = Intent(this, ComputerScience::class.java)
            intent.putExtra("id2", idCourse)
            startActivity(intent)
        }

    }

    private fun readCourse() {

        idCourse = intent.getStringExtra("id")
        val mDatabaseRef = mDatabase.child(idCourse)
        tvOverview = findViewById(R.id.tv_overview_content)


            mDatabaseRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {
                    Picasso.with(this@CourseContent).load(p0.child("image").value.toString().toUri()).into(imageView_course)
                    tvOverview.text = p0.child("desc").value as String

                    var count = 1
                    val builder = StringBuilder()

                    for (a in p0.child("Topic").children) {

                        builder.append(p0.child("Topic").child(count.toString()).child("name").value as String)
                        builder.append("\n")
                        count++
                    }

                    topicCovered.text = builder.toString()

                }
            }
        })

    }

    override fun onBackPressed() {


        super.onBackPressed()
        this.finish()
    }
}
