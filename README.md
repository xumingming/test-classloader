## Overview
There are 4 eclipse projects in this repository:
* fake-storm: plays the role of `Storm`
* user-topology: plays the role of `User Topology`
* testlib-storm: plays the role of `Storm`'s dependency
* testlib-user: plays the role of `User Topology`'s dependency, it is almost the same as `testlib-storm`, just the implementation of `Man#hello` is different.

## Preparation
Before run the test, build the jar for `user-topology` and `testlib-user`, and put the two jars into `/tmp` folder, `TopologyClassLoader` will try to load the classes there.

So the jars in `/tmp` are:
```bash
$ ls /tmp/*.jar
/tmp/testlib-user.jar  /tmp/user-topology.jar
```
These two jars contains the `User Topology`'s code and its dependencies.

## Run the test
```bash
cd fake-storm
java -cp bin:../testlib-storm/bin/ 
     -Djava.system.class.loader=xumingmingv.classloader.TopologyClassLoader 
     xumingmingv.classloader.StormCore
```

`-cp bin:../testlib-storm/bin/` specifies the `Fake Storm Core Class` and its dependencies.

## Result
```bash
ClassLoader xumingmingv.classloader.StormCore
StormCore: StormCore classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: IPerson classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: Man classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: ISpout classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
StormCore: UserSpout classloader: xumingmingv.classloader.TopologyClassLoader@67386000
StormCore: Man#hello: I am Man (testlib-storm)
UserSpout: UserSpout classloader: xumingmingv.classloader.TopologyClassLoader@67386000
UserSpout: IPerson classloader: xumingmingv.classloader.TopologyClassLoader@67386000
UserSpout: Man classloader: xumingmingv.classloader.TopologyClassLoader@67386000
UserSpout: ISpout classloader: sun.misc.Launcher$AppClassLoader@1ef6a746
UserSpout: Man#hello: I am Man (testlib-user)
```

We can see that the dependency is isolated from these two lines:
```bash
StormCore: Man#hello: I am Man (testlib-storm)
UserSpout: Man#hello: I am Man (testlib-user)
```
