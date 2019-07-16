package com.patrones.apppanamericanos.views.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.patrones.apppanamericanos.viewmodels.HelpViewModel;
import com.patrones.apppanamericanos.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class HelpFragment extends Fragment {

    @BindView(R.id.dates_profile_unlogged_iv2)
    ImageView image;

    private HelpViewModel mViewModel;

    public static HelpFragment newInstance() {
        return new HelpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help_fragment, container, false);

        ButterKnife.bind(this, view);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lima2019.pe/"));
                startActivity(browserIntent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HelpViewModel.class);
        // TODO: Use the ViewModel
    }

}
