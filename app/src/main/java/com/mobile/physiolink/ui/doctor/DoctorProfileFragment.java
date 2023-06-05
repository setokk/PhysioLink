package com.mobile.physiolink.ui.doctor;

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
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.mobile.physiolink.R;
import com.mobile.physiolink.databinding.FragmentDoctorProfileBinding;
import com.mobile.physiolink.model.user.singleton.UserHolder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.util.image.ImageUploader;
import com.mobile.physiolink.util.image.ProfileImageProvider;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorProfileFragment extends Fragment {
    private FragmentDoctorProfileBinding binding;
    ImageView editImg;
    CircleImageView photoProfile;
    public DoctorProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        String path = ImageUploader.getAbsolutePathFromUri(uri);
        if (path.isEmpty())
            return;
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

                    UserHolder.psf().setImageURL(API.URL + "/images/get/" + UserHolder.psf().getId());
                    ProfileImageProvider.setImageForUser(binding.profileImageDoctor,
                            UserHolder.psf());
                });
            }
        });
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
                NavController navController = Navigation.findNavController(getActivity(), R.id.container);
                navController.navigate(R.id.action_doctorProfileFragment_to_doctorHomeFragment);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentDoctorProfileBinding.inflate(inflater, container, false);

        ProfileImageProvider.setImageForUser(binding.profileImageDoctor,
                UserHolder.doctor());
        binding.profileNameDoctor.setText(String.format("%s %s",
                UserHolder.doctor().getName(), UserHolder.doctor().getSurname()));
        binding.profileUsernameDoctor.setText(String.format("%s ",
                UserHolder.doctor().getUsername()));
        binding.amkaOutputProfileDoctor.setText(String.format("%s ",
                UserHolder.doctor().getAfm()));
        binding.emailOutputDoctorProfile.setText(String.format("%s ",
                UserHolder.doctor().getEmail()));
        binding.physioNameDoctorProfile.setText(String.format("%s ",
                UserHolder.doctor().getPhysioName()));
        binding.cityOutputDoctorProfile.setText(String.format("%s ",
                UserHolder.doctor().getCity()));
        binding.adressOutputDoctorProfile.setText(String.format("%s ",
                UserHolder.doctor().getAddress()));
        binding.phoneOutputDoctorProfile.setText(String.format("%s ",
                UserHolder.doctor().getPhoneNumber()));
        binding.postalCodeDoctorProfile.setText(String.format("%s ",
                UserHolder.doctor().getPostalCode()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editImg = binding.editImgDoctorProfile;
        photoProfile = binding.profileImageDoctor;

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(DoctorProfileFragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080,1080)
                        .start();
            }
        });
    }
}
