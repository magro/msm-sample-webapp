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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.wicketstuff.annotation.mount.MountPath;

import de.javakaffee.msm.bench.component.ActionPanel;

/**
 * Homepage.
 * 
 * @author <a href="mailto:martin.grotzke@javakaffee.de">Martin Grotzke</a>
 */
@MountPath( path = "listlight" )
public class ListLightPage extends BasePage {

    private static final long serialVersionUID = 1L;

    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public ListLightPage( final PageParameters parameters ) {

        setStatelessHint( true );

        // Add the simplest type of label
        add( new Label( "message",
                "Just a list of lightweight items. They're not stored directly in the session, but only their ids." ) );

        final int page = parameters.getInt( "page", 1 ) - 1;
        final String sortProperty = parameters.getString( "orderby", "firstName" );
        final boolean ascending = parameters.getAsBoolean( "asc", true );
        final SortableContactDataProvider dp = new SortableContactDataProvider( sortProperty, ascending );

        final DataView<Contact> dataView = new DataView<Contact>( "sorting", dp ) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem( final Item<Contact> item ) {
                final Contact contact = item.getModelObject();
                item.add( new ActionPanel( "actions", item.getModel() ) );
                item.add( new Label( "contactid", String.valueOf( contact.getId() ) ) );
                item.add( new Label( "firstname", contact.getFirstName() ) );
                item.add( new Label( "lastname", contact.getLastName() ) );
                item.add( new Label( "homephone", contact.getHomePhone() ) );
                item.add( new Label( "cellphone", contact.getCellPhone() ) );

                item.add( new AttributeModifier( "class", true, new AbstractReadOnlyModel<String>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public String getObject() {
                        return ( item.getIndex() % 2 == 1 )
                            ? "even"
                            : "odd";
                    }
                } ) );
            }
        };

        dataView.setItemsPerPage( 8 );

        dataView.setCurrentPage( page );

        add( new MyOrderByBorder( "orderById", "id", dp ) );

        add( new MyOrderByBorder( "orderByFirstName", "firstName", dp ) );

        add( new MyOrderByBorder( "orderByLastName", "lastName", dp ) );

        add( dataView );

        add( new PagingNavigator( "navigator", dataView ) {
            private static final long serialVersionUID = 1L;

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.apache.wicket.markup.html.navigation.paging.PagingNavigator
             * #newPagingNavigationIncrementLink(java.lang.String,
             * org.apache.wicket.markup.html.navigation.paging.IPageable, int)
             */
            @Override
            protected AbstractLink newPagingNavigationIncrementLink( final String id, final IPageable pageable, final int increment ) {
                return new PagingNavigationIncrementLink<Void>( id, pageable, increment ) {
                    private static final long serialVersionUID = 1L;

                    /*
                     * (non-Javadoc)
                     * 
                     * @seeorg.apache.wicket.markup.html.navigation.paging.
                     * PagingNavigationIncrementLink#onClick()
                     */
                    @Override
                    public void onClick() {
                        throw new UnsupportedOperationException( "OnClick not supported by bookmarkable link" );
                    }

                    /*
                     * (non-Javadoc)
                     * 
                     * @see org.apache.wicket.markup.html.link.Link#getURL()
                     */
                    @Override
                    protected CharSequence getURL() {
                        final PageParameters params = new PageParameters();
                        final SortParam sort = dp.getSort();
                        params.add( "page", String.valueOf( getPageNumber() + 1 ) );
                        params.add( "orderby", sort.getProperty() );
                        params.add( "asc", String.valueOf( sort.isAscending() ) );

                        return urlFor( ListLightPage.class, params );
                    }

                    /*
                     * (non-Javadoc)
                     * 
                     * @see
                     * org.apache.wicket.markup.html.link.Link#getStatelessHint
                     * ()
                     */
                    @Override
                    protected boolean getStatelessHint() {
                        return true;
                    }

                };
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.apache.wicket.markup.html.navigation.paging.PagingNavigator
             * #newPagingNavigationLink(java.lang.String,
             * org.apache.wicket.markup.html.navigation.paging.IPageable, int)
             */
            @Override
            protected AbstractLink newPagingNavigationLink( final String id, final IPageable pageable, final int pageNumber ) {
                return new MyPagingNavigationLink<Void>( id, pageable, pageNumber, dp );
            }

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.apache.wicket.markup.html.navigation.paging.PagingNavigator
             * #newNavigation
             * (org.apache.wicket.markup.html.navigation.paging.IPageable,
             * org.apache
             * .wicket.markup.html.navigation.paging.IPagingLabelProvider)
             */
            @Override
            protected PagingNavigation newNavigation( final IPageable pageable, final IPagingLabelProvider labelProvider ) {
                return new PagingNavigation( "navigation", pageable, labelProvider ) {
                    private static final long serialVersionUID = 1L;

                    /*
                     * (non-Javadoc)
                     * 
                     * @seeorg.apache.wicket.markup.html.navigation.paging.
                     * PagingNavigation
                     * #newPagingNavigationLink(java.lang.String,
                     * org.apache.wicket
                     * .markup.html.navigation.paging.IPageable, int)
                     */
                    @Override
                    protected AbstractLink newPagingNavigationLink( final String id, final IPageable pageable, final int pageIndex ) {
                        return new MyPagingNavigationLink<Void>( id, pageable, pageIndex, dp );
                    }

                };
            }

        } );
    }

    static class MyPagingNavigationLink<T> extends PagingNavigationLink<T> {

        private final SortableDataProvider<?> _dataProvider;

        public MyPagingNavigationLink( final String id, final IPageable pageable, final int pageNumber,
                final SortableDataProvider<?> dataProvider ) {
            super( id, pageable, pageNumber );
            _dataProvider = dataProvider;
        }

        private static final long serialVersionUID = 1L;

        /*
         * (non-Javadoc)
         * 
         * @seeorg.apache.wicket.markup.html.navigation.paging.
         * PagingNavigationIncrementLink#onClick()
         */
        @Override
        public void onClick() {
            throw new UnsupportedOperationException( "OnClick not supported by bookmarkable link" );
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.wicket.markup.html.link.Link#getStatelessHint()
         */
        @Override
        protected boolean getStatelessHint() {
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.wicket.markup.html.link.Link#getURL()
         */
        @Override
        protected CharSequence getURL() {
            final PageParameters params = new PageParameters();
            final SortParam sort = _dataProvider.getSort();
            params.add( "page", String.valueOf( getPageNumber() + 1 ) );
            params.add( "orderby", sort.getProperty() );
            params.add( "asc", String.valueOf( sort.isAscending() ) );

            return urlFor( ListLightPage.class, params );
        }
    }

    static class MyOrderByBorder extends OrderByBorder {

        private static final long serialVersionUID = 1L;

        private final SortableDataProvider<?> _stateLocator;

        public MyOrderByBorder( final String id, final String prop, final SortableDataProvider<?> stateLocator ) {
            super( id, prop, stateLocator );
            _stateLocator = stateLocator;
        }

        @Override
        protected void onSortChanged() {
            final PageParameters params = new PageParameters();
            final SortParam sort = _stateLocator.getSort();
            params.add( "page", "1" );
            params.add( "orderby", sort.getProperty() );
            params.add( "asc", String.valueOf( sort.isAscending() ) );
            setResponsePage( ListLightPage.class, params );
        }

    }

    static class SortableContactDataProvider extends SortableDataProvider<Contact> {

        private static final long serialVersionUID = 1L;

        /**
         * constructor
         * 
         * @param property
         * @param ascending
         */
        public SortableContactDataProvider( final String property, final boolean ascending ) {
            // set default sort
            setSort( property, ascending );
        }

        protected ContactsDatabase getContactsDB() {
            return ContactsDatabase.getInstance();
        }

        /**
         * @see org.apache.wicket.markup.repeater.data.IDataProvider#iterator(int,
         *      int)
         */
        public Iterator<Contact> iterator( final int first, final int count ) {
            final SortParam sp = getSort();
            return getContactsDB().find( first, count, sp.getProperty(), sp.isAscending() ).iterator();
        }

        /**
         * @see org.apache.wicket.markup.repeater.data.IDataProvider#size()
         */
        public int size() {
            return getContactsDB().getCount();
        }

        /**
         * @see org.apache.wicket.markup.repeater.data.IDataProvider#model(java.lang.Object)
         */
        public IModel<Contact> model( final Contact object ) {
            return new DetachableContactModel( object );
        }

    }

    static class DetachableContactModel extends LoadableDetachableModel<Contact> {

        private static final long serialVersionUID = 1L;

        private final long id;

        protected ContactsDatabase getContactsDB() {
            return ContactsDatabase.getInstance();
        }

        /**
         * @param c
         */
        public DetachableContactModel( final Contact c ) {
            this( c.getId() );
        }

        /**
         * @param id
         */
        public DetachableContactModel( final long id ) {
            if ( id == 0 ) {
                throw new IllegalArgumentException();
            }
            this.id = id;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Long.valueOf( id ).hashCode();
        }

        /**
         * used for dataview with ReuseIfModelsEqualStrategy item reuse strategy
         * 
         * @see org.apache.wicket.markup.repeater.ReuseIfModelsEqualStrategy
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals( final Object obj ) {
            if ( obj == this ) {
                return true;
            } else if ( obj == null ) {
                return false;
            } else if ( obj instanceof DetachableContactModel ) {
                final DetachableContactModel other = (DetachableContactModel) obj;
                return other.id == id;
            }
            return false;
        }

        /**
         * @see org.apache.wicket.model.LoadableDetachableModel#load()
         */
        @Override
        protected Contact load() {
            // loads contact from the database
            return getContactsDB().get( id );
        }
    }

    public static class Contact {

        private long id;

        private String firstName;

        private String lastName;

        private String homePhone;

        private String cellPhone;

        /**
         * Constructor
         */
        public Contact() {

        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "[Contact id=" + id + " firstName=" + firstName + " lastName=" + lastName + " homePhone=" + homePhone
                    + " cellPhone=" + cellPhone + "]";
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals( final Object obj ) {
            if ( obj == this ) {
                return true;
            }
            if ( obj == null ) {
                return false;
            }
            if ( obj instanceof Contact ) {
                final Contact other = (Contact) obj;
                return other.getFirstName().equals( getFirstName() ) && other.getLastName().equals( getLastName() )
                        && other.getHomePhone().equals( getHomePhone() ) && other.getCellPhone().equals( getCellPhone() );

            } else {
                return false;
            }
        }

        /**
         * @param id
         */
        public void setId( final long id ) {
            this.id = id;
        }

        /**
         * @return id
         */
        public long getId() {
            return id;
        }

        /**
         * Constructor
         * 
         * @param firstName
         * @param lastName
         */
        public Contact( final String firstName, final String lastName ) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        /**
         * @return cellPhone
         */
        public String getCellPhone() {
            return cellPhone;
        }

        /**
         * @param cellPhone
         */
        public void setCellPhone( final String cellPhone ) {
            this.cellPhone = cellPhone;
        }

        /**
         * @return firstName
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * @param firstName
         */
        public void setFirstName( final String firstName ) {
            this.firstName = firstName;
        }

        /**
         * @return homePhone
         */
        public String getHomePhone() {
            return homePhone;
        }

        /**
         * @param homePhone
         */
        public void setHomePhone( final String homePhone ) {
            this.homePhone = homePhone;
        }

        /**
         * @return lastName
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * @param lastName
         */
        public void setLastName( final String lastName ) {
            this.lastName = lastName;
        }

    }

    static class ContactsDatabase {
        private final Map<Long, Contact> map = Collections.synchronizedMap( new HashMap<Long, Contact>() );

        private final List<Contact> idIdx = Collections.synchronizedList( new ArrayList<Contact>() );

        private final List<Contact> fnameIdx = Collections.synchronizedList( new ArrayList<Contact>() );

        private final List<Contact> lnameIdx = Collections.synchronizedList( new ArrayList<Contact>() );

        private final List<Contact> idDescIdx = Collections.synchronizedList( new ArrayList<Contact>() );

        private final List<Contact> fnameDescIdx = Collections.synchronizedList( new ArrayList<Contact>() );

        private final List<Contact> lnameDescIdx = Collections.synchronizedList( new ArrayList<Contact>() );

        private static ContactsDatabase _instance;

        /**
         * Constructor
         * 
         * @param count
         *            number of contacts to generate at startup
         */
        public ContactsDatabase( final int count ) {
            for ( int i = 0; i < count; i++ ) {
                add( ContactGenerator.getInstance().generate() );
            }
            updateIndecies();
        }

        public static ContactsDatabase getInstance() {
            if ( _instance == null ) {
                _instance = new ContactsDatabase( 200 );
            }
            return _instance;
        }

        /**
         * find contact by id
         * 
         * @param id
         * @return contact
         */
        public Contact get( final long id ) {
            final Contact c = map.get( id );
            if ( c == null ) {
                throw new RuntimeException( "contact with id [" + id + "] not found in the database" );
            }
            return c;
        }

        protected void add( final Contact contact ) {
            map.put( contact.getId(), contact );
            idIdx.add( contact );
            fnameIdx.add( contact );
            lnameIdx.add( contact );
            idDescIdx.add( contact );
            fnameDescIdx.add( contact );
            lnameDescIdx.add( contact );
        }

        /**
         * select contacts and apply sort
         * 
         * @param first
         * @param count
         * @param sortProperty
         * @param sortAsc
         * @return list of contacts
         */
        public List<Contact> find( final int first, final int count, final String sortProperty, final boolean sortAsc ) {
            final List<Contact> sublist = getIndex( sortProperty, sortAsc ).subList( first, first + count );
            return sublist;
        }

        protected List<Contact> getIndex( final String prop, final boolean asc ) {
            if ( prop == null ) {
                return fnameIdx;
            }
            if ( prop.equals( "id" ) ) {
                return ( asc )
                    ? idIdx
                    : idDescIdx;
            } else if ( prop.equals( "firstName" ) ) {
                return ( asc )
                    ? fnameIdx
                    : fnameDescIdx;
            } else if ( prop.equals( "lastName" ) ) {
                return ( asc )
                    ? lnameIdx
                    : lnameDescIdx;
            }
            throw new RuntimeException( "uknown sort option [" + prop + "]. valid options: [firstName] , [lastName]" );
        }

        /**
         * @return number of contacts in the database
         */
        public int getCount() {
            return fnameIdx.size();
        }

        /**
         * add contact to the database
         * 
         * @param contact
         */
        public void save( final Contact contact ) {
            if ( contact.getId() == 0 ) {
                contact.setId( ContactGenerator.getInstance().generateId() );
                add( contact );
                updateIndecies();
            } else {
                throw new IllegalArgumentException( "contact [" + contact.getFirstName() + "] is already persistent" );
            }
        }

        /**
         * delete contact from the database
         * 
         * @param contact
         */
        public void delete( final Contact contact ) {
            map.remove( contact.getId() );

            idIdx.remove( contact );
            fnameIdx.remove( contact );
            lnameIdx.remove( contact );
            idDescIdx.remove( contact );
            fnameDescIdx.remove( contact );
            lnameDescIdx.remove( contact );

            contact.setId( 0 );
        }

        private void updateIndecies() {
            Collections.sort( idIdx, new Comparator<Contact>() {
                public int compare( final Contact arg0, final Contact arg1 ) {
                    return arg0.getId() < arg1.getId()
                        ? -1
                        : arg0.getId() > arg1.getId()
                            ? 1
                            : 0;
                }
            } );

            Collections.sort( fnameIdx, new Comparator<Contact>() {
                public int compare( final Contact arg0, final Contact arg1 ) {
                    return ( arg0 ).getFirstName().compareTo( ( arg1 ).getFirstName() );
                }
            } );

            Collections.sort( lnameIdx, new Comparator<Contact>() {
                public int compare( final Contact arg0, final Contact arg1 ) {
                    return ( arg0 ).getLastName().compareTo( ( arg1 ).getLastName() );
                }
            } );

            Collections.sort( idDescIdx, new Comparator<Contact>() {
                public int compare( final Contact arg0, final Contact arg1 ) {
                    return arg1.getId() < arg0.getId()
                        ? -1
                        : arg1.getId() > arg0.getId()
                            ? 1
                            : 0;
                }
            } );

            Collections.sort( fnameDescIdx, new Comparator<Contact>() {
                public int compare( final Contact arg0, final Contact arg1 ) {
                    return ( arg1 ).getFirstName().compareTo( ( arg0 ).getFirstName() );
                }
            } );

            Collections.sort( lnameDescIdx, new Comparator<Contact>() {
                public int compare( final Contact arg0, final Contact arg1 ) {
                    return ( arg1 ).getLastName().compareTo( ( arg0 ).getLastName() );
                }
            } );

        }

    }

    static class ContactGenerator {
        private static ContactGenerator instance = new ContactGenerator();

        private static long nextId = 1;

        /**
         * @return static instance of generator
         */
        public static ContactGenerator getInstance() {
            return instance;
        }

        private final String[] firstNames =
                { "Jacob", "Emily", "Michael", "Sarah", "Matthew", "Brianna", "Nicholas", "Samantha", "Christopher", "Hailey",
                        "Abner", "Abby", "Joshua", "Douglas", "Jack", "Keith", "Gerald", "Samuel", "Willie", "Larry", "Jose",
                        "Timothy", "Sandra", "Kathleen", "Pamela", "Virginia", "Debra", "Maria", "Linda" };

        private final String[] lastNames =
                { "Smiith", "Johnson", "Williams", "Jones", "Brown", "Donahue", "Bailey", "Rose", "Allen", "Black", "Davis",
                        "Clark", "Hall", "Lee", "Baker", "Gonzalez", "Nelson", "Moore", "Wilson", "Graham", "Fisher", "Cruz",
                        "Ortiz", "Gomez", "Murray" };

        private ContactGenerator() {

        }

        /**
         * @return unique id
         */
        public synchronized long generateId() {
            return nextId++;
        }

        /**
         * generates a new contact
         * 
         * @return generated contact
         */
        public Contact generate() {
            final Contact contact = new Contact( randomString( firstNames ), randomString( lastNames ) );
            contact.setId( generateId() );
            contact.setHomePhone( generatePhoneNumber() );
            contact.setCellPhone( generatePhoneNumber() );
            return contact;
        }

        /**
         * generats <code>count</code> number contacts and puts them into
         * <code>collection</code> collection
         * 
         * @param collection
         * @param count
         */
        public void generate( final Collection<Contact> collection, final int count ) {
            for ( int i = 0; i < count; i++ ) {
                collection.add( generate() );
            }
        }

        private String generatePhoneNumber() {
            return new StringBuffer().append( rint( 2, 9 ) ).append( rint( 0, 9 ) ).append( rint( 0, 9 ) ).append( "-555-" ).append(
                    rint( 1, 9 ) ).append( rint( 0, 9 ) ).append( rint( 0, 9 ) ).append( rint( 0, 9 ) ).toString();
        }

        private int rint( final int min, final int max ) {
            return (int) ( Math.random() * ( max - min ) + min );
        }

        private String randomString( final String[] choices ) {
            return choices[rint( 0, choices.length )];
        }

    }

}
