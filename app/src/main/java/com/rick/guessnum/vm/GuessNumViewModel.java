package com.rick.guessnum.vm;

import android.text.Editable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rick.guessnum.constant.GameConstant;

import java.util.Random;

/**
 * 1. 讓 ViewModel 僅處理畫面邏輯，是畫面邏輯，而非畫面的呈現。
 * 2. 理論上，ViewModel 不應該涉 Context 相關的處理。
 */
public class GuessNumViewModel extends ViewModel {
    private int answerNum;

    private int minNum;
    private int maxNum;

    private int guessTimes;

    private final MutableLiveData<GameState> gameStateMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> minNumMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> maxNumMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> guessTimesMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ResultDescription> resDescriptionMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> inputMutableLiveData = new MutableLiveData<>();

    public void startGame() {
        gameStateMutableLiveData.postValue(GameState.ONGOING);

        minNum = GameConstant.GAME_START_NUMBER;
        minNumMutableLiveData.postValue(minNum);

        maxNum = GameConstant.GAME_END_NUMBER;
        maxNumMutableLiveData.postValue(maxNum);

        guessTimes = GameConstant.GAME_TIMES_INIT;
        guessTimesMutableLiveData.postValue(guessTimes);

        answerNum = new Random().nextInt(GameConstant.GAME_END_NUMBER) + 1;

        resDescriptionMutableLiveData.postValue(ResultDescription.GAME_DESCRIPTION_START_GAME);
        inputMutableLiveData.postValue(GameConstant.INPUT_STRING_EMPTY);
    }

    public void guessNum(String inputNum) {
        if (inputNum.isEmpty()) {
            resDescriptionMutableLiveData.postValue(ResultDescription.GAME_DESCRIPTION_EMPTY_INPUT);
        } else {
            int input = Integer.parseInt(inputNum);
            if (input >= maxNum || input <= minNum) {
                resDescriptionMutableLiveData.postValue(ResultDescription.GAME_DESCRIPTION_OUT_OF_RANGE);
            } else {
                guessTimes++;
                guessTimesMutableLiveData.postValue(guessTimes);
                if (answerNum == input) {
                    gameStateMutableLiveData.postValue(GameState.GAME_OVER);
                    resDescriptionMutableLiveData.postValue(ResultDescription.GAME_DESCRIPTION_CORRECT_NUM);
                } else if (answerNum > input) {
                    resDescriptionMutableLiveData.postValue(ResultDescription.GAME_DESCRIPTION_WRONG_AND_BIGGER);
                    minNum = input;
                    minNumMutableLiveData.postValue(minNum);
                } else {
                    resDescriptionMutableLiveData.postValue(ResultDescription.GAME_DESCRIPTION_WRONG_AND_SMALLER);
                    maxNum = input;
                    maxNumMutableLiveData.postValue(maxNum);
                }
            }
        }
        inputMutableLiveData.postValue(GameConstant.INPUT_STRING_EMPTY);
    }

    public void checkInputNumAfter(Editable s) {
        if (s.length() == GameConstant.GAME_INPUT_MAX_LENGTH &&
                Integer.parseInt(s.toString()) > GameConstant.GAME_END_NUMBER) {
            inputMutableLiveData.postValue(GameConstant.INPUT_STRING_EMPTY);
        }
    }

    public enum GameState {
        ONGOING, GAME_OVER
    }

    public enum ResultDescription {
        GAME_DESCRIPTION_START_GAME, // 開始遊戲！
        GAME_DESCRIPTION_EMPTY_INPUT, // 請輸入數字！
        GAME_DESCRIPTION_OUT_OF_RANGE, // 數字超出範圍。
        GAME_DESCRIPTION_CORRECT_NUM, // 恭喜答對了，總共猜了 %d 次。
        GAME_DESCRIPTION_WRONG_AND_BIGGER, // 答錯了，再大一點。
        GAME_DESCRIPTION_WRONG_AND_SMALLER // 答錯了，再小一點。
    }

    /**
     * 以 LiveData 的方式提供呼叫，能夠避免被修改。
     */
    public LiveData<GameState> getGameStateLiveData() {
        return gameStateMutableLiveData;
    }

    public LiveData<Integer> getMinNumLiveData() {
        return minNumMutableLiveData;
    }

    public LiveData<Integer> getMaxNumLiveData() {
        return maxNumMutableLiveData;
    }

    public LiveData<Integer> getGuessTimesLiveData() {
        return guessTimesMutableLiveData;
    }

    public LiveData<ResultDescription> getResDescriptionLiveData() {
        return resDescriptionMutableLiveData;
    }

    public LiveData<String> getInputLiveData() {
        return inputMutableLiveData;
    }
}
