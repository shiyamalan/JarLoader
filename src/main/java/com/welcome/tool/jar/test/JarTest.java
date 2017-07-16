package com.welcome.tool.jar.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URLClassLoader;

import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;

/**
 * Hello world!
 *
 */
public class JarTest {
	public static void main(String[] args) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException, ClassNotFoundException, URISyntaxException {
		System.out.println("Jar Test running starts");

		/*
		 * loadJar();
		 * 
		 * JarApp jarAPp = new JarApp();
		 * 
		 * System.out.println(jarAPp);
		 */

		String rootPath = System.getProperty("user.dir");

		String pathA = "\\lib\\appA\\JarApp-0.0.1-SNAPSHOT.jar";

		String pathB = "\\lib\\appB\\JarApp-0.0.1-SNAPSHOT.jar";

		ClassPathHacker pathAHacker = new ClassPathHacker(rootPath + pathA);

		pathAHacker.showClass();

		ClassPathHacker pathBHacker = new ClassPathHacker(rootPath + pathB);

		pathBHacker.showClass();

	}

	private static void testJarLoading() {

		String rootDir = System.getProperty("user.dir");
		JarClassLoader jcl = new JarClassLoader();
		try {
			jcl.add(new FileInputStream(new File(
					rootDir + "\\lib\\appB\\JarApp-0.0.1-SNAPSHOT.jar")));

			JclObjectFactory factory = JclObjectFactory.getInstance();

			// Create object of loaded class
			Object obj = factory.create(jcl, "com.welcome.tool.web.JarApp");

			System.out.println(obj);

			// jcl.unloadClass("com.welcome.tool.web.JarApp");

			/*
			 * obj = factory.create(jcl, "com.welcome.tool.web.JarApp");
			 * 
			 * System.out.println(obj);
			 */

			/*
			 * jcl.add(new FileInputStream(new File( rootDir +
			 * "\\lib\\appA\\JarApp-0.0.1-SNAPSHOT.jar")));
			 * 
			 * factory = JclObjectFactory.getInstance();
			 * 
			 * Object objA = factory.create(jcl, "com.welcome.tool.web.JarApp");
			 */

			System.out.println(obj);

			URLClassLoader load = new URLClassLoader(null);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void loadJar() throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, IOException {
		/*
		 * Method m = JarApp.class.getDeclaredMethod("showAppName",
		 * String.class); m.setAccessible(true);
		 * 
		 * String rootPath = System.getProperty("user.dir"); File jarFile = new
		 * File( rootPath + "\\lib\\appB\\JarApp-0.0.1-SNAPSHOT.jar");
		 * 
		 * JarApp app = new JarApp(); m.invoke(app, jarFile.toURI().toURL());
		 * String cp = System.getProperty("java.class.path"); if (cp != null) {
		 * cp += File.pathSeparatorChar + jarFile.getCanonicalPath(); } else {
		 * cp = jarFile.toURI().getPath(); }
		 * System.setProperty("java.class.path", cp);
		 */
	}
}
