package xumingmingv.user;

import xumingmingv.classloader.ISpout;
import xumingmingv.testlib.IPerson;
import xumingmingv.testlib.Man;


public class UserSpout implements ISpout {
	public void foo() {
		Man man = new Man();
		System.out.println("UserSpout: UserSpout classloader: " + this.getClass().getClassLoader());
		System.out.println("UserSpout: IPerson classloader: " + IPerson.class.getClassLoader());
		System.out.println("UserSpout: Man classloader: " + man.getClass().getClassLoader());
		System.out.println("UserSpout: ISpout classloader: " + ISpout.class.getClassLoader());
		System.out.println("UserSpout: Man#hello: " + man.hello());
		;
	}
}
