package com.patrones.apppanamericanos.utils.viewtemplates;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.patrones.apppanamericanos.utils.viewtemplates.design.ILayoutManager;

public abstract class MyFragment extends Fragment implements ILayoutManager {
    protected View baseLayout;
    protected View loadingLayout;
    protected View[] alternativesLayout;

    @Override
    public void set(View base, View loading, View... alternatives) {
        this.baseLayout = base;
        this.loadingLayout = loading;
        this.alternativesLayout = alternatives;
    }

    @Override
    public void showLoading() {
        baseLayout.setVisibility(View.GONE);
        if(alternativesLayout != null)
            for (View alternative : alternativesLayout) {
                alternative.setVisibility(View.GONE);
            }

        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBase() {
        loadingLayout.setVisibility(View.GONE);
        if(alternativesLayout != null)
            for (View alternative : alternativesLayout) {
                alternative.setVisibility(View.GONE);
            }

        baseLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAlternative(int index, ILayoutManagerSetup layoutManagerSetup) {
        if (alternativesLayout != null) {
            if (index < alternativesLayout.length) {
                loadingLayout.setVisibility(View.GONE);
                baseLayout.setVisibility(View.GONE);

                layoutManagerSetup.setUp();
                alternativesLayout[index].setVisibility(View.VISIBLE);
            }
        }
    }
}
