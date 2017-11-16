# springG
描述
-----
>springG项目是对spring中IoC源码的重新实现，省略了对IoC容器初始化无关的代码，展现IoC容器加载的全过程。

起因
-----
>刚开始学习spring源码，看着密密麻麻的代码，完全无从下手，在网上看了大量博客和参考有关spring的书籍后知道了refresh()是整个IoC容器初始化的入口，虽然有了教程，但光看教程也很枯燥无味，因此我决定写springG，意在通过项目学习源码，再由源码驱动项目。

什么是SPRING框架？
------
>Spring是一个轻量级的框架，在Spring这个一站式的应用平台或框架中，其中的各个模块除了依赖IoC容器和AOP之外，相互之间并没有很强的耦合性。Spring的最终目的是简化应用开发的编程模型。					                     									——《Spring技术内幕》


spring中部分重要的接口
------
BeanFactory：*最基本的IoC容器，它存储了BeanDefinition、单例Bean等许多IoC容器必需的元素。*
<br>BeanDefinition:*抽象了对Bean的定义，是让容器起作用的主要数据类型。*
<br>ApplicationContext：*高级形态的IoC容器，在BeanFactory的基础上添加了许多附加功能。*
<br>Resource：*提供对不同的底层资源的统一访问接口，位于org.springframework.core.io包。*



IOC容器的初始化过程
------
**一、配置文件读取为Resource**
<br>IoC容器初始化首先需要将XML配置文件加载到内存中，通过ResourceLoader.getResource(String location)方法获得XML的Resource对象.项目中只实现了DefualtResourceLoader类，它处理classpath下的资源文件，在[《张开涛——跟我学spring3第四章》](http://jinnianshilongnian.iteye.com/blog/1416320)有详细讲解。

**二、Resource解析为BeanDefinition**
<br>通过调用Resource.getInputStream()获得InputStream，再由JDK中DocumentBuilderFactory生成doc文档格式对象（spring使用的SAX解析配置文件），此时，这里我用DOM解析的方式获取BeanDefinition对象。这一步的核心是BeanDefinition的加载，我们在DefaultBeanDefinitionDocumentReader的processBeanDefinition()中给出了解析BeanDefinition的流程，其中包括从name和id中选择BeanName的方式，BeanName唯一性的判断，constructor-arg、property标签的解析等。
>**解析property节点**
<br>在springG中完成了对List和基本数据类型的解析支持，这里需要知道几个重要的类：<br>
<br>PropertyValue:*存储属性名和值的数据结构。*
<br>MutablePropertyValues:*用于存储PropertyValue，并对外提供操作PropertyValue的方法，值得注意的是，它采用深拷贝（对象属性所引用的对象全部进行新建对象复制,以保证深复制的对象的引用不对原对象产生影响）的方式返回PropertyValue对象，保证了自身数据的完整性。在BeanDefinition中保存的是MutablePropertyValue对象。*

**三、BeanDefinition注册到BeanFactory容器**
<br>上一步获得的BeanDefinition被包装为BeanDefnitionHolder，它里面储存了BeanDefinition对象、beanName、aliase属性，并对外提供get方法。将BeanDefinitionHolder对象传入registerBeanDefinition()，分别在BeanFactory容器中的aliasMap中添加别名，beanDefinitionNames中添加BeanName和在beanDefinitionMap中添加BeanDefinition对象，这里值得关注的是如果在beanDefinitionMap中有与待添加的Bean定义相同的BeanName，将采用替换的形式添加BeanDefinition。
