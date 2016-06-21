package com.jakebellotti;

import java.io.File;
import java.util.HashMap;

import com.jakebellotti.model.ListOrderer;
import com.jakebellotti.model.LoadPluginHandler;
import com.jakebellotti.plugin.MovieListOrdererRepository;

import groovy.lang.GroovyClassLoader;

/**
 *
 * @author Jake Bellotti
 * @since Jun 21, 2016
 */
public class GroovyLoader {
	
	private static final String SCRIPT_EXTENSION = ".groovy";

	private static final HashMap<String, LoadPluginHandler> handlers = new HashMap<>();

	public static final void load() {
		assignHandlers();
		try (GroovyClassLoader groovyClassLoader = new GroovyClassLoader()) {
			File root = new File("./groovy/");
			scanFile(groovyClassLoader, root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void scanFile(GroovyClassLoader loader, File root) {
		if (root.isDirectory()) {
			File[] list = root.listFiles();
			for (File f : list) {
				if (f.isDirectory()) {
					scanFile(loader, f);
				} else {
					loadPlugin(loader, f);
				}
			}
		}
	}
	
	private static final void loadPlugin(GroovyClassLoader loader, File file) {
		if(! file.exists() || file.isDirectory())
			return;
		final int extenstion = file.getName().lastIndexOf(".");
		if(extenstion < 0)
			return;
		if(! file.getName().substring(extenstion).equalsIgnoreCase(SCRIPT_EXTENSION))
			return;
		try {
			 Class<?> loaded = loader.parseClass(file);
			 handle(loaded);
		} catch(Exception e) {
			//TODO log script errors
			e.printStackTrace();
		}
	}

	private static final void handle(Class<?> clazz) {
		LoadPluginHandler handler = handlers.get(clazz.getSuperclass().getName());
		if (handler != null) 
			handler.loadPlugin(clazz);
	}

	private static final void assign(Class<?> clazz, LoadPluginHandler handler) {
		handlers.put(clazz.getName(), handler);
	}

	private static final void assignHandlers() {
		assign(ListOrderer.class, MovieListOrdererRepository::add);
	}

}
