package com.ken.bartfare;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * Created by KEN on 4/21/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private TextView fareTextTest;
    private MainActivity apptest;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        apptest = getActivity();
        fareTextTest =
                (TextView) apptest
                        .findViewById(R.id.fare_value);
    }

    public void testPreconditions() {
        assertNotNull("Apptest is null", apptest);
        assertNotNull( "faretexttest is null" , fareTextTest);

    }

    public void testTextView(){
        final String expected = apptest.getString(R.string.choose_text);
        final String actual = fareTextTest.getText().toString();
        assertEquals(expected,actual);
    }
}
