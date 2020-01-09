package com.example.test.ui.course

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.Class.Course
import com.example.test.Class.Topic
import com.example.test.R
import com.example.test.ui.tutor.AddCourse
import com.example.test.ui.tutor.Tutor
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_computer_science.*
import kotlinx.android.synthetic.main.course_list_layout.*
import kotlinx.android.synthetic.main.course_list_layout.view.*
import kotlinx.android.synthetic.main.fragment_course.*
import kotlinx.android.synthetic.main.fragment_course.view.*

class CourseFragment : Fragment() {

    lateinit var mRecylerView : RecyclerView
    lateinit var mDatabase : DatabaseReference
    private lateinit var courseViewModel: CourseViewModel


    lateinit var mDatabase2 : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        courseViewModel =
            ViewModelProviders.of(this).get(CourseViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_course, container, false)


//        view.imageBtnComputerScience.setOnClickListener {
//            val intent = Intent(activity, CourseContent::class.java)
//            startActivity(intent)
//        }

        /*view.button2.setOnClickListener {

            val intent = Intent(activity, AddCourse::class.java)
            startActivity(intent)
        }*/



        mRecylerView = view.findViewById(R.id.courseRecycler)
        mRecylerView.setLayoutManager(GridLayoutManager(context, 2))
        mDatabase = FirebaseDatabase.getInstance().getReference("Course")

        readCourse()
        logRecyclerView()



        return view

    }


    private fun readCourse() {

        mDatabase2 = FirebaseDatabase.getInstance().getReference("Course")

        mDatabase2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.exists()) {

                    //var i = p0.getChildrenCount().toInt()



                        // This is add course

//                        val course = Course("MAths", "good", i.plus(1).toString())
//                        mDatabase2.child(i.plus(1).toString()).setValue(course)
//
//                        var mDatabase3 = mDatabase2.child(i.plus(1).toString())
//
//                        var b = p0.child("topic").getChildrenCount().toInt()
//                        val topic = Topic("dwadadawdawd", "aw")
//                        mDatabase3.child("topic").child(b.plus(1).toString()).setValue(topic)

                        // This is Delete course
                       // mDatabase2 = FirebaseDatabase.getInstance().getReference("Course")
                       // mDatabase2.child("04").removeValue()
                    }

                }
            })
        }


    private fun logRecyclerView() {

        var FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Course, CourseViewHolder>(

            Course::class.java,
            R.layout.course_list_layout,
            CourseViewHolder::class.java,
            mDatabase
        ) {

            override fun populateViewHolder(viewHolder : CourseViewHolder?, model: Course?, position:Int) {
                viewHolder?.itemView?.test?.text = model?.title
                viewHolder?.itemView?.courseID?.text = model?.id
                Picasso.with(context).load(model?.image?.toUri()).into(viewHolder?.itemView?.courseImg)
            }
        }

        mRecylerView.adapter = FirebaseRecyclerAdapter
    }

    class CourseViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        
        init {

            itemView!!.setOnClickListener{

                val intent = Intent(itemView.context, CourseContent::class.java)
                intent.putExtra("title", itemView.test.text)
                intent.putExtra("id", itemView.courseID.text)
                itemView.context.startActivity(intent)
            }
        }
    }

}