package com.rick.guessnum;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.rick.guessnum.databinding.ActivityMainBinding;
import com.rick.guessnum.enums.ResultDescriptionEnum;
import com.rick.guessnum.vm.GuessNumViewModel;

public class MainActivity extends AppCompatActivity {
    private GuessNumViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewModels();
        initDataBinding();

        viewModel.startGame();
    }

    private void initViewModels() {
        viewModel = new ViewModelProvider(this).get(GuessNumViewModel.class);
    }

    private void initDataBinding() {
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setGuessNumViewModel(viewModel);
        mainBinding.setLifecycleOwner(this);
    }
}
