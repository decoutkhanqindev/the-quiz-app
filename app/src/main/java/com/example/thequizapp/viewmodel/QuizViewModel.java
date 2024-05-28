package com.example.thequizapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thequizapp.model.QuestionList;
import com.example.thequizapp.repository.QuizRepository;

public class QuizViewModel extends ViewModel {
    private final QuizRepository quizRepository;
    private final MutableLiveData<QuestionList> questionListMutableLiveData;

    public QuizViewModel() {
        this.quizRepository = new QuizRepository();
        this.questionListMutableLiveData = quizRepository.getQuestionsListFromAPI();
    }

    public MutableLiveData<QuestionList> getQuestionListMutableLiveData() {
        return questionListMutableLiveData;
    }
}
