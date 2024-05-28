package com.example.thequizapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.thequizapp.R;
import com.example.thequizapp.databinding.ActivityMainBinding;
import com.example.thequizapp.model.Question;
import com.example.thequizapp.model.QuestionList;
import com.example.thequizapp.viewmodel.QuizViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private QuizViewModel quizViewModel;
    private List<Question> questionsList;
    private int result = 0;
    private int totalQuestions = 0;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);
        result = 0;
        totalQuestions = 0;

        displayFirstQuestion();

        mainBinding.btnNextQuestion.setOnClickListener(v -> displayNextQuestions());
    }

    private void displayFirstQuestion() {
        // observing livedata from a viewmodel
        quizViewModel.getQuestionListMutableLiveData().observe(this, new Observer<QuestionList>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(QuestionList questions) {
                questionsList = questions;
                mainBinding.txtQuestion.setText("Question 1: " + questions.get(0).getQuestion());
                mainBinding.txtOption1.setText(questions.get(0).getOption1());
                mainBinding.txtOption2.setText(questions.get(0).getOption2());
                mainBinding.txtOption3.setText(questions.get(0).getOption3());
                mainBinding.txtOption4.setText(questions.get(0).getOption4());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayNextQuestions() {
        // Direct the user to the Results activity
        if (mainBinding.btnNextQuestion.getText().equals("Finish")) {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
            finish();
        }

        // Displaying the question
        int selectedOption = mainBinding.radioGroup.getCheckedRadioButtonId();
        if (selectedOption != -1) { // neu da chon option thi lay ra id cua option do
            RadioButton radioButtonOption = findViewById(selectedOption);

            // ktra xem con co cau hoi nao chua dc hien thi hay khong
            // neu tong so cau hoi - i (so cau hoi hien tai) > 0 tuc la con` co cau hoi chua dc hien thi
            if ((questionsList.size() - currentIndex) > 0) {
                totalQuestions = questionsList.size();
                // neu option la correct option
                if (radioButtonOption.getText().toString().equals(questionsList.get(currentIndex).getCorrectOption())) {
                    result++; // ket qua tang them 1
                    mainBinding.txtCorrectResult.setText("Correct Answers: " + result);
                }

                if (currentIndex == 0) currentIndex++;

                // Displaying the next Questions
                mainBinding.txtQuestion.setText("Question " + (currentIndex + 1) + ": " + questionsList.get(currentIndex).getQuestion());
                mainBinding.txtOption1.setText(questionsList.get(currentIndex).getOption1());
                mainBinding.txtOption2.setText(questionsList.get(currentIndex).getOption2());
                mainBinding.txtOption3.setText(questionsList.get(currentIndex).getOption3());
                mainBinding.txtOption4.setText(questionsList.get(currentIndex).getOption4());

                // Check if it is the last question
                if (currentIndex == (questionsList.size() - 1)) {
                    mainBinding.btnNextQuestion.setText("Finish");
                }
                mainBinding.radioGroup.clearCheck();
                currentIndex++;
            } else {
                if (radioButtonOption.getText().toString().equals(questionsList.get(currentIndex - 1).getCorrectOption())) {
                    result++;
                    mainBinding.txtCorrectResult.setText("Correct Answers: " + result);
                }
            }
        } else {
            Toast.makeText(this, "You need to make a selection", Toast.LENGTH_SHORT).show();
        }
    }
}