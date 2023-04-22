package com.mobile.physiolink.service.api;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Kote Kostandin (setokk) <br>
 * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
 * <br>
 *
 * This class is used as a simple API endpoints holder.
 * <br> <br>
 * Wherever there is a "/" at the end of an endpoint, it indicates the need
 * for one or more parameters at the end of the request URL.
 * <br> <br>
 * List of REST API methods for each constant:
 * <ul>
 *     <li>{@link API#AUTH_USER}: POST request (username, password)</li>
 *     <li>{@link API#CREATE_DOCTOR}: POST request (username, password, name, surname, email, phoneNumber, afm)</li>
 * </ul>
 */
public final class API
{
    public static final String IP = "192.168.1.8";
    public static final String URL = "http://" + IP  + ":3000/physiolink/api";

    /* User auth related API endpoint */
    public static final String AUTH_USER = API.URL + "/auth/log-in";

    /* Provision related API endpoints */
    public static final String CREATE_PROVISION = API.URL + "/provision/create";
    public static final String EDIT_PROVISION = API.URL + "/provision/edit/";
    public static final String GET_PROVISIONS = API.URL + "/provisions/get";

    /* Doctor related API endpoints */
    public static final String CREATE_DOCTOR = API.URL + "/doctor/create";
    public static final String EDIT_DOCTOR = API.URL + "/doctor/edit/";
    public static final String GET_DOCTOR = API.URL + "/doctor/get/";
    public static final String GET_DOCTORS = API.URL + "/doctors/get";

    /* Patient related API endpoints */
    public static final String CREATE_PATIENT = API.URL + "/patient/create";
    public static final String EDIT_PATIENT = API.URL + "/patient/edit/";
    public static final String GET_PATIENT = API.URL + "/patient/get/";
    public static final String GET_PATIENTS = API.URL + "/patients/get";
}
