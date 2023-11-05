package com.example.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.triviaapp.data.Repository;
import com.example.triviaapp.databinding.ActivityMainBinding;
import com.example.triviaapp.mdoel.Question;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    int questionIndex = 0;

    private ActivityMainBinding binding;
    private TextView textView;
    private boolean userdefinedAns;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // textView = findViewById(R.id.hello_world);
        //by using repository we implemented the control view and model architecture, we are getting the data from the repository
        // and displaying it in the view, which is modeled by the model class and singleton is app controller
        new Repository().getQuestion(questionArrayList -> {
            //when we create new Repository() we are calling the getQuestion method and passing the callback to synchronize the data
            //when main calls the repository it doesnt know that the task of repo is finished or not so that is why out put is []
            //but call back will tell the main that the task is finished and now it can use the data
            //Log.d("mainQ", "processFinished: " + questionArrayList.get(questionIndex));
            //textView.setText(questionArrayList.toString());
            //now we will be using data binding to display the question
            binding.questionDisplay.setText(questionArrayList.get(questionIndex).getAnswer());
            binding.questionNoText.setText("Question No: " + (questionIndex + 1) + "/" + questionArrayList.size());
            binding.buttonNext.setOnClickListener(v -> {
                questionIndex++;
                updateQuestion(questionArrayList);
            });
            binding.buttonPrevious.setOnClickListener(v -> {
                questionIndex--;
                updateQuestion(questionArrayList);
            });
            binding.buttonTrue.setOnClickListener(v -> {
                userdefinedAns = true;
                checkAnswer(questionArrayList);
                updateQuestion(questionArrayList);
            });
            binding.buttonFalse.setOnClickListener(v -> {
                userdefinedAns = false;
                checkAnswer(questionArrayList);
                updateQuestion(questionArrayList);
            });
            //there is a better method to use onclick listener when we have multiple buttons

        });


    }

    private void checkAnswer(ArrayList<Question> questionArrayList) {
        boolean answer = questionArrayList.get(questionIndex).getAnswerTrue();
        if (userdefinedAns == answer) {
            Snackbar.make(binding.cardView, "Correct Answer", Snackbar.LENGTH_SHORT).show();
            fadeAnimation();
        } else {
            Snackbar.make(binding.cardView, "Wrong Answer", Snackbar.LENGTH_SHORT).show();
            shakeAnimation();
        }
    }


    public void updateQuestion(ArrayList<Question> questionArrayList) {
        if (questionIndex >= 0 && questionIndex < questionArrayList.size()) {
            binding.questionDisplay.setText(questionArrayList.get(questionIndex).getAnswer());
            binding.questionNoText.setText("Question No: " + (questionIndex + 1) + "/" + questionArrayList.size());
        } else if (questionIndex >= questionArrayList.size()) {
            questionIndex--;
            Toast.makeText(MainActivity.this, "End of the questions", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "No previous Questions", Toast.LENGTH_SHORT).show();
        }
    }
   private void shakeAnimation(){
       //this method will be used to shake the card view when the user gives the wrong answer
       Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
       binding.cardView.setAnimation(shake);
         shake.setAnimationListener(new Animation.AnimationListener() {
              @Override
              public void onAnimationStart(Animation animation) {
              binding.questionDisplay.setTextColor(Color.RED);
              }

              @Override
              public void onAnimationEnd(Animation animation) {
                    binding.questionDisplay.setTextColor(Color.WHITE);
              }

              @Override
              public void onAnimationRepeat(Animation animation) {

              }
         });

   }
   private void fadeAnimation(){
       AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
         alphaAnimation.setDuration(100);
         alphaAnimation.setRepeatCount(1);
         alphaAnimation.setRepeatMode(Animation.REVERSE);
            binding.cardView.setAnimation(alphaAnimation);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    binding.questionDisplay.setTextColor(Color.GREEN);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.questionDisplay.setTextColor(Color.WHITE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
   }


}