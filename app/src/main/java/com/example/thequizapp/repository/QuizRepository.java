package com.example.thequizapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.thequizapp.model.QuestionList;
import com.example.thequizapp.retrofit.QuestionsAPI;
import com.example.thequizapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizRepository {
    private final QuestionsAPI questionsAPI;
    private final MutableLiveData<QuestionList> questionListMutableLiveData;

    public QuizRepository() {
        this.questionsAPI = new RetrofitInstance().getRetrofitInstance().create(QuestionsAPI.class);
        this.questionListMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<QuestionList> getQuestionsListFromAPI() {
        Call<QuestionList> call = questionsAPI.getQuestionsAPI();
        call.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(@NonNull Call<QuestionList> call, @NonNull Response<QuestionList> response) {
                if (response.isSuccessful()) {
                    questionListMutableLiveData.setValue(response.body());
                } else {
                    Log.e("QuizRepository", "API call failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuestionList> call, @NonNull Throwable throwable) {
                Log.e("QuizRepository", "API call failed", throwable);
            }
        });

        return questionListMutableLiveData;
    }
}

