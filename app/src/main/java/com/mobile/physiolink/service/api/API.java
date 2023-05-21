package com.mobile.physiolink.service.api;

/**
 * @author Kote Kostandin (setokk) <br>
 * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
 * <br>
 *
 * This class is used as a simple API endpoints reference.
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
    public static final String IP = "192.168.1.10";
    public static final String URL = "http://" + IP  + ":3000/physiolink/api";

    /* User auth related API endpoint */
    public static final String AUTH_USER = API.URL + "/auth/log-in";

    /* Provision related API endpoints */
    public static final String CREATE_SERVICE = API.URL + "/service/create";
    public static final String EDIT_SERVICE = API.URL + "/service/edit/";
    public static final String GET_SERVICES = API.URL + "/services/get";

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
    public static final String GET_PATIENTS_OF = API.URL + "/patients/of/";

    public static final String GET_UNAVAILABLE_HOURS = API.URL + "/unavailable-hours/get";

    public static final String GET_CONFIRMED_APPOINTMENTS = API.URL + "/appointments/confirmed";
    public static final String GET_CONFIRMED_LATEST_APPOINTMENTS = API.URL + "/appointments/confirmed/latest";
    public static final String GET_PENDING_APPOINTMENTS = API.URL + "/appointments/pending/";

    public static final String REQUEST_APPOINTMENT = API.URL + "/appointment/request";
    public static final String ACCEPT_APPOINTMENT = API.URL + "/appointment/accept";
    public static final String ACCEPT_PAYMENT = API.URL + "/appointment/payment/accept";
    public static final String DECLINE_PAYMENT = API.URL + "/appointment/decline";
}
