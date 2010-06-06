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

import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * TODO
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
public class NaviPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public NaviPanel( final String id, final List<? extends Class<? extends Page>> bookmarkablePages ) {
        super( id );

        final RepeatingView repeatingView = new RepeatingView( "repeater" );
        add( repeatingView );

        for ( int i = 0; i < bookmarkablePages.size(); i++ ) {
            final Class<? extends Page> clazz = bookmarkablePages.get( i );
            final BookmarkablePageLink<String> link = new BookmarkablePageLink<String>( String.valueOf( i ), clazz );
            link.add( new Label( "linkLabel", clazz.getSimpleName() ) );

            //            if ( getPage().getClass() == clazz ) {
            //                link.setEnabled( false );
            //            }

            repeatingView.add( link );
        }

    }

}