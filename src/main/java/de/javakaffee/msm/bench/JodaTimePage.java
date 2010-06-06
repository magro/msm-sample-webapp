/*
 * Copyright 2009 Martin Grotzke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.javakaffee.msm.bench;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.annotation.strategy.MountHybrid;

/**
 * Homepage.
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
@MountPath( path = "jodatime" )
@MountHybrid
public class JodaTimePage extends BasePage {

    static final String MSG = "If you see this message wicket is properly configured and running";

    private static final Logger LOG = LoggerFactory.getLogger( JodaTimePage.class );

    private static final long serialVersionUID = 1L;
    private DateTime _dateTime;

    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public JodaTimePage( final PageParameters parameters ) {

        setStatelessHint( false );

        // Add the simplest type of label
        add( new Label( "message", "A statefull page, just click and watch the time running." ) );

        _dateTime = new DateTime();

        final Link<String> link = new Link<String>( "link", new Model<String>( "forward" ) ) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                LOG.info( "Link clicked." );
                _dateTime = _dateTime.plusDays( 1 );
            }

        };

        link.add( new Label( "linkLabel", new Model<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return "Now it's " + _dateTime.toString( DateTimeFormat.fullDate() ) + "! (i'm " + JodaTimePage.this + ")";
            }

        } ) );

        add( link );

        // TODO Add your page's components here
    }

    //    @Override
    //    protected void setHeaders( final WebResponse response ) {
    //        response.setHeader( "Pragma", "no-cache" );
    //        response.setHeader( "Expires", "-1" );
    //        response.setHeader( "Cache-Control", "private, no-cache, no-store, max-age=0, must-revalidate" );
    //    }

}
