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

import javax.servlet.http.HttpSession;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Homepage.
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
@MountPath( path = "session" )
public class SessionPage extends BasePage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public SessionPage( final PageParameters parameters ) {

        setStatelessHint( true );

        // Add the simplest type of label
        final HttpSession session = getWebRequestCycle().getWebRequest().getHttpServletRequest().getSession( true );
        add( new Label( "message", "A page with an http session, your session id is " + session.getId() ) );

        final Link<String> link = new Link<String>( "link" ) {

            private static final long serialVersionUID = 1L;

            @Override
            public void onClick() {
                getSession().invalidateNow();
                setResponsePage( SessionPage.class );
            }

        };

        add( link );
    }

}
