package com.example.puzzlegame;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private List<TextView> tiles;
    private final int[] solution = {1, 2, 3, 4, 5, 6, 7, 8, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.grid_layout);
        tiles = new ArrayList<>();

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            TextView tile = (TextView) gridLayout.getChildAt(i);
            tile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveTile((TextView) v);
                }
            });
            tiles.add(tile);
        }

        startNewGame();
    }

    private void startNewGame() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            numbers.add(i);
        }
        numbers.add(0);
        Collections.shuffle(numbers);

        for (int i = 0; i < tiles.size(); i++) {
            TextView tile = tiles.get(i);
            int number = numbers.get(i);
            if (number == 0) {
                tile.setText("");
            } else {
                tile.setText(String.valueOf(number));
            }
        }
    }

    private void moveTile(TextView tile) {
        int index = tiles.indexOf(tile);
        int emptyIndex = findEmptyTile();

        if (isAdjacent(index, emptyIndex)) {
            tiles.get(emptyIndex).setText(tile.getText());
            tile.setText("");

            if (checkWin()) {
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
                disableTiles();
            }
        }
    }

    private int findEmptyTile() {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getText().toString().isEmpty()) {
                return i;
            }
        }
        return -1;  // Should never happen
    }

    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / 3;
        int col1 = index1 % 3;
        int row2 = index2 / 3;
        int col2 = index2 % 3;

        return (Math.abs(row1 - row2) == 1 && col1 == col2) ||
                (Math.abs(col1 - col2) == 1 && row1 == row2);
    }

    private boolean checkWin() {
        for (int i = 0; i < tiles.size(); i++) {
            String text = tiles.get(i).getText().toString();
            int number = text.isEmpty() ? 0 : Integer.parseInt(text);
            if (number != solution[i]) {
                return false;
            }
        }
        return true;
    }

    private void disableTiles() {
        for (TextView tile : tiles) {
            tile.setClickable(false);
        }
    }
}
