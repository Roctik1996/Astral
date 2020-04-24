package com.goroscop.astral.utils;

import java.text.ChoiceFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static String getSign(String date) {

        Date date1 = new Date(Long.parseLong(date)*1000);
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(date1);

        int month=birthday.get(Calendar.MONTH)+1;
        int day=birthday.get(Calendar.DAY_OF_MONTH);


        String sign = "";

        if (month == 12) {

            if (day < 22)
                sign = "Стрелец";
            else
                sign = "Козерог";
        } else if (month == 1) {
            if (day < 20)
                sign = "Козерог";
            else
                sign = "Водолей";
        } else if (month == 2) {
            if (day < 19)
                sign = "Водолей";
            else
                sign = "Рыбы";
        } else if (month == 3) {
            if (day < 21)
                sign = "Рыбы";
            else
                sign = "Овен";
        } else if (month == 4) {
            if (day < 20)
                sign = "Овен";
            else
                sign = "Телец";
        } else if (month == 5) {
            if (day < 21)
                sign = "Телец";
            else
                sign = "Близнецы";
        } else if (month == 6) {
            if (day < 21)
                sign = "Близнецы";
            else
                sign = "Рак";
        } else if (month == 7) {
            if (day < 23)
                sign = "Рак";
            else
                sign = "Лев";
        } else if (month == 8) {
            if (day < 23)
                sign = "Лев";
            else
                sign = "Дева";
        } else if (month == 9) {
            if (day < 23)
                sign = "Дева";
            else
                sign = "Весы";
        } else if (month == 10) {
            if (day < 23)
                sign = "Весы";
            else
                sign = "Скорпион";
        } else if (month == 11) {
            if (day < 22)
                sign = "Скорпион";
            else
                sign = "Стрелец";
        }

        return sign;
    }

    public static String getChinaSign(String date){

        Date date1 = new Date(Long.parseLong(date)*1000);
        Calendar birthday = Calendar.getInstance();
        birthday.setTime(date1);

        int year =birthday.get(Calendar.YEAR);

        int remainder = year % 12;
        String animal="";
        // determine which animal it is
        switch (remainder)
        {
            case (0): animal = "Обезьяна";
                break;
            case (1): animal = "Петух";
                break;
            case (2): animal = "Собака";
                break;
            case (3): animal = "Свинья";
                break;
            case (4): animal = "Крыса";
                break;
            case (5): animal = "Бык";
                break;
            case (6): animal = "Тигр";
                break;
            case (7): animal = "Кролик";
                break;
            case (8): animal = "Дракон";
                break;
            case (9): animal = "Змея";
                break;
            case (10): animal = "Лошадь";
                break;
            case (11): animal = "Коза";
                break;
        }
       return animal;
    }

    public static String getAge(String date){
        Date date1 = new Date(Long.parseLong(date)*1000);
        Calendar birthday = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        birthday.setTime(date1);

        int age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        double[] limits = {0, 1, 2, 5};
        String[] strings = {"лет", "год", "года", "лет"};
        ChoiceFormat format = new ChoiceFormat(limits, strings);
        int rule = 11 <= (age % 100) && (age % 100) <= 14 ? age : age % 10;
        return age+ " "+format.format(rule);
    }



}
