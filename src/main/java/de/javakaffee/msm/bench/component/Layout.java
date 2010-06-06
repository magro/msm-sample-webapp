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
package de.javakaffee.msm.bench.component;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.border.Border;

import de.javakaffee.msm.bench.HomePage;
import de.javakaffee.msm.bench.JodaTimePage;
import de.javakaffee.msm.bench.ListLightPage;
import de.javakaffee.msm.bench.SessionPage;
import de.javakaffee.msm.bench.StatefullPage;
import de.javakaffee.msm.bench.StatefullPage2;
import de.javakaffee.msm.bench.StatelessPage;

/**
 * TODO
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
public class Layout extends Border {

    private static final long serialVersionUID = -4653584176197125544L;

    public Layout( final String id ) {
        super( id );

        @SuppressWarnings( "unchecked" )
        final List<Class<? extends Page>> pages =
                Arrays.<Class<? extends Page>> asList( HomePage.class, StatelessPage.class, SessionPage.class, StatefullPage.class,
                        JodaTimePage.class, StatefullPage2.class, ListLightPage.class );
        add( new NaviPanel( "header", pages ) );
        add( new MessagePanel( "footer", "This is the footer text" ) );
        add( new MessagePanel( "teaser", "teaser" ) );

    }
}
