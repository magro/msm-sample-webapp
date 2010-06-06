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

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.SecondLevelCacheSessionStore.IPageStore;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
public class WicketApplication extends WebApplication {

    @SuppressWarnings( "unused" )
    private static final Logger LOG = LoggerFactory.getLogger( WicketApplication.class );

    /**
     * Constructor
     */
    public WicketApplication() {
        new AnnotatedMountScanner().scanPackage( getClass().getPackage().getName() ).mount( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
        super.init();
        getRequestLoggerSettings().setRequestLoggerEnabled( true );
        // getRequestCycleSettings().setBufferResponse( false );
        getRequestCycleSettings().setRenderStrategy( IRequestCycleSettings.REDIRECT_TO_RENDER );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Session newSession( final Request request, final Response response ) {
        return new MsmBenchSession( request );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ISessionStore newSessionStore() {
        LOG.info( "Creating new HttpSessionStore..." );

        return new HttpSessionStore( this ); // new SecondLevelCacheSessionStore( this, new DummyPageStore() );
        //return super.newSessionStore();
    }

    /**
     * Does not store pages.
     * 
     * @author grotzke (initial creation)
     */
    static class DummyPageStore implements IPageStore /* IClusteredPageStore */{

        /**
         * {@inheritDoc}
         */
        public boolean containsPage( final String sessionId, final String pageMapName, final int pageId, final int pageVersion ) {
            // TODO Auto-generated method stub
            return false;
        }

        /**
         * {@inheritDoc}
         */
        public void destroy() {
            // TODO Auto-generated method stub

        }

        /**
         * {@inheritDoc}
         */
        public <T> Page getPage( final String sessionId, final String pagemap, final int id, final int versionNumber,
                final int ajaxVersionNumber ) {
            // TODO Auto-generated method stub
            return null;
        }

        /**
         * {@inheritDoc}
         */
        public void pageAccessed( final String sessionId, final Page page ) {
            // TODO Auto-generated method stub

        }

        /**
         * {@inheritDoc}
         */
        public void removePage( final String sessionId, final String pagemap, final int id ) {
            // TODO Auto-generated method stub

        }

        /**
         * {@inheritDoc}
         */
        public void storePage( final String sessionId, final Page page ) {
            // TODO Auto-generated method stub

        }

        /**
         * {@inheritDoc}
         */
        public void unbind( final String sessionId ) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

}
