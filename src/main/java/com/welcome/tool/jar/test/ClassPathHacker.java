package com.welcome.tool.jar.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassPathHacker extends URLClassLoader {

	public ClassPathHacker(String path) throws IOException {
		super(addFile(path));
	}

	private final Class[] parameters = new Class[]{URL.class};

	public static URL[] addFile(String s) throws IOException {
		File f = new File(s);
		return new URL[]{getURL(f)};
	}// end method

	public static URL getURL(File f) throws IOException {

		return f.toURL();
	}// end method

	public void showClass()
			throws IOException, URISyntaxException, ClassNotFoundException {

		URLClassLoader sysloader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();

		System.out.println("System Class Loader:- " + sysloader.hashCode());
		Class sysclass = URLClassLoader.class;
		//
		URL url = this.getURLs()[0];
		File jarFile = new File(url.toURI());
		Class<?> clas = null;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[]{this.getURLs()[0]});
			//
			// String cp = System.getProperty("java.class.path");
			// if (cp != null) {
			// cp += File.pathSeparatorChar + jarFile.getCanonicalPath();
			// } else {
			// cp = url.toURI().getPath();
			// }
			// System.setProperty("java.class.path", cp);

			clas = this.findClass("com.welcome.tool.web.JarApp");
			//
			// Class<?> clasAppD =
			// this.loadClass("com.welcome.tool.web.Details");
			// Class<?> clasApp = this
			// .loadClass("com.welcome.tool.web.Application");
			//
			// Constructor<?> ctorAppD = clasAppD.getConstructor();
			// Object objectAppD = ctorAppD.newInstance();
			//
			// Constructor<?> ctorApp = clasApp.getConstructor();
			// Object objectApp = ctorApp.newInstance();
			//
			// System.out.println(clas);
			//
			// System.out.println("-------------------------------------------");
			// System.out.println(objectApp);
			// System.out.println(objectAppD);

			try {
				Constructor<?> ctor = clas.getConstructor();
				Object object = ctor.newInstance();
				method = object.getClass().getMethod("showAppName");
				System.out.println("System loaded");

				method.invoke(object);

			} catch (SecurityException e1) {
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			}

		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException(
					"Error, could not add URL to system classloader");
		} // end try catch

		// JarFile jar = new JarFile(jarFile.getPath());
		// Enumeration<JarEntry> e = jar.entries();
		//
		// URL[] urls = {new URL("jar:file:" + jarFile.getPath() + "!/")};
		// URLClassLoader cl = URLClassLoader.newInstance(urls);
		//
		// while (e.hasMoreElements()) {
		// JarEntry je = e.nextElement();
		// if (je.isDirectory() || !je.getName().endsWith(".class")) {
		// continue;
		// }
		// // -6 because of .class
		// String className = je.getName().substring(0,
		// je.getName().length() - 6);
		// className = className.replace('/', '.');
		// Class c = cl.loadClass(className);
		//
		// c = null;
		// System.out.println("Class:- " + className);
		//
		// }
		clas = null;
		
		
		sysloader.close();

		sysloader = null;
		
		System.out.println(sysloader);
		
		

	}// end method

	/**
	 * Loads the class from the file system. The class file should be located in
	 * the file system. The name should be relative to get the file location
	 *
	 * @param name
	 *            Fully Classified name of class, for example com.journaldev.Foo
	 */
	private Class getClass(String name) throws ClassNotFoundException {
		String file = name.replace('.', File.separatorChar) + ".class";
		byte[] b = null;
		try {
			// This loads the byte code data from the file
			b = loadClassFileData(file);
			// defineClass is inherited from the ClassLoader class
			// that converts byte array into a Class. defineClass is Final
			// so we cannot override it
			Class c = defineClass(name, b, 0, b.length);
			resolveClass(c);
			return c;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Every request for a class passes through this method. If the class is in
	 * com.journaldev package, we will use this classloader or else delegate the
	 * request to parent classloader.
	 *
	 *
	 * @param name
	 *            Full class name
	 */

	public Class loadClass1(String name) throws ClassNotFoundException {
		System.out.println("Loading Class '" + name + "'");
		if (name.startsWith("com.welcome.tool.web")) {
			System.out.println("Loading Class using CCLoader");
			return getClass(name);
		}
		return super.loadClass(name);
	}

	/**
	 * Reads the file (.class) into a byte array. The file should be accessible
	 * as a resource and make sure that its not in Classpath to avoid any
	 * confusion.
	 *
	 * @param name
	 *            File name
	 * @return Byte array read from the file
	 * @throws IOException
	 *             if any exception comes in reading the file
	 */
	private byte[] loadClassFileData(String name) throws IOException {
		InputStream stream = getClass().getClassLoader()
				.getResourceAsStream(name);
		int size = stream.available();
		byte buff[] = new byte[size];
		DataInputStream in = new DataInputStream(stream);
		in.readFully(buff);
		in.close();
		return buff;
	}
}
