package com.example.test.ui.tutor

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.Class.Course
import com.example.test.Class.Topic
import com.example.test.R
import com.example.test.ui.course.CourseViewModel
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_course.*
import kotlinx.android.synthetic.main.fragment_course.*
import kotlinx.android.synthetic.main.fragment_course.view.*

class AddCourse : AppCompatActivity() {

    //internal lateinit var sp :Spinner
    lateinit var mRecylerView : RecyclerView
    lateinit var mDatabase : DatabaseReference

    lateinit var imgurl : Uri
    lateinit var img : ImageView
    lateinit var btnUpload : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        /*val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listOf("None", "Top", "Bottom"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        positionSpinner.adapter = adapter*/

        /*sp = findViewById<Spinner>(R.id.spnTopicCover)
        val topicCovered = arrayOf("Binary", "Logic")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, topicCovered)
        sp.adapter = adapter*/

        mDatabase = FirebaseDatabase.getInstance().getReference("Course")

        val toolbar = findViewById<View>(R.id.toolbar2) as Toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add Course"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        btnAdd.setOnClickListener {

            if (TextUtils.isEmpty(ti_courseName.text) || TextUtils.isEmpty(ti_overview.text)) {

                Toast.makeText(this,"Please fill in all textbox!", Toast.LENGTH_SHORT).show()
            }

            else {

                val myIntent = Intent(this, AddTopicDetail::class.java)
                myIntent.putExtra("courseTitle", ti_courseName.text.toString())
                myIntent.putExtra("courseOverview", ti_overview.text.toString())
                myIntent.putExtra("courseImg", imgurl.toString())
                startActivity(myIntent)
            }
        }

        btnUpload = findViewById<Button>(R.id.btnUploadPhoto)
        img = findViewById<ImageView>(R.id.imageView)

        btnUpload.setOnClickListener{

            filechooser()
        }
    }

    private fun filechooser() {

        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {

            imgurl = data.getData()!!
            Picasso.with(this).load(imgurl).into(img)
        }

    }

    override fun onSupportNavigateUp(): Boolean {

        onBackPressed()
        return true
    }

    private fun addCourse() {

//        mDatabase2 = FirebaseDatabase.getInstance().getReference("Course")
//
//        mDatabase2.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//
//                if (p0.exists()) {
//
//                    var i = p0.getChildrenCount().toInt()
//
//                    btnAdd.setOnClickListener {





                        // This is add course
                        /*val courseName = findViewById<EditText>(R.id.ti_courseName)
                        val courseOverview = findViewById<EditText>(R.id.ti_overview)

                        val course = Course(courseName.text.toString(), courseOverview.text.toString(), i.plus(1).toString())
                        mDatabase2.child(i.plus(1).toString()).setValue(course)

                        var mDatabase3 = mDatabase2.child(i.plus(1).toString())

                        var b = p0.child("topic").getChildrenCount().toInt()
                        val topic = Topic("dwadadawdawd", "aw")
                        mDatabase3.child("topic").child(b.plus(1).toString()).setValue(topic)
*/
                        // This is Delete course
                       // mDatabase2 = FirebaseDatabase.getInstance().getReference("Course")
                       // mDatabase2.child("04").removeValue()
//                    }
//
//                }
//            }
//        }
    }


}
