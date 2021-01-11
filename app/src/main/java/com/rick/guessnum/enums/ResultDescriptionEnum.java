package com.rick.guessnum.enums;

import com.rick.guessnum.R;

public enum ResultDescriptionEnum {
    GAME_DESCRIPTION_INIT,
    GAME_DESCRIPTION_START_GAME(R.string.game_description_start_game), // 開始遊戲！
    GAME_DESCRIPTION_EMPTY_INPUT(R.string.game_description_empty_input), // 請輸入數字！
    GAME_DESCRIPTION_OUT_OF_RANGE(R.string.game_description_out_of_range), // 數字超出範圍。
    GAME_DESCRIPTION_CORRECT_NUM(R.string.game_description_correct_num), // 恭喜答對了，總共猜了 %d 次。
    GAME_DESCRIPTION_WRONG_AND_BIGGER(R.string.game_description_wrong_and_bigger), // 答錯了，再大一點。
    GAME_DESCRIPTION_WRONG_AND_SMALLER(R.string.game_description_wrong_and_smaller); // 答錯了，再小一點。

    ResultDescriptionEnum() {
        this.resourceId = 0;
    }

    ResultDescriptionEnum(int resourceId) {
        this.resourceId = resourceId;
    }

    private final int resourceId;

    public int getResourceId() {
        return resourceId;
    }
}
