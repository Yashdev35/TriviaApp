package com.example.triviaapp.data;

import com.example.triviaapp.mdoel.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);//error was occuring due to not Adding <Question> in the array list
    //this will signal that the volley request has finished and now we can use the data
}
