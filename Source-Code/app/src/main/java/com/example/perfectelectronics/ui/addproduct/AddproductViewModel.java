package com.example.perfectelectronics.ui.addproduct;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddproductViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddproductViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}