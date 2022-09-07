package com.bignerdranch.android.composition.domain.entity

 data class Question(
     val sum:Int,
     val visibleNumder:Int,
     val options:List<Int>
 ) {
     val rightAnswer:Int
     get()=sum-visibleNumder
}