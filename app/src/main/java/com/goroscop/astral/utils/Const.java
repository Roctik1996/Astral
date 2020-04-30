package com.goroscop.astral.utils;

import com.goroscop.astral.R;

import java.util.LinkedHashMap;
import java.util.Map;

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
    public static final String APP_PREFERENCES_IS_FIRST = "is_first";
    public static final String APP_PREFERENCES_PLANET = "planet";
    public static final String APP_PREFERENCES_PRO = "pro";
    public static final String APP_PREFERENCES_IS_HOROSCOPE = "horoscope";

    public static final String APP_PREFERENCES_TODAY = "today";
    public static final String APP_PREFERENCES_TODAY_SUCCESS = "success_today";
    public static final String APP_PREFERENCES_CHINA = "china";
    public static final String APP_PREFERENCES_SUCCESS_CHINA = "success_china";
    public static final String APP_PREFERENCES_TOMORROW = "tomorrow";
    public static final String APP_PREFERENCES_WEEK = "week";
    public static final String APP_PREFERENCES_MONTH = "month";
    public static final String APP_PREFERENCES_YEAR = "year";
    public static final String APP_PREFERENCES_LUCK_NUMBER = "luck_number";
    public static final String APP_PREFERENCES_LOVE = "love";
    public static final String APP_PREFERENCES_HEALTH = "health";
    public static final String APP_PREFERENCES_CAREER = "career";


    public static final String[] tabTitle = new String[]{"Сегодня",
            "Завтра",
            "Неделя",
            "Месяц",
            "Год",
    };


    public static final Map<String, Integer> avatarIcon = new LinkedHashMap<>();

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

    public static final Map<String, Integer> miniIcon = new LinkedHashMap<>();

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

    public static final Map<String, Integer> avatarChinaIcon = new LinkedHashMap<>();

    static {
        avatarChinaIcon.put("Обезьяна", R.drawable.ic_monkey);
        avatarChinaIcon.put("Петух", R.drawable.ic_rooster);
        avatarChinaIcon.put("Собака", R.drawable.ic_dog);
        avatarChinaIcon.put("Свинья", R.drawable.ic_pig);
        avatarChinaIcon.put("Крыса", R.drawable.ic_rat);
        avatarChinaIcon.put("Бык", R.drawable.ic_ox);
        avatarChinaIcon.put("Тигр", R.drawable.ic_tiger);
        avatarChinaIcon.put("Кролик", R.drawable.ic_rabbit);
        avatarChinaIcon.put("Дракон", R.drawable.ic_dragon);
        avatarChinaIcon.put("Змея", R.drawable.ic_snake);
        avatarChinaIcon.put("Лошадь", R.drawable.ic_horse);
        avatarChinaIcon.put("Коза", R.drawable.ic_sheep);
    }

    public static final Map<String, Integer> chinaAll = new LinkedHashMap<>();

    static {
        chinaAll.put("Обезьяна", R.string.monkey);
        chinaAll.put("Петух", R.string.rooster);
        chinaAll.put("Собака", R.string.dog);
        chinaAll.put("Свинья", R.string.pig);
        chinaAll.put("Крыса", R.string.rat);
        chinaAll.put("Бык", R.string.ox);
        chinaAll.put("Тигр", R.string.tiger);
        chinaAll.put("Кролик", R.string.rabbit);
        chinaAll.put("Дракон", R.string.dragon);
        chinaAll.put("Змея", R.string.snake);
        chinaAll.put("Лошадь", R.string.horse);
        chinaAll.put("Коза", R.string.sheep);
    }

    public static final Map<String, Integer> miniChinaIcon = new LinkedHashMap<>();

    static {
        miniChinaIcon.put("Обезьяна", R.drawable.ic_monkey_mini);
        miniChinaIcon.put("Петух", R.drawable.ic_rooster_mini);
        miniChinaIcon.put("Собака", R.drawable.ic_dog_mini);
        miniChinaIcon.put("Свинья", R.drawable.ic_pig_mini);
        miniChinaIcon.put("Крыса", R.drawable.ic_rat_mini);
        miniChinaIcon.put("Бык", R.drawable.ic_ox_mini);
        miniChinaIcon.put("Тигр", R.drawable.ic_tiger_mini);
        miniChinaIcon.put("Кролик", R.drawable.ic_rabbit_mini);
        miniChinaIcon.put("Дракон", R.drawable.ic_dragon_mini);
        miniChinaIcon.put("Змея", R.drawable.ic_snake_mini);
        miniChinaIcon.put("Лошадь", R.drawable.ic_horse_mini);
        miniChinaIcon.put("Коза", R.drawable.ic_sheep_mini);
    }


    public static final Map<String, String> datesSign = new LinkedHashMap<>();

    static {
        datesSign.put("Овен", "21.03 по 20.04");
        datesSign.put("Телец", "21.04 по 21.05");
        datesSign.put("Близнецы", "21.05 по 21.06");
        datesSign.put("Рак", "22.06 по 22.07");
        datesSign.put("Лев", "23.07 по 23.08");
        datesSign.put("Дева", "24.08 по 23.09");
        datesSign.put("Весы", "24.09 по 23.10");
        datesSign.put("Скорпион", "24.10 по 22.11");
        datesSign.put("Стрелец", "23.11 по 21.12");
        datesSign.put("Козерог", "22.12 по 20.01");
        datesSign.put("Водолей", "21.01 по 19.02");
        datesSign.put("Рыбы", "20.02 по 20.03");
    }

    public static final Map<String, String> planetUrls = new LinkedHashMap<>();

    static {
        planetUrls.put("Овен", "http://ru.astrologyk.com/zodiac/planets/aries");
        planetUrls.put("Телец", "http://ru.astrologyk.com/zodiac/planets/taurus");
        planetUrls.put("Близнецы", "http://ru.astrologyk.com/zodiac/planets/gemini");
        planetUrls.put("Рак", "http://ru.astrologyk.com/zodiac/planets/cancer");
        planetUrls.put("Лев", "http://ru.astrologyk.com/zodiac/planets/leo");
        planetUrls.put("Дева", "http://ru.astrologyk.com/zodiac/planets/virgo");
        planetUrls.put("Весы", "http://ru.astrologyk.com/zodiac/planets/libra");
        planetUrls.put("Скорпион", "http://ru.astrologyk.com/zodiac/planets/scorpio");
        planetUrls.put("Стрелец", "http://ru.astrologyk.com/zodiac/planets/sagittarius");
        planetUrls.put("Козерог", "http://ru.astrologyk.com/zodiac/planets/capricorn");
        planetUrls.put("Водолей", "http://ru.astrologyk.com/zodiac/planets/aquarius");
        planetUrls.put("Рыбы", "http://ru.astrologyk.com/zodiac/planets/pisces");
    }

    public static final Map<String, Integer> elements = new LinkedHashMap<>();

    static {
        elements.put("Овен", R.string.aries);
        elements.put("Телец", R.string.taurus);
        elements.put("Близнецы", R.string.gemini);
        elements.put("Рак", R.string.cancer);
        elements.put("Лев", R.string.leo);
        elements.put("Дева", R.string.virgo);
        elements.put("Весы", R.string.libra);
        elements.put("Скорпион", R.string.scorpion);
        elements.put("Стрелец", R.string.sagittarius);
        elements.put("Козерог", R.string.capricorn);
        elements.put("Водолей", R.string.aquarius);
        elements.put("Рыбы", R.string.pisces);
    }

    public static final Map<String, Integer> elementsCompatibility = new LinkedHashMap<>();

    static {
        elementsCompatibility.put("Овен", R.string.fire);
        elementsCompatibility.put("Телец", R.string.earth);
        elementsCompatibility.put("Близнецы", R.string.air);
        elementsCompatibility.put("Рак", R.string.water);
        elementsCompatibility.put("Лев", R.string.fire);
        elementsCompatibility.put("Дева", R.string.earth);
        elementsCompatibility.put("Весы", R.string.air);
        elementsCompatibility.put("Скорпион", R.string.water);
        elementsCompatibility.put("Стрелец", R.string.fire);
        elementsCompatibility.put("Козерог", R.string.earth);
        elementsCompatibility.put("Водолей", R.string.air);
        elementsCompatibility.put("Рыбы", R.string.water);
    }


    public static final Map<String, Integer> elementsIcon = new LinkedHashMap<>();

    static {
        elementsIcon.put("Овен", R.drawable.ic_fire);
        elementsIcon.put("Телец", R.drawable.ic_earth);
        elementsIcon.put("Близнецы", R.drawable.ic_air);
        elementsIcon.put("Рак", R.drawable.ic_water);
        elementsIcon.put("Лев", R.drawable.ic_fire);
        elementsIcon.put("Дева", R.drawable.ic_earth);
        elementsIcon.put("Весы", R.drawable.ic_air);
        elementsIcon.put("Скорпион", R.drawable.ic_water);
        elementsIcon.put("Стрелец", R.drawable.ic_fire);
        elementsIcon.put("Козерог", R.drawable.ic_earth);
        elementsIcon.put("Водолей", R.drawable.ic_air);
        elementsIcon.put("Рыбы", R.drawable.ic_water);
    }

    public static final Map<String, String> signEn = new LinkedHashMap<>();

    static {
        signEn.put("Овен", "aries");
        signEn.put("Телец", "taurus");
        signEn.put("Близнецы", "gemini");
        signEn.put("Рак", "cancer");
        signEn.put("Лев", "lion");
        signEn.put("Дева", "virgo");
        signEn.put("Весы", "libra");
        signEn.put("Скорпион", "scorpio");
        signEn.put("Стрелец", "sagittarius");
        signEn.put("Козерог", "capricorn");
        signEn.put("Водолей", "aquarius");
        signEn.put("Рыбы", "pisces");
    }

}
