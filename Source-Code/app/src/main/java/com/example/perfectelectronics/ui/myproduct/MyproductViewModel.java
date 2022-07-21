package com.example.perfectelectronics.ui.myproduct;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyproductViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyproductViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my Product");
    }

    public LiveData<String> getText() {
        return mText;
    }
}