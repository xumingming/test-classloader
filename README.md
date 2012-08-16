h2. Overview
There are 4 eclipse projects in this repository:
fake-storm: plays the role of `Storm`
user-topology: plays the role of `User Topology`
testlib-storm: plays the role of `Storm`'s dependency
testlib-user: plays the role of `User Topology`'s dependency, it is almost the same as `testlib-storm`, just the implementation of `Man#hello` is different.

h2. Preparation
Before run the test, build the jar for `user-topology` and `testlib-user`, and put the two jars into `/tmp` folder, `TopologyClassLoader` will try to load the classes there.

h2. Run the test
```bash
java -cp bin:../testlib-storm/bin/ -Djava.system.class.loader=xumingmingv.classloader.TopologyClassLoader xumingmingv.classloader.StormCore
```

h2. Result
```bash
StormCore: StormCore classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: IPerson classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: Man classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: ISpout classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: UserSpout classloader: xumingmingv.classloader.TopologyClassLoader@67386000
I am Man (testlib-storm)
UserClass: UserClass classloader: xumingmingv.classloader.TopologyClassLoader@67386000
UserClass: IPerson classloader: xumingmingv.classloader.TopologyClassLoader@67386000
UserClass: Man classloader: xumingmingv.classloader.TopologyClassLoader@67386000
UserClass: ISpout classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
I am Man (testlib-user)
```

We can see that the dependency is isolated(from `I am Man (testlib-storm)` and `I am Man (testlib-user)`)
