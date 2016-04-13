package com.jakebellotti.io;

/**
 * @author Jake Bellotti
 * @date Apr 12, 2016
 */
public class Logger {
	
	private static boolean ON = true;
	
	private Class<?> classReference;
	private boolean simpleName = true;
	
	/**
	 * Creates a new Logger from the given class, which will display the simple name of the class.
	 * @param classReference
	 */
	public Logger(Class<?> classReference) {
		this(classReference, true);
	}
	
	/**
	 * 
	 * @param classReference
	 * @param simpleName
	 */
	public Logger(Class<?> classReference, boolean simpleName) {
		this.classReference = classReference;
		this.simpleName = simpleName;
	}
	
	/**
	 * Creates a new Logger instance, which will log messages without class references.
	 */
	public Logger() {
		
	}
	
	/**
	 * Prints the message on a new line.
	 * @param message
	 */
	public void println(Object message) {
		print(message + "\n");
	}
	
	/**
	 * Prints a message
	 * @param message
	 */
	public void print(Object message) {
		if(ON)
			System.out.print(classReference == null ? "" : ("["+ getClassName()+ "]: ") + message);
	}
	
	private String getClassName() {
		return simpleName ? classReference.getSimpleName() : classReference.getName();
	}
	
	public static void turnOff() {
		ON = false;
	}

}
