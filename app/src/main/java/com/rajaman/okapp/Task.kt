package com.rajaman.okapp

class Task {
    companion object Factory{
        fun create() : Task = Task()
    }

    var objectId :String ?= null
    var firstName :String ?= null
    var lastName :String ?= null
    var gender :String ?= null
    var year :Int ?= null
    var month : Int ?= null
    var day :Int ?= null
    var source :String ?= null
    var destination :String ?= null
    var fare :Int ?= null
}