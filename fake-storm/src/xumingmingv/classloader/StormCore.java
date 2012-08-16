package xumingmingv.classloader;

import xumingmingv.testlib.IPerson;
import xumingmingv.testlib.Man;

public class StormCore {
	public static void main(String[] args) throws Exception {
		StormCore core = new StormCore();
		
		System.out.println("StormCore: StormCore classloader: " + core.getClass().getClassLoader());
		System.out.println("StormCore: IPerson classloader: " + IPerson.class.getClassLoader());
		System.out.println("StormCore: Man classloader: " + Man.class.getClassLoader());
		System.out.println("StormCore: ISpout classloader: " + ISpout.class.getClassLoader());
		
		// Here we load the UserSpout
		// In storm this step is implemented using ObjectInputStream and deserialization
		// the merchanism is similar: ObjectInputStream is also using classloader to 
		// load the class
		Class<?> userSpoutClass = ClassLoader.getSystemClassLoader().loadClass("xumingmingv.user.UserSpout");
		System.out.println("StormCore: UserSpout classloader: " + userSpoutClass.getClassLoader());
		
		
		// call the Man.foo() directly from StormCore
		Man man = new Man();
		man.hello();

		// call userSpout's foo, it will then call
		// man.hello()
		// we will see that the two man.hello()'s result is different
		ISpout userSpout = (ISpout)userSpoutClass.newInstance();
		userSpout.foo();
	}
}
