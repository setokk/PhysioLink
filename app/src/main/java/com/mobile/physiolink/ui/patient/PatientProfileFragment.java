package com.mobile.physiolink.ui.patient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentPatientProfileBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.util.image.ImageUploader;
import com.mobile.physiolink.util.image.ProfileImageProvider;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PatientProfileFragment extends Fragment{
    private FragmentPatientProfileBinding binding;
    ImageView editImg;
    CircleImageView photoProfile;

    public PatientProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        /* On back button pressed, Go back to home fragment */
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                NavController navController = Navigation.findNavController(getActivity(), R.id.containerPatient);
                navController.navigate(R.id.action_fragmentPatientProfile_to_fragmentPatientHome);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        String path = ImageUploader.getAbsolutePathFromUri(uri);
        ImageUploader.uploadImage(getActivity(), path, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(() ->
                {
                    Toast.makeText(getActivity(), "Η φωτογραφία προφίλ ανέβηκε επιτυχώς!"
                            ,Toast.LENGTH_SHORT).show();

                    ProfileImageProvider.setImageForUser(binding.profileImagePatient,
                            UserHolder.psf());
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentPatientProfileBinding.inflate(inflater, container, false);
        ProfileImageProvider.setImageForUser(binding.profileImagePatient,
                UserHolder.patient());
        binding.profileNamePatient.setText(String.format("%s %s",
                UserHolder.patient().getName(), UserHolder.patient().getSurname()));
        binding.profileUsernamePatient.setText(String.format("%s ",
                UserHolder.patient().getUsername()));
        binding.amkaOutputProfilePatient.setText(String.format("%s ",
                UserHolder.patient().getAmka()));
        binding.emailOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getEmail()));
        binding.cityOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getCity()));
        binding.adressOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getAddress()));
        binding.phoneOutputPatientProfile.setText(String.format("%s ",
                UserHolder.patient().getPhoneNumber()));
        binding.postalCodePatientProfile.setText(String.format("%s ",
                UserHolder.patient().getPostalCode()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editImg = binding.editImgPatientProfile;
        photoProfile = binding.profileImagePatient;

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(PatientProfileFragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .start();
            }
        });
    }
}
