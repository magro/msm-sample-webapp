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

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;

public class MsmBenchSession extends WebSession {

    private static final long serialVersionUID = 1L;
    private AtomicInteger _counter;

    public MsmBenchSession( final Request request ) {
        super( request );
    }

    public static MsmBenchSession get() {
        return (MsmBenchSession) Session.get();
    }

    /**
     * @param i
     * @return
     */
    public AtomicInteger getOrCreateCounter( final int i ) {
        if ( _counter == null ) {
            _counter = new AtomicInteger( i );
        }
        return _counter;
    }

}