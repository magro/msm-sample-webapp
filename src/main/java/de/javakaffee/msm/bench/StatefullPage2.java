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

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.annotation.strategy.MountHybrid;

import de.javakaffee.web.msm.SessionLock;

/**
 * Homepage.
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
@MountPath( path = "statefull2" )
@MountHybrid
public class StatefullPage2 extends BasePage {

    static final String MSG = "If you see this message wicket is properly configured and running";

    private static final Logger LOG = LoggerFactory.getLogger( StatefullPage2.class );

    private static final long serialVersionUID = 1L;

    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public StatefullPage2( final PageParameters parameters ) {

        setStatelessHint( false );

        final AtomicInteger counter = new AtomicInteger( 0 );
        final Foo foo = new Foo();

        // Add the simplest type of label
        add( new Label( "message", "A simple statefull page, just click and watch the counter incrementing." ) );

        final Link<String> link = new Link<String>( "link", new Model<String>( "hello" ) ) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                LOG.info( "Link clicked: " + counter.incrementAndGet() + " " + foo );

                final HttpSession httpSession = ( (WebRequest) getRequest() ).getHttpServletRequest().getSession();
                SessionLock.lock( httpSession.getId() );
                try {
                    httpSession.setAttribute( "foo", "bar" );
                } finally {
                    SessionLock.unlock( httpSession.getId() );
                }

            }

            @Override
            protected boolean getStatelessHint() {
                return true;
            }

        };

        link.add( new Label( "linkLabel", new Model<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public String getObject() {
                return "hit me! (already clicked " + counter.get() + " times) (i'm " + StatefullPage2.this + ")";
            }

        } ) );

        add( link );

    }

    class Foo implements Serializable {

    }
}
