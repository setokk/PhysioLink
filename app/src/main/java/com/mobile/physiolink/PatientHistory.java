package com.mobile.physiolink;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String history_date[], history_time[], history_fysio[],history_doc[],history_service[], history_price[];

    private RecyclerView recycler_history;
    private HistoryRecyclerAdapter adapter;
    public PatientHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientHistory newInstance(String param1, String param2) {
        PatientHistory fragment = new PatientHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        history_date = getResources().getStringArray(R.array.servicesPatientHistoryListExampleDate);
        history_time = getResources().getStringArray(R.array.requestListExampleTime);
        history_fysio = getResources().getStringArray(R.array.doctorOfficeListExample);
        history_doc = getResources().getStringArray(R.array.doctoNameListExample);
        history_service = getResources().getStringArray(R.array.paroxesNameExample);
        history_price =  getResources().getStringArray(R.array.paroxesCostExamle);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        //adapt recycler view to fragment
        View view = inflater.inflate(R.layout.fragment_patient_history,container,false);
        recycler_history = view.findViewById(R.id.history_recyclerview);
        recycler_history.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new HistoryRecyclerAdapter(history_date,history_time,history_fysio,history_doc,history_service,history_price);
        recycler_history.setAdapter(adapter);
        return view;

    }
}