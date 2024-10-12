package com.example.spacebar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class Compiler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compiler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText field = findViewById(R.id.field);
        Button submit = findViewById(R.id.submit);
        TextView result = findViewById(R.id.result);
        result.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = String.valueOf(field.getText());
                submit.setVisibility(View.GONE);
                field.setVisibility(View.GONE);
                result.setText(new Code(code).res());
                result.setVisibility(View.VISIBLE);
            }
        });
    }
}

class Code {
    String text;
    public Code(String t) {
        text = t.replaceAll("[\n\b\r\t]", " ").replaceAll(" {2,}", " ").trim();
    }
    public String res() {
        String r = "";
        StringBuilder curr = new StringBuilder();
        ArrayList<Variable> vars = new ArrayList<>();
        ArrayList<ArrayList<String>> words = new ArrayList<>();
        ArrayList<String> statement = new ArrayList<>();
        final int len = text.length();
        char c;
        for (int i = 0; i < len; i++) {
            c = text.charAt(i);
            if (c == '.') {
                if (statement.isEmpty()) {
                    return "Syntax error.";
                }
                if (curr.length() > 0) {
                    statement.add(curr.toString());
                    curr = new StringBuilder();
                }
                words.add(statement);
                statement = new ArrayList<>();
            } else {
                if (c == ' ') {
                    if (curr.length() > 0) {
                        statement.add(curr.toString());
                        curr = new StringBuilder();
                    }
                } else {
                    if (isSpecial(c)) {
                        if (curr.length() > 0) {
                            statement.add(curr.toString());
                            curr = new StringBuilder();
                        }
                        statement.add(String.valueOf(c));
                    } else {
                        curr.append(c);
                    }
                }
            }
        }
        for (ArrayList<String> stat : words) {
            if (stat.size() < 2) {
                return "Syntax error.";
            }
            if (Objects.equals(stat.get(0), "make")) {
                if (stat.size() == 2 || isSpecial(stat.get(1).charAt(0))) {
                    return "Syntax error.";
                }
                statement = new ArrayList<>(stat);
                statement.remove(0);
                statement.remove(0);
                curr = new StringBuilder(parsed(statement, vars));
                if (Objects.equals(curr.toString(), "Syntax error.")) {
                    return "Syntax error.";
                }
                vars.add(new Variable(stat.get(1), Double.parseDouble(curr.toString())));
            } else {
                if (Objects.equals(stat.get(0), "purr")) {
                    statement = new ArrayList<>(stat);
                    statement.remove(0);
                    curr = new StringBuilder(parsed(statement, vars));
                    if (Objects.equals(curr.toString(), "Syntax error.")) {
                        return "Syntax error.";
                    }
                    r += curr + "\n";
                } else {
                    return "Syntax error.";
                }
            }
        }
        return r;
    }
    String parsed(ArrayList<String> stat, ArrayList<Variable> vars) {
        int last = -1;
        String curr;
        char c;
        ArrayList<String> s, spar = new ArrayList<>();
        final int len = stat.size();
        if (len == 1) {
            curr = stat.get(0);
            if (isSpecial(curr.charAt(0))) {
                return "Syntax error.";
            }
            if (isNum(curr.charAt(0))) {
                return stat.get(0);
            }
            for (Variable var : vars) {
                if (Objects.equals(var.name, curr)) {
                    return String.valueOf(var.val);
                }
            }
            return "Syntax error.";
        }
        for (int i = 0; i < len; i++) {
            curr = stat.get(i);
            if (Objects.equals(curr, "Syntax error.") || curr == null) {
                return "Syntax error.";
            }
            if (curr.equals(")")) {
                if (last == -1) {
                    return "Syntax error.";
                }
                s = new ArrayList<>(stat);
                for (int j = last + 1; j < i; j++) {
                    s.remove(last);
                    spar.add(stat.get(j));
                }
                s.remove(last);
                s.set(last, parsed(spar, vars));
                return parsed(s, vars);
            } else {
                if (curr.equals("(")) {
                    last = i;
                }
            }
        }
        for (int i = 0; i < len; i++) {
            curr = stat.get(i);
            c = curr.charAt(0);
            if (c == '^') {
                s = new ArrayList<>();
                s.add(stat.get(i - 1));
                double a = Double.parseDouble(parsed(s, vars));
                s = new ArrayList<>();
                s.add(stat.get(i + 1));
                double b = Double.parseDouble(parsed(s, vars));
                s = stat;
                s.remove(i);
                s.remove(i);
                s.set(i - 1, String.valueOf(Math.pow(a, b)));
                return parsed(s, vars);
            }
        }
        for (int i = 0; i < len; i++) {
            curr = stat.get(i);
            c = curr.charAt(0);
            if (c == '*' || c == '/') {
                s = new ArrayList<>();
                s.add(stat.get(i - 1));
                double a = Double.parseDouble(parsed(s, vars));
                s = new ArrayList<>();
                s.add(stat.get(i + 1));
                double b = Double.parseDouble(parsed(s, vars));
                s = stat;
                s.remove(i);
                s.remove(i);
                s.set(i - 1, String.valueOf(a * b));
                if (c == '/') {
                    if (b == 0) {
                        return "Syntax error.";
                    }
                    s.set(i - 1, String.valueOf(a / b));
                }
                return parsed(s, vars);
            }
        }
        for (int i = 0; i < len; i++) {
            curr = stat.get(i);
            c = curr.charAt(0);
            if (c == '+' || c == '-') {
                s = new ArrayList<>();
                s.add(stat.get(i - 1));
                double a = Double.parseDouble(parsed(s, vars));
                s = new ArrayList<>();
                s.add(stat.get(i + 1));
                double b = Double.parseDouble(parsed(s, vars));
                s = stat;
                s.remove(i);
                s.remove(i);
                s.set(i - 1, String.valueOf(a + b));
                if (c == '-') {
                    s.set(i - 1, String.valueOf(a - b));
                }
                return parsed(s, vars);
            }
        }
        if (last > -1) {
            return "Syntax error.";
        }
        return null;
    }
    boolean isSpecial(char c) {
        return (c == '/' || c == '+' || c == '-' || c == '^' || c == '*' || c == '(' || c == ')');
    }
    boolean isNum(char c) {
        return (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == ',');
    }
}

class Variable {
    String name;
    Double val;
    public Variable(String s, Double v) {
        name = s;
        val = v;
    }
}