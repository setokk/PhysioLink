package com.mobile.physiolink.ui.patient;

import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentRequestAppointmentBinding;
import com.mobile.physiolink.databinding.ItemListTimeBinding;
import com.mobile.physiolink.model.availability.AvailableHoursManager;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.ui.patient.adapter.AdapterForAppointmentHour;
import com.mobile.physiolink.ui.patient.viewmodel.RequestAppointmentViewModel;
import com.mobile.physiolink.util.date.DateFormatter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.chrono.ChronoLocalDate;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RequestAppointmentFragment extends Fragment
{
    private RequestAppointmentViewModel appointmentViewmodel;
    private AdapterForAppointmentHour adapter;

    private FragmentRequestAppointmentBinding binding;

    private ArrayWeekDayFormatter weekDayFormatter;
    private MonthArrayTitleFormatter monthFormatter;

    private LocalDate selectedDate;

    private boolean reopened = false;

    public RequestAppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentRequestAppointmentBinding.inflate(inflater, container, false);

        selectedDate = LocalDate.now();

        appointmentViewmodel = new ViewModelProvider(this).get(RequestAppointmentViewModel.class);
        appointmentViewmodel.getAvailableHours().observe(getViewLifecycleOwner(), hours ->
        {
            if (!reopened)
            {
                adapter.setHours(hours.getAvailableHoursOfDate(
                        selectedDate.getYear(), selectedDate.getMonthValue(),
                        selectedDate.getDayOfMonth()));
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
        appointmentViewmodel.loadAvailableHours(selectedDate.getMonthValue(),
                selectedDate.getYear(),
                UserHolder.patient().getDoctorId());

        // Send appointment request
        binding.saveButton.setOnClickListener((v) ->
        {
            if (binding.hourBtn.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Πρέπει να επιλεχθεί ώρα...",
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
                        catch (IOException e) {
                            Toast.makeText(getActivity(), "Προέκυψε σφάλμα. Δοκιμάστε ξανά αργότερα",
                                    Toast.LENGTH_LONG).show();
                        }

                        if (res.contains(Error.RESOURCE_EXISTS))
                        {
                            Toast.makeText(getActivity(), "Έχετε κάνει ήδη αίτημα για ραντεβού! Μπορείτε να ξανακάνετε αίτημα μόλις περάσει η ημερομήνια του ραντεβού.",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(getActivity(), "Το αίτημα για ραντεβού ολοκληρώθηκε επιτυχώς!",
                                Toast.LENGTH_SHORT).show();

                        binding.calendarView.clearSelection();
                        binding.hourBtn.setText("");

                        // Reload everything
                        appointmentViewmodel.loadAvailableHours(selectedDate.getMonthValue(),
                                selectedDate.getYear(),
                                UserHolder.patient().getDoctorId());
                    });
                }
            };

            HashMap<String, String> keyValues = new HashMap<>();
            keyValues.put("patient_id", UserHolder.patient().getId() + "");
            keyValues.put("doctor_id", UserHolder.patient().getDoctorId() + "");
            keyValues.put("date", DateFormatter.fixDatePrefixes(selectedDate.getYear(),
                    selectedDate.getMonthValue(),
                    selectedDate.getDayOfMonth()));
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
        binding.dateText.setText(DateFormatter.formatToAlphanumeric(selectedDate.getYear(),
                selectedDate.getMonthValue(),
                selectedDate.getDayOfMonth()));

        binding.calendarView.setTitleFormatter(monthFormatter);
        binding.calendarView.setWeekDayFormatter(weekDayFormatter);

        binding.calendarView.setOnDateChangedListener((widget, date, selected) ->
        {
            // Clear selection date text
            binding.hourBtn.setText("");

            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDay();
            selectedDate = LocalDate.of(year, month, day);

            adapter.setHours(appointmentViewmodel.getAvailableHoursOfDate(year, month, day));
            binding.dateText.setText(DateFormatter.formatToAlphanumeric(year, month, day));
        });

        // Set minimum date to the current date
        binding.calendarView.state().edit()
                .setMinimumDate(CalendarDay.from(selectedDate.getYear(),
                        selectedDate.getMonthValue(),
                        selectedDate.getDayOfMonth()))
                .commit();

        // Select the date
        binding.calendarView.setSelectedDate(CalendarDay.from(selectedDate.getYear(),
                selectedDate.getMonthValue(),
                selectedDate.getDayOfMonth()));

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