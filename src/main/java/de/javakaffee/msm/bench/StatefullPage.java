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

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.annotation.strategy.MountHybrid;

/**
 * Homepage.
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
@MountPath( path = "statefull" )
@MountHybrid
public class StatefullPage extends BasePage {

    static final String MSG = "If you see this message wicket is properly configured and running";

    private static final Logger LOG = LoggerFactory.getLogger( StatefullPage.class );

    private static final long serialVersionUID = 1L;
    private final AtomicInteger _counter;

    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public StatefullPage( final PageParameters parameters ) {

        setStatelessHint( false );

        // Add the simplest type of label
        add( new Label( "message", "A simple statefull page, just click and watch the counter incrementing." ) );

        _counter = new AtomicInteger( 0 );

        final Link<String> link = new Link<String>( "link", new Model<String>( "hello" ) ) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                LOG.info( "Link clicked: " + _counter.incrementAndGet() );
            }

        };

        link.add( new Label( "linkLabel", new Model<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return "hit me! (already clicked " + _counter.get() + " times) (i'm " + StatefullPage.this + ")";
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
