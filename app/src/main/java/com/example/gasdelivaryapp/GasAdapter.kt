package com.example.gasdelivaryapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class GasAdapter(private val context:Context,private val gas: List<GasModel>) :
    RecyclerView.Adapter<GasAdapter.GasViwHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GasAdapter.GasViwHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom,parent,false)
        return GasViwHolder(itemView)
    }

    override fun onBindViewHolder(holder: GasAdapter.GasViwHolder, position: Int) {
        //curent position
        val currentUser =gas[position]
        //binding data from model to adapter
        holder.userestate.text =currentUser.yHome
        holder.username.text =  currentUser.yName
        holder.usercontact.text= currentUser.yConduct
        holder.userkgs.text =currentUser.yKilograms
        holder.userhomeaddress.text=currentUser.yAddres
        holder.userstreet.text= currentUser.yRoad
        Glide.with(context)
            .load(currentUser.productImage)
            .into(holder.productimage)

        val kgs = currentUser.yKilograms
        val id = currentUser.id
        holder.custom.setOnClickListener {
            updateandDeleteDialog(kgs,id)
        }
    }

    private fun updateandDeleteDialog(kgs: String, id: String) {
        //raise dialog
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
        //attatching dialog to interface
        val dialogView = inflater.inflate(R.layout.updateanddeletedialoge, null)
        // view identification
        var updateprice =dialogView.findViewById<TextInputEditText>(R.id.editname)
        var btupdate =dialogView.findViewById<Button>(R.id.btupdate)
        var btdelete = dialogView.findViewById<Button>(R.id.btdelete)
         //customize diaolog box
        dialogBuilder.setTitle("Upadate and delete")
        //creat and show
        val dialog =dialogBuilder.create()
        dialog.show()
        //set buttons
        btupdate.setOnClickListener {
            var price =updateprice.text.toString()
            updateProductFirebase(price, id)
        }
        btdelete.setOnClickListener {
            deleteProductFIrebase(id)
        }
    }

    private fun deleteProductFIrebase(id: String) {
        val databaseReference =FirebaseDatabase.getInstance().getReference("heros").child(id)
        databaseReference.removeValue()
    }

    private fun updateProductFirebase(price: String, id: String) {

    }

    override fun getItemCount() = gas.size

    class GasViwHolder(itemView: View): RecyclerView.ViewHolder(itemView){
      var productimage = itemView.findViewById<ImageView>(R.id.imageView2)
        var userestate =itemView.findViewById<TextView>(R.id.textView2)
        var username =itemView.findViewById<TextView>(R.id.textView5)
        var usercontact =itemView.findViewById<TextView>(R.id.textView7)
        var userkgs =itemView.findViewById<TextView>(R.id.textView8)
        var userhomeaddress =itemView.findViewById<TextView>(R.id.textView9)
        var userstreet =itemView.findViewById<TextView>(R.id.textView10)
        var custom = itemView.findViewById<CardView>(R.id.cardv)
    }

}