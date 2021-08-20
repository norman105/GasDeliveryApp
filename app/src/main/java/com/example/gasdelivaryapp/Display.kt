package com.example.gasdelivaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Display : AppCompatActivity() {
    private var databaseReference: DatabaseReference? =null
    lateinit var recyclerView: RecyclerView
    //declare mutable list
    lateinit var gaslist: MutableList<GasModel>
    lateinit var gasAdapter: GasAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        databaseReference =FirebaseDatabase.getInstance().getReference("gasData")
        //list
        gaslist = mutableListOf()
        //read data from fire base
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()) {
                    gaslist.clear()

                    for (h in snapshot.children) {

                        val bal = h.getValue(GasModel::class.java)
                        gaslist?.add(bal!!)
                    }

                    val adapter = GasAdapter(this@Display!!, gaslist)

                    recyclerView?.setAdapter(adapter)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "something went wrong", Toast.LENGTH_SHORT).show()
                Log.d("error","error is "+ error.details)
            }
        })
    }
}