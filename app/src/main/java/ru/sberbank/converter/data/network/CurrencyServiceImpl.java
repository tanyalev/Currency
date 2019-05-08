package ru.sberbank.converter.data.network;

import java.util.List;

import ru.sberbank.converter.data.network.adapter.CurrencyCoursesDeserializer;
import ru.sberbank.converter.data.repository.CurrencyDataSource;
import ru.sberbank.converter.entity.Currency;

public class CurrencyServiceImpl implements CurrencyService {
    private HttpClient httpClient;
    private CurrencyCoursesDeserializer adapter;
    private String urlStr;

    public CurrencyServiceImpl(HttpClient httpClient,
                               CurrencyCoursesDeserializer adapter, String urlStr) {
        this.httpClient = httpClient;
        this.adapter = adapter;
        this.urlStr = urlStr;
    }

    @Override
    public void getCurrencies(final CurrencyDataSource.CurrenciesCallback callback) {
        httpClient.getContentString(urlStr, new HttpClient.LoadCallback() {
            @Override
            public void onLoadSuccess(String content) {
                List<Currency> currencyList = adapter.transform(content);
                if (currencyList == null) {
                    callback.onLoadError();
                } else {
                    callback.onLoadCompleted(adapter.transform(content));
                }
            }

            @Override
            public void onLoadError(Exception exception) {
                callback.onLoadError();
            }
        });
    }

    @Override
    public void unsubscribe() {
        httpClient.cancelDownload();
    }
}