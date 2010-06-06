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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.mount.MountPath;

/**
 * Homepage.
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
@MountPath( path = "home" )
public class HomePage extends BasePage {

    static final String MSG = "This is the starting point, you can go to available pages from here.";

    private static final Logger LOG = LoggerFactory.getLogger( HomePage.class );

    private static final long serialVersionUID = 1L;

    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public HomePage( final PageParameters parameters ) {

        setStatelessHint( true );

        // Add the simplest type of label
        add( new Label( "message", MSG ) );

    }

}
