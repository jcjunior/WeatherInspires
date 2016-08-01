package br.com.josecarlos.weatherinspires.utils;

import android.content.Context;
import android.content.res.Resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by jcjunior on 17/07/2016.
 */
public class UtilViewTest {

    @InjectMocks
    UtilView utilView;

    @Mock
    Context context;

    @Mock
    Resources resources;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown(){
        Mockito.reset(context);
    }

    @Test
    public void getIconByNameSuccess(){

        int id = 123;

        Mockito.when(context.getResources()).thenReturn(resources);
        Mockito.when(resources.getIdentifier(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(id);
        Mockito.when(context.getString(new Integer(id))).thenReturn("\\uF00D");

        String iconName = utilView.getWeatherIconByName("sunny");

        assertNotNull(iconName);
        assertEquals("\\uF00D", iconName);
    }

    @Test
    public void getIconByNameWithNullValue(){

        String iconName = utilView.getWeatherIconByName(null);

        assertNull(iconName);
    }

    @Test
    public void getIconByNameWithEmptyValue(){

        String iconName = utilView.getWeatherIconByName("");

        assertNull(iconName);
    }

    @Test
    public void addDegreeSuccess(){
        String degreeInUnicode = "\u00b0";
        String numberWithDegree = utilView.addDegree("12");
        assertEquals("12"+degreeInUnicode, numberWithDegree);
    }

    @Test
    public void addDegreeWithNullValue(){
        String numberWithDegree = utilView.addDegree(null);
        assertNull(numberWithDegree);
    }

    @Test
    public void addDegreeWithEmptyValue(){
        String numberWithDegree = utilView.addDegree("");
        assertNull(numberWithDegree);
    }


}
