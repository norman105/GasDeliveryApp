 package com.example.gasdelivaryapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_upload.*
import java.util.*

class Upload : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var productImage: Uri
    var yHome: String = ""
    var yName: String = ""
    var yConduct: String = ""
    var yKilograms: String = ""
    var yaddres: String = ""
    var yRoad: String = ""

    //tags for db and storage
    private var firebaseStorage: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    private var databaseRef: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        //initialize of product
        auth = FirebaseAuth.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        storageRef =FirebaseStorage.getInstance().reference
        databaseRef = FirebaseDatabase.getInstance().getReference("gasData")
       //pick image shared
        val bundle:Bundle? = intent.extras
        val imagepicked:Int = bundle!!.getInt("image")
        Log.d("shareData","image name " + imagepicked.toString())
        //set image to image view
        imageb.setImageResource(imagepicked)
        ///estate picked spinner
        val estate = resources.getStringArray(R.array.Estate)
        val spinner = findViewById<Spinner>(R.id.estate)
                            if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, estate)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Toast.makeText(this@Upload, "" + "" + estate[position], Toast.LENGTH_SHORT).show() }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
        signout.setOnClickListener {
          auth.signOut()
            updateUI()
        }
        submit.setOnClickListener {
            yHome = estate.toString()
            yName = name.text.toString()
            yConduct= contactnumber.text.toString()
            yKilograms =contactnumberS.text.toString()
            yaddres = homeAddress.text.toString()
            yRoad =road.text.toString()

            if (yHome.isEmpty() && yName.isEmpty() && yConduct.isEmpty() && yKilograms.isEmpty() && yaddres.isEmpty() && yRoad.isEmpty()) {
                Toast.makeText(applicationContext, "Field should not be emmpty", Toast.LENGTH_SHORT).show()
            } else {
                submitToFirebase(productImage,yHome,yName,yConduct,yKilograms,yaddres,yRoad)
            } 

        }


    }


    private fun submitToFirebase(productImage: Uri, yHome: String, yName: String, yConduct: String, yKilograms: String, yaddres: String, yRoad: String) {

        //process to take database
        ///uuid  ref to image name
        val ref = storageRef?.child("gasimage /" + UUID.randomUUID().toString())
        //put the image to storage bucket
        val uploadTask = ref?.putFile(this.productImage!!)
        //monitoring the process
        val urlTask= uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>
        {
            if (!it.isSuccessful){
                it.exception?.let {
                    throw it
                }
            }
            return@Continuation ref.downloadUrl

        })?.addOnCompleteListener {
            if (it.isSuccessful) {
                //checking if upload process is complete ,if complete save
                //download url for image
                val downloadUri = it.result
                Log.d("image","download url" + downloadUri.toString())
                //beginin the process of taking of data to realtime database
                //generate id for ref
                val gasId = databaseRef?.push()?.key
                //hold this data in our model
                val gas =gasId.let { GasModel(gasId.toString(),downloadUri.toString(),yHome,yName,yConduct,yKilograms,yaddres,
                        yRoad) }
                //sending values to realtime database
                if (gasId != null) {
                    databaseRef?.child(gasId)?.setValue(gas)?.addOnCompleteListener {
                            Toast.makeText(applicationContext, "Details added successfully", Toast.LENGTH_SHORT).show()

                        }?.addOnFailureListener {
                            Toast.makeText(applicationContext, "Error check the internet", Toast.LENGTH_SHORT).show()
                        }
                    //end of sending data to real time data
                }
            }else{
                Toast.makeText(applicationContext, "Error occured,check internet connection", Toast.LENGTH_SHORT).show()
            }

        }?.addOnFailureListener {
            //here u can get actual error from fire base
            val massageError = it.message
            Toast.makeText(applicationContext, "Error is", Toast.LENGTH_SHORT).show()

        }

    }



    private fun updateUI() {
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)

    }




}