package xumingmingv.classloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Enumeration;

public class TopologyClassLoader extends URLClassLoader {
	private String name;
	public void setName(String name) {
		this.name = name;
	}
    public static TopologyClassLoader create(Collection<String> urls) {
        URL[] urlArr = string2Url(urls);
        return new TopologyClassLoader(urlArr);
    }

    public static TopologyClassLoader create(String urls) {
        URL[] urlArr = string2Url(urls.split(File.separator));
        return new TopologyClassLoader(urlArr);
    }
    
    public TopologyClassLoader(URL[] urls){
        super(urls);
    }
 
    public TopologyClassLoader(URL[] urls, ClassLoader parent){
        super(urls, parent);
    }
    
    public TopologyClassLoader(ClassLoader parent){
        super(string2Url(new String[] {"/tmp/testlib-user.jar", "/tmp/user-topology.jar"}), parent);
        this.name = "default";
    }
    
	@Override
	public synchronized Class<?> loadClass(String name)
			throws ClassNotFoundException {
		//System.out.println("Loader: " + this.name + ", CLASSNAME: " + name);
		Class<?> clazz = this.findLoadedClass(name);

		if (clazz == null) {
			// first find the class from the topology's dependencies
			try {
				clazz = this.findClass(name);
			} catch (ClassNotFoundException e) {
				// ignore it.
				//System.out.println("Can not find the class[" + name + "] in TopologyClassLoader(" + this.name + ")");
			}
			//System.out.println(this.getURLs()[0].getPath());
			if (clazz == null) {
				// then delegate to parent loader to load
				clazz = this.getParent().loadClass(name);
			}
		}

		return clazz;
	}
    
    public Enumeration<URL> getResources(String name) throws IOException {
        Enumeration<URL> ret = this.findResources(name);
        
        if (ret == null || !ret.hasMoreElements()) {
            ret = this.getParent().getResources(name);
        }
        
        return ret;
    }
    
    /**
     * Takes a collection of file path, return an array of corresponding URL.
     * @param urls
     * @return
     */
    protected static URL[] string2Url(Collection<String> urls) {
        URL[] urlArr = new URL[urls.size()];
        int idx = 0;
        for (String url : urls) {
            try {
                System.out.println("processing url: " + url);
                urlArr[idx] = new File(url).toURI().toURL();
                idx++;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urlArr;
    }
    
    protected static URL[] string2Url(String[] urls) {
        URL[] urlArr = new URL[urls.length];
        int idx = 0;
        for (String url : urls) {
            try {
                urlArr[idx] = new File(url).toURI().toURL();
                idx++;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urlArr;
    }
}
