package com.patrones.apppanamericanos.utils.viewtemplates.design;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

public interface ILayoutManager {
    void set(View base, View loading, View... alternatives);
    void showLoading();
    void showBase();
    void showAlternative(int index, ILayoutManagerSetup layoutManagerSetup);

    interface ILayoutManagerSetup{
        void setUp();
    }
}
