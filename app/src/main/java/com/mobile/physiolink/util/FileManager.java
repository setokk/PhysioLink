package com.mobile.physiolink.util;

import android.content.Context;

import com.mobile.physiolink.model.user.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager
{
    public static Context context;
    public static String pathToAppFolder;

    public static void setContext(Context context)
    {
        FileManager.context = context;
        pathToAppFolder = context.getExternalFilesDir(null).getAbsolutePath();
    }

    public static void writeUserObj(User user, String name) throws IOException
    {
        String filePath = pathToAppFolder + File.separator + name;

        ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)));
        outputStream.writeObject(user);
        outputStream.close();
    }

    public static User readUserObj(String name) throws IOException, ClassNotFoundException
    {
        String filePath = pathToAppFolder + File.separator + name;

        ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)));
        User user = (User) inputStream.readObject();
        inputStream.close();

        return user;
    }

    public static boolean exists(String name)
    {
        String filePath = pathToAppFolder + File.separator + name;
        File userObj = new File(filePath);

        return userObj.exists();
    }
}
