
## springboot 一些配置

### 1. @Cacheable key参数的使用
* 当前方法名: #root.methodName
* 当前方法: #root.method.name
* 当前被调用的对象: #root.target
* 当前被调用的对象的: #root.targetClass
* 当前方法参数组成的数组: #root.args[0]
* 当前被调用的方法使用的: #root.caches[0].name


### 2. AOP JoinPoint\ProceedingJoinPoint 常用方法
* 获取连接点方法运行时的入参列表: getArgs()
* 获取连接点的方法签名对象: getSignature()
* 获取连接点所在的目标对象: getTarget()
* 获取代理对象本身:  getThis()
* 通过反射执行目标对象的连接点处的方法: proceed()
* 通过反射执行目标对象连接点处的方法，不过使用新的入参替换原来的入参 proceed(java.lang.Object[] args)