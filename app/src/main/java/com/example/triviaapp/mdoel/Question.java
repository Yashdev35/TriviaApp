package com.example.triviaapp.mdoel;
//model package will contain all the model of the data that we will be using in our app

import androidx.annotation.NonNull;

public class Question {
private String answer;
private boolean answerTrue;
//    public  Question() {
//    }
//    public Question(String answer, boolean answerTrue) {
//        this.answer = answer;
//        this.answerTrue = answerTrue;
//    }
//empty constructor will allow us to not always pass an argument


    public void setAnswer(String answer) {
        this.answer = answer;
    }




    public boolean getAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }
    public String getAnswer() {
        return answer;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "Question(answer variable)='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
