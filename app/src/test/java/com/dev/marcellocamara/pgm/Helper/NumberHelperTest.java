package com.dev.marcellocamara.pgm.Helper;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/***
    marcellocamara@id.uff.br
            2019
***/

public class NumberHelperTest {

    private int month[] = { 1, 12 };
    private double decimal[] = { 0, 0.9, 0.99, 0.990, 0.994, 0.995, 0.999 };

    @Test
    public void GetDecimal_successReturned(){
        String result[] = new String[decimal.length];
        for ( int i = 0 ; i < decimal.length ; i++ ){
            result[i] = NumberHelper.GetDecimal(decimal[i]);
        }
        for (String res : result) {
            assertThat(res.length(), is(4));
        }
        assertThat(result[0], is("0,00"));
        assertThat(result[1], is("0,90"));
        assertThat(result[2], is("0,99"));
        assertThat(result[3], is("0,99"));
        assertThat(result[4], is("0,99"));
        assertThat(result[5], is("1,00"));
        assertThat(result[6], is("1,00"));
    }

    @Test
    public void GetMonth_successReturned(){
        String result[] = new String[month.length];
        for ( int i = 0 ; i < month.length ; i++ ){
            result[i] = NumberHelper.GetMonth(month[i]);
        }
        assertThat(result[0].length(), is(2));
        assertThat(result[1].length(), is(2));
        assertThat(result[0], is("01"));
        assertThat(result[1], is("12"));
    }

}