package com.rick.guessnum.utils;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.rick.guessnum.R;
import com.rick.guessnum.vm.GuessNumViewModel;

public class BindingAdapterUtils {

    @BindingAdapter(value = {"resDescription", "guessTimes"}, requireAll = false)
    public static void setResDescriptionStr(TextView textView, GuessNumViewModel.ResultDescription resDescription, int guessTimes) {
        if (resDescription == null) return;

        final String resDescriptionStr;
        switch (resDescription) {
            case GAME_DESCRIPTION_START_GAME:
                resDescriptionStr = getDescriptionStrStr(textView, R.string.game_description_start_game);
                break;
            case GAME_DESCRIPTION_EMPTY_INPUT:
                resDescriptionStr = getDescriptionStrStr(textView, R.string.game_description_empty_input);
                break;
            case GAME_DESCRIPTION_CORRECT_NUM:
                resDescriptionStr = String.format(getDescriptionStrStr(textView, R.string.game_description_correct_num), guessTimes);
                break;
            case GAME_DESCRIPTION_OUT_OF_RANGE:
                resDescriptionStr = getDescriptionStrStr(textView, R.string.game_description_out_of_range);
                break;
            case GAME_DESCRIPTION_WRONG_AND_BIGGER:
                resDescriptionStr = getDescriptionStrStr(textView, R.string.game_description_wrong_and_bigger);
                break;
            case GAME_DESCRIPTION_WRONG_AND_SMALLER:
                resDescriptionStr = getDescriptionStrStr(textView, R.string.game_description_wrong_and_smaller);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + resDescription);
        }
        textView.setText(resDescriptionStr);
    }

    private static String getDescriptionStrStr(TextView textView, int resDescriptionStrId) {
        return textView.getResources().getString(resDescriptionStrId);
    }
}
