package com.bignerdranch.android.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
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
@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, boolean: Boolean){
           textView.setTextColor(getColorByState(textView.context, boolean))
    }

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar:ProgressBar, boolean: Boolean){
    val color = getColorByState(progressBar.context, boolean)
    progressBar.progressTintList= ColorStateList.valueOf(color)
}


private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_dark
    } else {
        android.R.color.holo_red_dark
    }
    return ContextCompat.getColor(context, colorResId)

}
@BindingAdapter("numberAsText")
fun bindingNumberAsText(textView: TextView, int:Int){
textView.text=int.toString()
}



