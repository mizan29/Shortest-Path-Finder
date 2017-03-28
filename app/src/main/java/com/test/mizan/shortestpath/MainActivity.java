package com.test.mizan.shortestpath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.test.mizan.shortestpath.implementation.CostManager;
import com.test.mizan.shortestpath.implementation.PathFinder;
import com.test.mizan.shortestpath.model.Graph;
import com.test.mizan.shortestpath.model.Output;
import com.test.mizan.shortestpath.parser.InputParser;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_text_input;
    private TextView text_view_error, text_view_completed, text_view_total_cost, text_view_path;
    private Button btn_find_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        edit_text_input = ((EditText) findViewById(R.id.edit_text_input));
        text_view_completed = ((TextView) findViewById(R.id.text_view_completed));
        text_view_total_cost = ((TextView) findViewById(R.id.text_view_total_cost));
        text_view_path = ((TextView) findViewById(R.id.text_view_path));
        text_view_error = ((TextView) findViewById(R.id.text_view_error));
        btn_find_path = (Button) findViewById(R.id.btn_find_path);
        btn_find_path.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_find_path) {
            getResult();
        }
    }

    private void getResult() {
        try {
            InputParser inputParser = new InputParser();
            CostManager manager = new CostManager(new PathFinder(50));
            text_view_error.setVisibility(View.GONE);
            Graph graph = inputParser.parseInput(edit_text_input.getText().toString());
            Output output = manager.result(graph);
            text_view_completed.setText(output.isCompleted() ? "Yes" : "No");
            text_view_total_cost.setText(String.valueOf(output.getTotalCost()));
            text_view_path.setText(pathValue(output.getPath()));
        } catch (RuntimeException e) {
            text_view_error.setVisibility(View.VISIBLE);
            text_view_error.setText(e.getMessage());
        }
    }

    private String pathValue(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            sb.append(path.get(i));
            if (i != path.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
