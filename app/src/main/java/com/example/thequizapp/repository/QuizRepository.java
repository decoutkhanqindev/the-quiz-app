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

    public MutableLiveData<QuestionList> getQuestionsListFromAPI(){
        Call<QuestionList> response = questionsAPI.getQuestionsAPI();
        response.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(@NonNull Call<QuestionList> call, @NonNull Response<QuestionList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    QuestionList questionsListFromAPi = response.body();
                    questionListMutableLiveData.setValue(questionsListFromAPi);
                } else{
                    Log.e("QuizRepository", "Response not successful: " + response.message());
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
