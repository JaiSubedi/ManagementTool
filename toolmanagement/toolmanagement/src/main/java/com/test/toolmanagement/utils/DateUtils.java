package com.test.toolmanagement.utils;

import com.test.toolmanagement.entity.Tool;
import com.test.toolmanagement.exception.ValidationException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    public static int calculateChargeDays(LocalDate startDate, LocalDate endDate, Tool tool) {
        int chargeDays = 0;
        LocalDate date = startDate.plusDays(1);
        while (!date.isAfter(endDate)) {
            if (isChargeable(date, tool)) {
                chargeDays++;
            }
            date = date.plusDays(1);
        }
        return chargeDays;
    }


    public static boolean isChargeable(LocalDate date, Tool tool) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        boolean isWeekend = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
        boolean isHoliday = isHoliday(date);

        return (tool.isWeekdayCharge() && !isWeekend && !isHoliday) ||
                (tool.isWeekendCharge() && isWeekend) ||
                (tool.isHolidayCharge() && isHoliday);
    }

    public static boolean isHoliday(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4);
        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            independenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            independenceDay = independenceDay.plusDays(1);
        }
        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        return date.equals(independenceDay) || date.equals(laborDay);
    }

    public static String formatDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return formatter.format(date);
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseDate(String dateStr) throws ValidationException {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }
}
