package com.example.demo.constants;

public final class ValidationConstraints {

    public static final int FIRSTNAME_MIN_LENGTH = 2;
    public static final int FIRSTNAME_MAX_LENGTH = 20;

    public static final int LASTNAME_MIN_LENGTH = 2;
    public static final int LASTNAME_MAX_LENGTH = 20;

    public static final String USER_EMAIl_REGEX = "^(.+)@(.+)$";
    public static final int USER_EMAIL_MIN_LENGTH = 10;
    public static final int USER_EMAIL_MAX_LENGTH = 50;

    public static final String PHONE_REGEX = "08[789]\\d{7}";
    public static final int PHONE_MIN_LENGTH = 10;
    public static final int PHONE_MAX_LENGTH = 10;

}
