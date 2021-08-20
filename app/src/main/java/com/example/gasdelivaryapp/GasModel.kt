package com.example.gasdelivaryapp

class GasModel(val id: String, val productImage:String, val yHome: String, val yName: String,
               val yConduct:String,val yKilograms:String, val yAddres:String, val yRoad: String)  {
    constructor() :this("","","","","","","","")
}