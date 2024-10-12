package com.example.spacebar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Vector;
public class MainActivity extends AppCompatActivity {
    int index;
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
    }
    @Override
    protected void onStart() {
        super.onStart();
        Quiz quiz = new Quiz();
        TextView scoreText = findViewById(R.id.score);
        Button start = findViewById(R.id.start_button);
        TextView greet = findViewById(R.id.greeting_text);
        TextView question = findViewById(R.id.question);
        question.setVisibility(View.GONE);
        Button set1 = findViewById(R.id.set1), set2 = findViewById(R.id.set2), pc = findViewById(R.id.pc);
        set1.setVisibility(View.GONE);
        set2.setVisibility(View.GONE);
        pc.setVisibility(View.GONE);
        scoreText.setVisibility(View.GONE);
        Button answer1 = findViewById(R.id.answer1), answer2 = findViewById(R.id.answer2), answer3 = findViewById(R.id.answer3), answer4 = findViewById(R.id.answer4);
        answer1.setVisibility(View.GONE);
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                greet.setVisibility(View.GONE);
                set1.setVisibility(View.VISIBLE);
                set2.setVisibility(View.VISIBLE);
                pc.setVisibility(View.VISIBLE);
            }
        });
        set1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set1.setVisibility(View.GONE);
                set2.setVisibility(View.GONE);
                index = 0;
                displayQuestions(0, 0, quiz, question, answer1, answer2, answer3, answer4, scoreText);
            }
        });
        set2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set1.setVisibility(View.GONE);
                set2.setVisibility(View.GONE);
                index = 1;
                displayQuestions(0, 0, quiz, question, answer1, answer2, answer3, answer4, scoreText);
            }
        });
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), Compiler.class));
            }
        });
    }
    private void displayQuestions(int i, int score, Quiz quiz, TextView question, Button a1, Button a2, Button a3, Button a4, TextView s) {
        int setSize = quiz.sets.get(index).ques.size();
        s.setVisibility(View.VISIBLE);
        s.setText(String.valueOf(score));
        if (i < setSize) {
            String questionText = quiz.sets.get(index).ques.get(i).text;
            question.setVisibility(View.VISIBLE);
            question.setText(questionText);
            int numOfAnswers = quiz.sets.get(index).ques.get(i).ans.size();
            a1.setVisibility(View.VISIBLE);
            a1.setText(quiz.sets.get(index).ques.get(i).ans.get(0).text);
            if (numOfAnswers > 1) {
                a2.setVisibility(View.VISIBLE);
                a2.setText(quiz.sets.get(index).ques.get(i).ans.get(1).text);
            }
            if (numOfAnswers > 2) {
                a3.setVisibility(View.VISIBLE);
                a3.setText(quiz.sets.get(index).ques.get(i).ans.get(2).text);
            }
            if (numOfAnswers > 3) {
                a4.setVisibility(View.VISIBLE);
                a4.setText(quiz.sets.get(index).ques.get(i).ans.get(3).text);
            }
            a1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a1.setVisibility(View.GONE);
                    a2.setVisibility(View.GONE);
                    a3.setVisibility(View.GONE);
                    a4.setVisibility(View.GONE);
                    question.setVisibility(View.GONE);
                    int k = 0;
                    if (a1.getText() == quiz.sets.get(index).ques.get(i).correctAnswer)
                        k = 1;
                    displayQuestions(i + 1, score + k, quiz, question, a1, a2, a3, a4, s);
                }
            });
            a2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a1.setVisibility(View.GONE);
                    a2.setVisibility(View.GONE);
                    a3.setVisibility(View.GONE);
                    a4.setVisibility(View.GONE);
                    question.setVisibility(View.GONE);
                    int k = 0;
                    if (a2.getText() == quiz.sets.get(index).ques.get(i).correctAnswer)
                        k = 1;
                    displayQuestions(i + 1, score + k, quiz, question, a1, a2, a3, a4, s);
                }
            });
            a3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a1.setVisibility(View.GONE);
                    a2.setVisibility(View.GONE);
                    a3.setVisibility(View.GONE);
                    a4.setVisibility(View.GONE);
                    question.setVisibility(View.GONE);
                    int k = 0;
                    if (a3.getText() == quiz.sets.get(index).ques.get(i).correctAnswer)
                        k = 1;
                    displayQuestions(i + 1, score + k, quiz, question, a1, a2, a3, a4, s);
                }
            });
            a4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a1.setVisibility(View.GONE);
                    a2.setVisibility(View.GONE);
                    a3.setVisibility(View.GONE);
                    a4.setVisibility(View.GONE);
                    question.setVisibility(View.GONE);
                    int k = 0;
                    if (a4.getText() == quiz.sets.get(index).ques.get(i).correctAnswer)
                        k = 1;
                    displayQuestions(i + 1, score + k, quiz, question, a1, a2, a3, a4, s);
                }
            });
        }
    }
}
class QuestionSet {
    public Vector<Question> ques;
    public void addQuestion(Question question) {
        ques.add(question);
    }
    public QuestionSet() {
        ques = new Vector<>();
    }
}
class Question {
    public String text;
    public Vector<Answer> ans;
    public String correctAnswer;
    public Question(String t, Vector<Answer> a, String cor) {
        text = t;
        ans = a;
        correctAnswer = cor;
    }
}
class Answer {
    public String text;
    public Answer(String t) {
        text = t;
    }
}
class Quiz {
    public Vector<QuestionSet> sets;

    public Quiz() {
        sets = new Vector<>();
        QuestionSet qs1 = new QuestionSet(), qs2 = new QuestionSet();
        Answer a1 = new Answer("Calculations, but mostly just for fun."),
                a2 = new Answer("To get a job."),
                a3 = new Answer("None of the above.");
        Vector<Answer> answerSet1 = new Vector<>();
        answerSet1.add(a1);
        answerSet1.add(a2);
        answerSet1.add(a3);
        Answer a4 = new Answer("Yes."), a5 = new Answer("No.");
        Vector<Answer> answerSet2 = new Vector<>();
        answerSet2.add(a4);
        answerSet2.add(a5);
        qs1.addQuestion(new Question("Is CalcuBara cool?", answerSet2, "Yes."));
        qs1.addQuestion(new Question("What is CalcuBara made for?", answerSet1, "Calculations, but mostly just for fun."));
        sets.add(qs1);
        Answer a6 = new Answer("Ok.");
        Vector<Answer> answerSet3 = new Vector<>();
        answerSet3.add(a6);
        qs2.addQuestion(new Question("I'll think of more questions later...", answerSet3, "Ok."));
        sets.add(qs2);
    }
}