package com.rick.guessnum;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rick.guessnum.vm.GuessNumViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tv_minNum;
    private TextView tv_maxNum;
    private TextView tv_guessTimes;
    private TextView tv_resDescription;
    private EditText ed_input;
    private Button btn_guess;

    private GuessNumViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initObserves();

        viewModel.startGame();
    }

    /**
     * 畫面的處理依然是放在 Activity。
     */
    private void initObserves() {
        viewModel = new ViewModelProvider(this).get(GuessNumViewModel.class);
        viewModel.getMinNumLiveData().observe(this, min -> tv_minNum.setText(String.valueOf(min)));
        viewModel.getMaxNumLiveData().observe(this, max -> tv_maxNum.setText(String.valueOf(max)));
        viewModel.getGuessTimesLiveData().observe(this, times -> tv_guessTimes.setText(String.valueOf(times)));
        viewModel.getResDescriptionLiveData().observe(this, res -> {
            final String resDescriptionStr;
            switch (res) {
                case GAME_DESCRIPTION_START_GAME:
                    resDescriptionStr = getString(R.string.game_description_start_game);
                    break;
                case GAME_DESCRIPTION_EMPTY_INPUT:
                    resDescriptionStr = getString(R.string.game_description_empty_input);
                    break;
                case GAME_DESCRIPTION_CORRECT_NUM:
                    resDescriptionStr = String.format(
                            getString(R.string.game_description_correct_num), tv_guessTimes.getText().toString());
                    break;
                case GAME_DESCRIPTION_OUT_OF_RANGE:
                    resDescriptionStr = getString(R.string.game_description_out_of_range);
                    break;
                case GAME_DESCRIPTION_WRONG_AND_BIGGER:
                    resDescriptionStr = getString(R.string.game_description_wrong_and_bigger);
                    break;
                case GAME_DESCRIPTION_WRONG_AND_SMALLER:
                    resDescriptionStr = getString(R.string.game_description_wrong_and_smaller);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + res);
            }
            tv_resDescription.setText(resDescriptionStr);
        });
        viewModel.getGameStateLiveData().observe(this,
                gameState -> btn_guess.setEnabled(gameState != GuessNumViewModel.GameState.GAME_OVER));

        viewModel.getInputLiveData().observe(this, txt -> ed_input.setText(txt));
    }

    private void initViews() {
        Log.d(TAG, "initViews: ");

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
                viewModel.checkInputNumAfter(s);
            }
        });

        btn_guess = findViewById(R.id.btn_guess);
        btn_guess.setOnClickListener(v -> viewModel.guessNum(ed_input.getText().toString()));

        findViewById(R.id.btn_reset).setOnClickListener(v -> viewModel.startGame());
    }
}