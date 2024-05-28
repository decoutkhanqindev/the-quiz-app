package com.example.thequizapp.repository;

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
    private MutableLiveData<QuestionList> questionListMutableLiveData;
    public QuizRepository() {
        this.questionsAPI = new RetrofitInstance()
                .getRetrofitInstance()
                .create(QuestionsAPI.class);
    }

    public MutableLiveData<QuestionList> getQuestionsListFromAPI(){
        Call<QuestionList> response = questionsAPI.getQuestionsAPI();
        response.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(@NonNull Call<QuestionList> call, @NonNull Response<QuestionList> response) {
                QuestionList questionsListFromAPi = response.body();
                questionListMutableLiveData.setValue(questionsListFromAPi);
            }

            @Override
            public void onFailure(@NonNull Call<QuestionList> call, @NonNull Throwable throwable) {
            }
        });

        return questionListMutableLiveData;
    }
}
