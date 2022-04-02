package com.example.myfirebase_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var users = ArrayList<Person>()

    lateinit var myName: TextView
    lateinit var myButton: Button
    lateinit var myRV: RecyclerView


    val changeListener: ValueEventListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.hasChildren()){
                //great we have kids
                var count = snapshot.childrenCount
                users.clear()
                for(child in snapshot.children){
                    val holdData = child.getValue(Person::class.java)
                    users.add(holdData!!)
                    Log.i("child", child.key.toString())
                    Log.i("value", child.getValue().toString())
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        myButton = findViewById(R.id.addNameButton)
        myName = findViewById(R.id.name2Add)
        myRV = findViewById(R.id.recyclerView)


//        database.child("person").child("user").setValue("Jeff")
//        database.child("person").child("address").setValue("305 Shawnee Lane")

        var currentUser = Person("Jeff", "Cadieux", "305 Shawnee Lane", "cadieux77@gmail.com", "0001")
        database.child("users").child(currentUser.ID).setValue(currentUser)

        database = Firebase.database.getReference("/users")
        database.addValueEventListener(changeListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        database.removeEventListener(changeListener)
    }

}