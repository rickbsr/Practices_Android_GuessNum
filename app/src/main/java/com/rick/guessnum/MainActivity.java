package com.rick.guessnum;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rick.guessnum.constant.GameConstant;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;

    private TextView tv_minNum;
    private TextView tv_maxNum;
    private TextView tv_guessTimes;
    private TextView tv_resDescription;
    private EditText ed_input;
    private Button btn_guess;

    private GuessNumHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initViews();
        startGame();
    }

    private void initViews() {
        Log.d(TAG, "findViews: ");

        tv_minNum = findViewById(R.id.tv_minNum);
        tv_maxNum = findViewById(R.id.tv_maxNum);
        tv_guessTimes = findViewById(R.id.tv_guessTimes);
        tv_resDescription = findViewById(R.id.tv_resDescription);

        ed_input = findViewById(R.id.ed_input);
        ed_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == GameConstant.GAME_INPUT_MAX_LENGTH &&
                        Integer.parseInt(s.toString()) > GameConstant.GAME_END_NUMBER) {
                    ed_input.setText("");
                }
            }
        });

        btn_guess = findViewById(R.id.btn_guess);
        btn_guess.setOnClickListener(v -> {
            final String inputStr = ed_input.getText().toString();
            if (inputStr.isEmpty()) {
                tv_resDescription.setText(R.string.game_description_empty_input);
            } else {
                guessNum(Integer.parseInt(inputStr));
            }

            ed_input.setText("");
        });

        findViewById(R.id.btn_reset).setOnClickListener(v -> startGame());
    }

    private void startGame() {
        helper = new GuessNumHelper(mContext.getApplicationContext());
        setGuessNumUi();
        btn_guess.setEnabled(true);
    }

    private void guessNum(int input) {
        boolean isGameOver = helper.guessNum(input);
        setGuessNumUi();
        if (isGameOver) {
            btn_guess.setEnabled(false);
        }
    }

    private void setGuessNumUi() {
        tv_minNum.setText(String.valueOf(helper.getMinNum()));
        tv_maxNum.setText(String.valueOf(helper.getMaxNum()));
        tv_guessTimes.setText(String.valueOf(helper.getGuessTimes()));
        tv_resDescription.setText(helper.getResDescription());
        ed_input.setText("");
    }

    private static class GuessNumHelper {
        private final Context mContext;

        private final int answerNum;
        private int minNum;
        private int maxNum;
        private int guessTimes;
        private String resDescription;

        public GuessNumHelper(Context context) {
            mContext = context;

            answerNum = new Random().nextInt(GameConstant.GAME_END_NUMBER) + 1;
            minNum = GameConstant.GAME_START_NUMBER;
            maxNum = GameConstant.GAME_END_NUMBER;
            guessTimes = GameConstant.GAME_TIMES_INIT;

            resDescription = mContext.getString(R.string.game_description_start_game);
        }

        public boolean guessNum(int input) {
            boolean isCorrectNum = false;
            if (input >= maxNum || input <= minNum) {
                resDescription = mContext.getString(R.string.game_description_out_of_range);
            } else {
                guessTimes++;
                if (answerNum == input) {
                    resDescription = String.format(mContext.getString(R.string.game_description_correct_num), guessTimes);
                    isCorrectNum = true;
                } else if (answerNum > input) {
                    resDescription = mContext.getString(R.string.game_description_wrong_and_bigger);
                    minNum = input;
                } else {
                    resDescription = mContext.getString(R.string.game_description_wrong_and_smaller);
                    maxNum = input;
                }
            }
            return isCorrectNum;
        }

        public int getMinNum() {
            return minNum;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public int getGuessTimes() {
            return guessTimes;
        }

        public String getResDescription() {
            return resDescription;
        }
    }
}