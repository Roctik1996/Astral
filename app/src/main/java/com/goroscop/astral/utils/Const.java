package com.goroscop.astral.utils;

import com.goroscop.astral.R;

import java.util.HashMap;

public class Const {
    public static String BASE_URL = "https://7astral.ru";

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_TOKEN = "token";
    public static final String APP_PREFERENCES_NAME = "name";
    public static final String APP_PREFERENCES_BIRTHDAY = "birthday";
    public static final String APP_PREFERENCES_GENDER = "gender";
    public static final String APP_PREFERENCES_CITY = "city";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_PASS = "pass";


    private static final HashMap<String, Integer> avatarIcon = new HashMap<>();
    static {
        avatarIcon.put("Овен", R.drawable.ic_aries);
        avatarIcon.put("Телец", R.drawable.ic_taurus);
        avatarIcon.put("Близнецы", R.drawable.ic_gemini);
        avatarIcon.put("Рак", R.drawable.ic_cancer);
        avatarIcon.put("Лев", R.drawable.ic_leo);
        avatarIcon.put("Дева", R.drawable.ic_virgo);
        avatarIcon.put("Весы", R.drawable.ic_libra);
        avatarIcon.put("Скорпион", R.drawable.ic_scorpion);
        avatarIcon.put("Стрелец", R.drawable.ic_sagittarius);
        avatarIcon.put("Козерог", R.drawable.ic_capricorn);
        avatarIcon.put("Водолей", R.drawable.ic_aquarius);
        avatarIcon.put("Рыбы", R.drawable.ic_pisces);
    }

    private static final HashMap<String, Integer> miniIcon = new HashMap<>();
    static {
        miniIcon.put("Овен", R.drawable.ic_aries_mini);
        miniIcon.put("Телец", R.drawable.ic_taurus_mini);
        miniIcon.put("Близнецы", R.drawable.ic_gemini_mini);
        miniIcon.put("Рак", R.drawable.ic_cancer_mini);
        miniIcon.put("Лев", R.drawable.ic_leo_mini);
        miniIcon.put("Дева", R.drawable.ic_virgo_mini);
        miniIcon.put("Весы", R.drawable.ic_libra_mini);
        miniIcon.put("Скорпион", R.drawable.ic_scorpion_mini);
        miniIcon.put("Стрелец", R.drawable.ic_sagittarius_mini);
        miniIcon.put("Козерог", R.drawable.ic_capricorn_mini);
        miniIcon.put("Водолей", R.drawable.ic_aquarius_mini);
        miniIcon.put("Рыбы", R.drawable.ic_pisces_mini);
    }

}
