package ru.sberbank.converter.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import ru.sberbank.converter.R;
import ru.sberbank.converter.di.Injection;
import ru.sberbank.converter.interactor.CurrenciesInteractor;
import ru.sberbank.converter.presenter.SplashPresenter;
import ru.sberbank.converter.view.SplashView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashView {
    private SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = instantiatePresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected SplashPresenter providePresenter() {
        return new SplashPresenter(
                new CurrenciesInteractor(Injection.provideCurrencyRepository(this)));
    }

    @Override
    public void showRefreshErrorMessage() {
        Toast.makeText(this, R.string.refresh_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToConverterScreen() {
        startActivity(ConverterActivity.getStartIntent(this));
        finish();
    }
}