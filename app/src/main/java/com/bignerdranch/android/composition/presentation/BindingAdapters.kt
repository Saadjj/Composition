package com.bignerdranch.android.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bignerdranch.android.composition.R
import com.bignerdranch.android.composition.domain.entity.GameResult

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView:TextView, count: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, score: Int){
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        score
    )
}

@BindingAdapter("requiredPercentages")
fun bindRequiredPercentages(textView: TextView, percentage: Int){
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        percentage

    )
}

@BindingAdapter("scorePercentages")
 fun bindScorePercentages (textView: TextView,gameResult:GameResult){

    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult)
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult)=with(gameResult){
    if(countOfQuestions==0){
         0
    }else{
        return  (countOfRightAnswers*100)/countOfQuestions
    }
}

@BindingAdapter("resultEmodji")
fun bindResultEmodji(imageView: ImageView, winner:Boolean){
    imageView.setImageResource(
            getSmileResID(winner))
}

private fun getSmileResID(winner:Boolean): Int {
    return if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
}


