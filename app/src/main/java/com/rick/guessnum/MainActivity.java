package com.rick.guessnum;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rick.guessnum.databinding.ActivityMainBinding;
import com.rick.guessnum.vm.GuessNumViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataBinding();
        mainBinding.getGuessNumViewModel().startGame();
    }

    private void initDataBinding() {
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setGuessNumViewModel(new ViewModelProvider(this).get(GuessNumViewModel.class));
        mainBinding.setLifecycleOwner(this);
    }
}
