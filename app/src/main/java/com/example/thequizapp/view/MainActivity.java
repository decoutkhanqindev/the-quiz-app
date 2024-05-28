package com.example.thequizapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

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
    ActivityMainBinding mainBinding;
    QuizViewModel quizViewModel;
    List<Question> questionsList;
    static int result = 0;
    static int totalQuestions = 0;
    int i = 0;
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

        // databinding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // creating instance view model
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // resetting score
        result = 0;
        totalQuestions = 0;

        // display first question
        displayFirstQuestion();

        mainBinding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFirstQuestions();
            }
        });
    }

    private void displayFirstQuestion(){
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

    private void displayFirstQuestions() {
        // Direct the user to the Results activity
        if(mainBinding.btnNextQuestion.getText().equals("Finish")){
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
            finish();
        }

        // Displaying the question
        int selectedOption = mainBinding.radioGroup.getCheckedRadioButtonId();
        if (selectedOption != -1){
            RadioButton option = findViewById(selectedOption);
            // More Questions to Display??
            if()
        }
    }
}