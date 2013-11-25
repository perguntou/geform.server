/**
 * 
 */
package br.ufrj.del.geform.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


/**
 *
 */
public class App extends Application {

	/*
	 * (non-Javadoc)
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add( FormResources.class );
		return s;
	}

}
