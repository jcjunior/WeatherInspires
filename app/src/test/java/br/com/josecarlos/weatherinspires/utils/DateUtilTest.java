package br.com.josecarlos.weatherinspires.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by jcjunior on 17/07/2016.
 */
public class DateUtilTest {

    @Test
    public void formatDateLongToStringSuccess(){

        String dateFormatted = DateUtil.formatDateLongToString(1468772593000L);

        assertEquals("Jul 17, 2016", dateFormatted);

    }

    @Test
    public void formatDateLongToStringWithNullValue(){

        String dateFormatted = DateUtil.formatDateLongToString(null);

        assertNull(dateFormatted);

    }

    @Test
    public void getWeekDayOfADateSuccess(){

        String weekDay = DateUtil.getWeekDayOfADate("2016-07-17 14:49:00","yyyy-MM-dd HH:mm:ss");

        assertEquals("Sunday", weekDay);
    }

    @Test
    public void getWeekDayOfADateWithNullValues(){

        String weekDay = DateUtil.getWeekDayOfADate(null,null);

        assertNull(weekDay);
    }

    @Test
    public void getWeekDayOfADateWithEmptyValues(){

        String weekDay = DateUtil.getWeekDayOfADate("","");

        assertNull(weekDay);
    }


    @Test
    public void getDayOfDateSuccess(){

        Integer day = DateUtil.getDayOfDate("2016-07-17 14:49:00","yyyy-MM-dd HH:mm:ss");

        assertEquals(new Integer(17), day);

    }

    @Test
    public void getDayOfDateWithNullValue(){

        Integer day = DateUtil.getDayOfDate(null , null);

        assertNull(day);

    }

    @Test
    public void getDayOfDateWithEmptyValue(){

        Integer day = DateUtil.getDayOfDate("" , "");

        assertNull(day);

    }

}
