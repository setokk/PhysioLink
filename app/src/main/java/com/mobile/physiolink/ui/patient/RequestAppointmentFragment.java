package com.mobile.physiolink.ui.patient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentRequestAppointmentBinding;
import com.mobile.physiolink.databinding.ItemListTimeBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.ui.patient.adapter.AdapterForAppointmentHour;
import com.mobile.physiolink.ui.patient.viewmodel.RequestAppointmentViewModel;
import com.mobile.physiolink.util.DateFormatter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RequestAppointmentFragment extends Fragment
{
    private RequestAppointmentViewModel appointmentViewmodel;
    private AdapterForAppointmentHour adapter;

    private FragmentRequestAppointmentBinding binding;

    private ItemListTimeBinding itemListTimeBinding;

    private ArrayWeekDayFormatter weekDayFormatter;
    private MonthArrayTitleFormatter monthFormatter;

    private Calendar currentDate;
    private Calendar selectedDate;

    private boolean reopened = false;

    public RequestAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRequestAppointmentBinding.inflate(inflater, container, false);

        currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(currentDate.getTimeInMillis());

        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.MONTH, month - 1);
        selectedDate.set(Calendar.DAY_OF_MONTH, day);

        appointmentViewmodel = new ViewModelProvider(this).get(RequestAppointmentViewModel.class);
        appointmentViewmodel.getAvailableHours().observe(getViewLifecycleOwner(), hours ->
        {
            if (!reopened)
            {
                adapter.setHours(hours.getAvailableHoursOfDate(year, month, day));
                reopened = true;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        monthFormatter = new MonthArrayTitleFormatter(
            getResources().getTextArray(R.array.greek_months));
        weekDayFormatter = new ArrayWeekDayFormatter(
            getResources().getTextArray(R.array.greek_days));
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        adapter = new AdapterForAppointmentHour(new String[0]);
        binding.availableAppointmentHoursList.setAdapter(adapter);
        binding.availableAppointmentHoursList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.hourBtn.setOnClickListener(view1 ->
        {
            if(binding.availableAppointmentHoursList.getVisibility()==View.GONE){
                TransitionManager.beginDelayedTransition(binding.chooseAppointmentHourCardView,new AutoTransition());
                binding.availableAppointmentHoursList.setVisibility(View.VISIBLE);
            }
            else{
                TransitionManager.beginDelayedTransition(binding.chooseAppointmentHourCardView,new AutoTransition());
                binding.availableAppointmentHoursList.setVisibility(View.GONE);
            }
        });

        binding.availableAppointmentHoursList.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), binding.availableAppointmentHoursList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                            String text = adapter.getHours()[position];
                            binding.hourBtn.setText(text);
                            binding.availableAppointmentHoursList.setVisibility(View.GONE);
                    }
                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        initializeCalendar();

        // Load hours of current month
        appointmentViewmodel.loadAvailableHours(currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.YEAR),
                UserHolder.patient().getDoctorId());

        // Send appointment request
        binding.saveButton.setOnClickListener((v) ->
        {
            if (binding.messageInput.getText().toString().isEmpty() ||
                binding.hourBtn.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Πρέπει να συμπληρωθούν όλα τα πεδία...",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Needed for showing toast
            FragmentActivity context = getActivity();
            Callback callback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                    context.runOnUiThread(() ->
                            Toast.makeText(getActivity(), "Υπήρξε ένα σφάλμα δικτύου, δοκιμάστε ξανά σε λίγο!",
                            Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    context.runOnUiThread(() ->
                    {
                        String res = "";
                        try { res = response.body().string(); }
                        catch (IOException e) { }

                        if (res.contains(Error.RESOURCE_EXISTS))
                        {
                            Toast.makeText(getActivity(), "Έχετε κάνει ήδη αίτημα για ραντεβού! Μπορείτε να ξανακάνετε αίτημα μόλις περάσει η ημερομήνια του ραντεβού.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(getActivity(), "Το αίτημα για ραντεβού ολοκληρώθηκε επιτυχώς!",
                                Toast.LENGTH_SHORT).show();
                    });

                    // Reload everything
                    appointmentViewmodel.loadAvailableHours(selectedDate.get(Calendar.YEAR),
                            selectedDate.get(Calendar.MONTH) + 1,
                            UserHolder.patient().getDoctorId());
                }
            };

            HashMap<String, String> keyValues = new HashMap<>();
            keyValues.put("patient_id", UserHolder.patient().getId() + "");
            keyValues.put("doctor_id", UserHolder.patient().getDoctorId() + "");
            keyValues.put("date", DateFormatter.fixDatePrefixes(selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH) + 1, selectedDate.get(Calendar.DAY_OF_MONTH)));
            keyValues.put("hour", (String) binding.hourBtn.getText().subSequence(0,2));
            keyValues.put("patient_name", UserHolder.patient().getName());
            keyValues.put("patient_surname", UserHolder.patient().getSurname());
            keyValues.put("patient_number", UserHolder.patient().getPhoneNumber());
            keyValues.put("message", binding.messageInput.getText().toString());

            RequestFacade.postRequest(API.REQUEST_APPOINTMENT, keyValues, callback);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void initializeCalendar()
    {
        binding.dateText.setText(DateFormatter.formatToAlphanumeric(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH)));

        binding.calendarView.setTitleFormatter(monthFormatter);
        binding.calendarView.setWeekDayFormatter(weekDayFormatter);

        binding.calendarView.setOnDateChangedListener((widget, date, selected) ->
        {
            // Clear selection date text
            binding.hourBtn.setText("");

            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDay();

            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month - 1);
            selectedDate.set(Calendar.DAY_OF_MONTH, day);

            adapter.setHours(appointmentViewmodel.getAvailableHoursOfDate(year, month, day));
            binding.dateText.setText(DateFormatter.formatToAlphanumeric(year, month, day));
        });

        // Set minimum date to the current date
        binding.calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(currentDate.get(Calendar.YEAR),
                        currentDate.get(Calendar.MONTH) + 1,
                        currentDate.get(Calendar.DAY_OF_MONTH)))
                .commit();

        // Select the date
        binding.calendarView.setSelectedDate(CalendarDay.from(currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH) + 1,
                currentDate.get(Calendar.DAY_OF_MONTH)));

        binding.calendarView.setAllowClickDaysOutsideCurrentMonth(false);

        binding.calendarView.setOnMonthChangedListener((widget, date) ->
        {
            // Clear selection date text
            binding.hourBtn.setText("");
            binding.calendarView.clearSelection();

            appointmentViewmodel.loadAvailableHours(date.getMonth(),
                    date.getYear(),
                    UserHolder.patient().getDoctorId());
        });
    }
}