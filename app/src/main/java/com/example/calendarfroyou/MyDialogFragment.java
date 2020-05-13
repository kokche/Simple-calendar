package com.example.calendarfroyou;

import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;



public class MyDialogFragment extends AppCompatDialogFragment {
    public void onClick(View view) {
        FragmentManager manager = getFragmentManager();
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(manager, "myDialog");
    }
}
