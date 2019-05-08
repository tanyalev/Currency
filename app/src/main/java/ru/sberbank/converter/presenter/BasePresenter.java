package ru.sberbank.converter.presenter;

public class BasePresenter<V> {
    private V view;

    public void attachView(V view) {
        this.view = view;
        onViewAttach();
    }

    public void detachView() {
        this.view = null;
    }

    public V getView() {
        return view;
    }

    /**
     * Метод, который вызывается когда View впервые добавлена к презентеру
     */
    protected void onViewAttach() {
    }

    /**
     * Метод, который вызывается, когда презентер уничтожается и необходимо освободить ресурсы
     */
    public void onDestroy() {
    }
}