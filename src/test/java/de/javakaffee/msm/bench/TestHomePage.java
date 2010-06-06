package de.javakaffee.msm.bench;

import org.apache.wicket.util.tester.WicketTester;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
    private WicketTester tester;

    @BeforeClass
    public void setUp() {
        tester = new WicketTester( new WicketApplication() );
    }

    @Test
    public void testRenderMyPage() {
        //start and render the test page
        tester.startPage( HomePage.class );

        //assert rendered page class
        tester.assertRenderedPage( HomePage.class );

        //assert rendered label component
        tester.assertLabel( "border:message", HomePage.MSG );
    }
}
