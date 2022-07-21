package com.example.perfectelectronics.ui.sellonsellsmart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SellOnSellSMartViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SellOnSellSMartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}