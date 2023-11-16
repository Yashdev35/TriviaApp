package com.example.triviaapp.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviaapp.controller.AppController;
import com.example.triviaapp.mdoel.Question;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

//this package will contain all the data that we will be using in our app
//please read the comments to understand the code thoroughly
public class Repository {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

     public List<Question> getQuestion(final AnswerListAsyncResponse callBack){//get question is a method used in the repository class
         //this method returns the list of questions (Question class Objects)
        //this method will return the list of questions we will be using volley to get the data from the url we will be using gson to parse the data  we will be using a callback to return the data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, (JSONArray response) -> {
            //now we are actually getting the json array
            //we will be using a for loop to iterate through the json array
            for (int i = 0; i < response.length(); i++) {
                try {
                    Question question = new Question();//using constructor to set a value for the question object
          question.setAnswer(response.getJSONArray(i).get(0).toString());//get(0) is getting the 1st element of the inner array // and getter to get the set or constructed value
          question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));//getBoolean(1) is getting the ans(2nd element) of the inner array
//now we are adding the question to the array list
                    questionArrayList.add(question);
// Log.d("questionlist", "getQuestion: " + questionArrayList); this log cat is working but when
//when we will try to create the question list in the main activity it will not work, the out put will be [] or and error java.lang error
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != callBack) callBack.processFinished(questionArrayList);
            //when the process is on going the callback will be null, but when the process is finished the callback will not be null
            //this says that if the callback is not null then call the process finished method
            //and question array list is the parameter, now when we use this in main activity
            // we will override that method for desired result like displaying the data
        }, error -> {
            Log.d("json", "onCreate: failed to load the request");
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        //the reason for getting the null pointer exception or [] out put is that we are returning the question array list before the volley request is completed
        //the program needs to be synchronized, other classes must when a certain class has finished its task and now they can use
        // the data that has been returned from the task
        //in order to remove the error we will be using a callback to return the data ,it is an interface
        // that will be implemented in the main activity
        return questionArrayList;
    }
}
