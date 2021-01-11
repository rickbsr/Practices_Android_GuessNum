package com.rick.guessnum.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.rick.guessnum.enums.ResultDescriptionEnum;
import com.rick.guessnum.vm.GuessNumViewModel;

import static com.rick.guessnum.enums.ResultDescriptionEnum.GAME_DESCRIPTION_CORRECT_NUM;
import static com.rick.guessnum.enums.ResultDescriptionEnum.GAME_DESCRIPTION_INIT;

public class BindingAdapterUtils {

    @BindingAdapter(value = {"resDescription", "guessTimes"})
    public static void setResDescriptionStr(TextView textView, ResultDescriptionEnum resDescription, int guessTimes) {

        if (!GAME_DESCRIPTION_INIT.equals(resDescription)) {
            String resDescriptionStr = textView.getResources().getString(resDescription.getResourceId());

            if (GAME_DESCRIPTION_CORRECT_NUM.equals(resDescription)) {
                resDescriptionStr = String.format(resDescriptionStr, guessTimes);
            }

            textView.setText(resDescriptionStr);
        }
    }

    @BindingAdapter(value = {"guessNumViewModel"})
    public static void checkedInputText(EditText editText, GuessNumViewModel guessNumViewModel) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                guessNumViewModel.checkedInputNum(s.toString());
            }
        });
    }
}
