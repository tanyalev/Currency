package ru.sberbank.converter.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Retain-instance fragment, который содержит в себе презентер
 */
public class PresenterHolderFragment<P> extends Fragment {
    private P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter() {
        return presenter;
    }

    public static <P> PresenterHolderFragment newInstance() {
        PresenterHolderFragment<P> fragment = new PresenterHolderFragment<>();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}