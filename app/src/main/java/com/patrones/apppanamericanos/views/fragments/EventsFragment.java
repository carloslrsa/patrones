package com.patrones.apppanamericanos.views.fragments;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.patrones.apppanamericanos.viewmodels.EventsViewModel;
import com.patrones.apppanamericanos.models.entities.Discipline;
import com.patrones.apppanamericanos.models.entities.PlacePreview;
import com.patrones.apppanamericanos.models.entities.Sport;
import com.patrones.apppanamericanos.utils.adapters.EventReviewAdapter;
import com.patrones.apppanamericanos.utils.viewtemplates.MyFragment;
import com.patrones.apppanamericanos.models.entities.EventPreview;
import com.patrones.apppanamericanos.R;
import com.patrones.apppanamericanos.utils.observer.Observable;
import com.patrones.apppanamericanos.utils.observer.design.IObserver;
import com.patrones.apppanamericanos.utils.strategy.dates.AllDaysComparatorStrategy;
import com.patrones.apppanamericanos.utils.strategy.dates.design.IDateComparatorStrategy;
import com.patrones.apppanamericanos.utils.strategy.dates.NearDayComparatorStrategy;
import com.patrones.apppanamericanos.utils.strategy.dates.TodayComparatorStrategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsFragment extends MyFragment {

    //Alternative
    @BindView(R.id.image_globalstate_iv)
    ImageView imageState;
    @BindView(R.id.text_globalstate_tv)
    TextView textState;

    //Base
    @BindView(R.id.appbar_events_abl)
    AppBarLayout appBarLayout;
    @BindView(R.id.baselayout_events_cl)
    ConstraintLayout baseLayout;
    @BindView(R.id.loadinglayout_events_cl)
    ConstraintLayout loadingLayout;
    @BindView(R.id.alternativelayout_events_cl)
    ConstraintLayout alternativeLayout;
    @BindView(R.id.keyword_events_et)
    EditText keywordParam;
    @BindView(R.id.date_events_et)
    TextView dateParam;
    @BindView(R.id.sports_events_sp)
    Spinner sportsParam;
    @BindView(R.id.disciplines_events_sp)
    Spinner disciplinesParam;
    @BindView(R.id.places_events_sp)
    Spinner placesParam;
    @BindView(R.id.search_events_bt)
    Button searchButton;
    @BindView(R.id.list_events_rv)
    RecyclerView eventListResult;
    @BindView(R.id.datefiltercontainer_events_cl)
    ConstraintLayout datefilterLayour;
    @BindView(R.id.filterdate_events_sw)
    Switch filterdateSwitch;
    @BindView(R.id.neardate_events_sw)
    Switch neardateSwitch;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    int actualMonth = c.get(Calendar.MONTH);
    int actualDay = c.get(Calendar.DAY_OF_MONTH);
    int actualYear = c.get(Calendar.YEAR);

    private EventsViewModel mViewModel;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.events_fragment, container, false);
        ButterKnife.bind(this, view);

        set(baseLayout,loadingLayout,alternativeLayout);

        appBarLayout.setExpanded(false);

        showAlternative(0, new ILayoutManagerSetup() {
            @Override
            public void setUp() {
                textState.setText("¡Bienvenido!\nDesliza hacia abajo para empezar tu búsqueda.");
                imageState.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_pineapple_happy));
            }
        });

        eventListResult.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEvents();
            }
        });

        //Fechas
        String dayString = actualDay < 10 ? "0"+actualDay : String.valueOf(actualDay);
        String monthString = (actualMonth + 1) < 10 ? "0"+(actualMonth + 1) : String.valueOf((actualMonth + 1));

        dateParam.setText(dayString + "/" + monthString + "/" + actualYear);
        dateParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog getDate = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;

                        String dayString = dayOfMonth < 10 ? "0"+dayOfMonth : String.valueOf(dayOfMonth);
                        String monthString = month < 10 ? "0"+month : String.valueOf(month);
                        actualDay = dayOfMonth;
                        actualMonth = month;
                        actualYear = year;

                        dateParam.setText(dayString + "/" + monthString + "/" + year);
                    }
                },actualYear,actualMonth,actualDay);

                getDate.show();
            }
        });

        //Spinners
        sportsParam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    List<String> emptyArray = new ArrayList<>();
                    emptyArray.add("«Disciplinas»");
                    disciplinesParam.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, emptyArray));

                }else{
                    String sportName = (String) sportsParam.getSelectedItem();
                    String sportId = "";
                    for(Sport sportTO : mViewModel.getSports().getData()){
                        if (sportTO.getName().equals(sportName)) {
                            List<Discipline> disciplinesTO = mViewModel.getDisciplines().getData().get(sportTO.getId());
                            List<String> disciplinesNames = new ArrayList<>();
                            disciplinesNames.add("«Disciplinas»");
                            for(Discipline disciplineTO : disciplinesTO){
                                disciplinesNames.add(disciplineTO.getName());
                            }

                            disciplinesParam.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,disciplinesNames));
                            break;
                        }
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Switchs
        filterdateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    datefilterLayour.setVisibility(View.VISIBLE);
                } else {
                    datefilterLayour.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        mViewModel.getEvents().addObserver(new IObserver<List<EventPreview>>() {
            @Override
            public void update(Observable<List<EventPreview>> observable, List<EventPreview> data) {
                if(data == null || data.size() == 0)
                    showAlternative(0, new ILayoutManagerSetup() {
                        @Override
                        public void setUp() {
                            textState.setText("¡Lo sentimos!\nNo hemos encontrado coincidencias.");
                            imageState.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_pineapple_confused));
                        }
                    });
                else{
                    refreshEvents(data);
                    showBase();
                }
            }
        });
        mViewModel.getSports().addObserver(new IObserver<List<Sport>>() {
            @Override
            public void update(Observable<List<Sport>> observable, List<Sport> data) {
                List<String> sportNames = new ArrayList<>();
                sportNames.add("«Deportes»");
                for(Sport sportTO : data){
                    sportNames.add(sportTO.getName());
                }
                sportsParam.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sportNames));
            }
        });
        mViewModel.getDisciplines().addObserver(new IObserver<Map<String, List<Discipline>>>() {
            @Override
            public void update(Observable<Map<String, List<Discipline>>> observable, Map<String, List<Discipline>> data) {

            }
        });
        mViewModel.getPlaces().addObserver(new IObserver<List<PlacePreview>>() {
            @Override
            public void update(Observable<List<PlacePreview>> observable, List<PlacePreview> data) {
                List<String> placeNames = new ArrayList<>();
                placeNames.add("«Sedes»");
                for (PlacePreview placePreviewTO : data) {
                    placeNames.add(placePreviewTO.getName());
                }
                placesParam.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, placeNames));
            }
        });

        mViewModel.SearchSports(getContext());
        mViewModel.SearchDisciplines(getContext());
        mViewModel.SearchPlaces(getContext());
    }

    private void searchEvents(){
        String keywordParamString = keywordParam.getText().toString();
        String sportParamString = (String) sportsParam.getSelectedItem();
        String disciplineParamString = (String) disciplinesParam.getSelectedItem();
        String placeParamString = (String) placesParam.getSelectedItem();
        
        if(keywordParamString.isEmpty() && sportsParam.getSelectedItemId() == 0 && disciplinesParam.getSelectedItemId() == 0 && placesParam.getSelectedItemId() == 0 && !filterdateSwitch.isChecked()){
            Toast.makeText(getContext(), "¡Debe usar al menos un criterio de búsqueda!", Toast.LENGTH_SHORT).show();
            return;
        }

        IDateComparatorStrategy comparatorStrategy = null;
        if (filterdateSwitch.isChecked()) {
            Date date = new GregorianCalendar(actualYear, actualMonth - 1, actualDay).getTime();
            if (neardateSwitch.isChecked()) {
                comparatorStrategy = new NearDayComparatorStrategy(date, 2);
            }else{
                comparatorStrategy = new TodayComparatorStrategy(date);
            }
        }else{
            comparatorStrategy = new AllDaysComparatorStrategy();
        }

        showLoading();
        appBarLayout.setExpanded(false);
        mViewModel.SearchEvents(keywordParamString,comparatorStrategy,placeParamString,sportParamString,disciplineParamString,getContext());
    }

    private void refreshEvents(List<EventPreview> events){
        eventListResult.setAdapter(new EventReviewAdapter(events,getContext()));
    }

}
