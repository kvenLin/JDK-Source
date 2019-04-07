# GC回顾
## 基本概念
* GC就是 Garbage Collection,垃圾回收
>因为程序在运行时需要不断的在堆内存中进行对象的创建和销毁,避免没有使用的对象持续占用空间造成资源的浪费,
同时JVM为了方便程序员只注重业务逻辑不用关心底层的垃圾回收,所以底层自动实现了垃圾回收器对垃圾进行自动回收,
实现自动的对堆内存进行管理.
* 主要的GC类型:
    * YoungGC: 新生代的回收,也称为MinorGC或者YGC
    * OldGC: 老年代的回收,只单独回收Old区域的只有CMS回收器.但**是CMS并不是完全并发的,仍然有两个阶段会STW**(初始标记,重新标记)
    * FullGC: 对整个堆进行垃圾回收,也称为MajorGC
* FullGC触发条件(即导致FullGC的原因):
    * 在没有配置-XX:+DisableExplicitGC情况下,System.gc()可能会触发FullGC
    * promotion failed(晋升失败): 当在MinorGC时,Survivor放不下,对象只能放入老年代,而此时老年代也放不下触发FullGC
    * concurrent mode failure: 是在执行CMS GC的过程中同时有对象要放入老年代,而老年代空间不足导致
    * Metaspace Space使用达到MaxMetaspaceSize阈值
    * 执行jmap -histo:live或者jmap -dump:live
    