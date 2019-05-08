package ru.sberbank.converter.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import ru.sberbank.converter.R;
import ru.sberbank.converter.data.repository.CurrencyRepository;
import ru.sberbank.converter.di.Injection;
import ru.sberbank.converter.entity.Currency;
import ru.sberbank.converter.interactor.ConverterInteractor;
import ru.sberbank.converter.interactor.CurrenciesInteractor;
import ru.sberbank.converter.presenter.ConverterPresenter;
import ru.sberbank.converter.ui.adapter.CurrencyAdapter;
import ru.sberbank.converter.ui.fragment.SelectCurrencyDialogFragment;
import ru.sberbank.converter.view.ConverterView;

public class ConverterActivity extends BaseActivity<ConverterPresenter>
        implements ConverterView, View.OnClickListener {
    private static final String TAG_SELECT_CURRENCY = "tag_select_currency";

    private EditText etSourceSum;
    private EditText etDestSum;
    private TextView tvSourceRatio;
    private TextView tvDestRatio;
    private TextView tvSourceCurrency;
    private TextView tvDestCurrency;
    private DialogFragment currencyDialog;

    private ConverterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        presenter = super.instantiatePresenter();

        initToolbar();
        initViews();
        findViewById(R.id.btnConvert).setOnClickListener(this);
    }

    private void initToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_converter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_swap) {
            presenter.swapCurrencies(getSourceSum());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        etSourceSum = findViewById(R.id.etSourceSum);
        etDestSum = findViewById(R.id.etDestSum);

        tvSourceRatio = findViewById(R.id.tvSourceRatio);
        tvDestRatio = findViewById(R.id.tvDestRatio);

        tvSourceCurrency = findViewById(R.id.tvSourceCurrency);
        tvSourceCurrency.setOnClickListener(sourceCurrencyClickListener);

        tvDestCurrency = findViewById(R.id.tvDestCurrency);
        tvDestCurrency.setOnClickListener(destCurrencyClickListener);
    }

    private View.OnClickListener sourceCurrencyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.onCurrencyClicked(true);
        }
    };

    private View.OnClickListener destCurrencyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.onCurrencyClicked(false);
        }
    };

    @Override
    public void showConvertingResult(float result) {
        etDestSum.setText(String.format(Locale.US, "%1$.2f", result));
    }

    @Override
    public void showConvertingError() {
        Toast.makeText(this, R.string.converting_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSelectCurrencyDialog(List<Currency> currencyList, boolean isSourceCurrency) {
        CurrencyAdapter.OnItemClickListener listener = isSourceCurrency
                ? sourceCurrencySelectedListener : destCurrencySelectedListener;
        currencyDialog = SelectCurrencyDialogFragment.newInstance(currencyList, listener);
        currencyDialog.show(getSupportFragmentManager(), TAG_SELECT_CURRENCY);
    }

    @Override
    public void showSourceCurrency(String currency) {
        tvSourceCurrency.setText(currency);
    }

    @Override
    public void showDestCurrency(String currency) {
        tvDestCurrency.setText(currency);
    }

    @Override
    public void showSourceRatio(String sourceCurrency, String destCurrency, float ratio) {
        tvSourceRatio.setText(getString(
                R.string.converter_ratio_format, sourceCurrency, ratio, destCurrency));
    }

    @Override
    public void showDestRatio(String sourceCurrency, String destCurrency, float ratio) {
        tvDestRatio.setText(getString(
                R.string.converter_ratio_format, destCurrency, ratio, sourceCurrency));
    }

    private CurrencyAdapter.OnItemClickListener sourceCurrencySelectedListener =
            new CurrencyAdapter.OnItemClickListener() {
                @Override
                public void onItemClicked(String currencyId) {
                    currencyDialog.dismiss();
                    presenter.onSourceCurrencySelected(getSourceSum(), currencyId);
                }
            };

    private CurrencyAdapter.OnItemClickListener destCurrencySelectedListener =
            new CurrencyAdapter.OnItemClickListener() {
                @Override
                public void onItemClicked(String currencyId) {
                    currencyDialog.dismiss();
                    presenter.onDestCurrencySelected(getSourceSum(), currencyId);
                }
            };

    private String getSourceSum() {
        return etSourceSum.getText().toString();
    }

    @Override
    public void onClick(View view) {
        presenter.convert(getSourceSum());
    }

    @Override
    protected ConverterPresenter providePresenter() {
        CurrencyRepository currencyRepository = Injection.provideCurrencyRepository(this);
        return new ConverterPresenter(
                new CurrenciesInteractor(currencyRepository),
                new ConverterInteractor(currencyRepository));
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, ConverterActivity.class);
    }
}