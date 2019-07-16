package com.patrones.apppanamericanos.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patrones.apppanamericanos.models.entities.Assistance;
import com.patrones.apppanamericanos.R;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssistanceAdapter extends RecyclerView.Adapter<AssistanceAdapter.ViewHolder> {

    private View.OnClickListener clickListener;
    private List<Assistance> assistances;
    private Context context;

    public AssistanceAdapter(View.OnClickListener clickListener, List<Assistance> assistances, Context context) {
        this.clickListener = clickListener;
        this.assistances = assistances;
        this.context = context;
    }

    @NonNull
    @Override
    public AssistanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.profile_fragment_event_cardview, parent, false);
        return new AssistanceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssistanceAdapter.ViewHolder holder, int position) {
        holder.Bind(assistances.get(position));
    }

    @Override
    public int getItemCount() {
        return assistances.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.eventname_profile_tv)
        TextView title;
        @BindView(R.id.eventdate_profile_tv)
        TextView dateTime;

        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);
        }

        public void Bind(final Assistance assistance){
            if(assistance != null){
                title.setText(assistance.getEvent().getTitle());
                dateTime.setText(new SimpleDateFormat("dd/MM/yyyy").format(assistance.getEvent().getDate()));

                mView.setOnClickListener(clickListener);
            }
        }
    }
}
