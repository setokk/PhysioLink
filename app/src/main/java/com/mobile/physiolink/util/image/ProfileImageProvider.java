package com.mobile.physiolink.util.image;

import android.net.Uri;
import android.widget.ImageView;

import com.mobile.physiolink.R;
import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.model.user.User;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public final class ProfileImageProvider
{
    private static final List<String> nameExceptions = Arrays.asList("Άρτεμις", "Αρτεμις");
    public static Uri userURI = null;

    public static void setImageForUser(ImageView imageView, User user, boolean shouldUseURI)
    {
        if (user.hasImage())
        {
            if (userURI != null && shouldUseURI)
            {
                imageView.setImageURI(userURI);
            }
            else
            {
                Picasso.with(imageView.getRootView().getContext())
                        .load(Uri.parse(user.getImageURL()))
                        .into(imageView);
            }
            return;
        }

        int imageResource;
        if (user.isDoctor())
            imageResource = getDoctorDefaultProfileImage(user);
        else
            imageResource = getPatientDefaultProfileImage(user);
        imageView.setImageResource(imageResource);
    }

    public static void setImageOfAppointment(ImageView imageView, Appointment appointment)
    {
        String imageURL = appointment.getImageURL();
        System.out.println(imageURL);
        if (imageURL.equals("Resource not found"))
        {
            imageView.setImageResource(R.drawable.boy);
            return;
        }

        Picasso.with(imageView.getRootView().getContext())
                .load(Uri.parse(imageURL))
                .into(imageView);
    }

    public static int getDoctorDefaultProfileImage(User user)
    {
        Doctor doc = (Doctor) user;
        String name = doc.getName();

        if ((name.charAt(name.length() - 1) == 'ς' |
                name.charAt(name.length() - 1) == 'λ')
                && !nameInExceptions(name))
            return R.drawable.prof_doctor;
        else
            return R.drawable.prof_doctoress;
    }

    public static int getPatientDefaultProfileImage(User user)
    {
        return R.drawable.boy;
    }

    private static boolean nameInExceptions(String name)
    {
        return nameExceptions.stream()
                    .anyMatch(name::equals);
    }

    public static void clearURI()
    {
        userURI = null;
    }
}
