# CodeReview代码规范文档

### **1、基本代码检查清单**

![img](https:////upload-images.jianshu.io/upload_images/1489654-d5e7408ffe58b756.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/640/format/webp)

1.自己是否可以很容易的理解代码？

2.代码是否遵循编码规范？

3.相同的代码是否重复两次？

4.是否可以单元测试或者调试代码，定位到问题的根本原因？

5.这个函数或类是否太大？如果是的，函数或类是否有太多的职责？

### **2、详细代码检查清单**

以下代码审查清单提供了在审查代码时需要考虑的各个方面

![img](https:////upload-images.jianshu.io/upload_images/1489654-1ebb0890759b2f23.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/640/format/webp)

#### **2.1、代码格式化**

在查看代码时，检查代码格式以提高可读性，确保没有阻塞：

a）使用对齐（左边距），正确的空格。还要确保代码的开始和结束容易识别。

b）确保遵循正确的命名约定（Pascal，CamelCase等）。

c）代码应符合标准的14寸笔记本电脑屏幕。在21英寸显示器中，不需要水平滚动来查看代码，可以在修改代码的同时打开其他窗口（工具箱，属性等），因此确保代码在14英寸显示器正常。

d）删除掉注释的代码。如果需要，可以从源代码控制（如SVN）获取到。



#### **2.2、架构**

代码应遵循已经定义好的架构。

1.有关分层

根据需要进行分层（展现层，业务层和数据层)。

对文件类型进行划分（HTML，JavaScript和CSS）。

2.代码和现有的代码规范/技术保持同步。

3.设计模式：在完全理解问题和业务之后，使用适当的设计模式（如果可以）。



#### **2.3、良好的编码习惯**

1.没有硬编码，使用常量/配置值。

2.一组相似的值使用枚举（ENUM）。

3.注释 - 不要为你正在做的事情写注释，而是写出你为什么要这么做。指出使用 的技巧，解决方法和临时修复。另外，在to-do注释里写上待处理的任务，可     以方便跟踪。

4.避免多个if/else块。

5.尽可能使用框架提供的功能，而不是自己造轮子。



#### **2.4、非功能要求**

a）可维护性（可支持性） - 希望系统在未来花最少的精力去做维护。应该容易定位和修复问题

1.可读性：代码应该是清晰易读的。可以从代码中读取很多信息，使用恰当的变量名，函数名，类名。如果你用很多的时间去阅读代码，说明代码需要重构或者至少要把注释写的清楚点。

2.可测试性：代码应该很容易被测试。拆分成一个单独的函数（如果需要）。系统的其他层使用接口，可以很方便的mock。避免静态函数，单例类，因为这些不容易被mock测试。

3.可调试性：可以打印日志，参数数据和异常详细信息，可以方便定位到问题原因。如果你正在使用log4net的类似组件，可以添加支持数据库日志，查询日志表很容易。

4.可配置性： 可配置的值放到以下地方（XML文件，数据库表），数据可以自由变更不需要改代码。

b）可重用性

1.DRY（Do not Repeat Yourself）原则：同一代码不应该重复两次以上。

2.考虑可重用的服务，功能和组件。

3.考虑通用函数和类。

c）可靠性 - 异常处理和清理（释放）资源。

d）可扩展性 - 轻松添加功能，对现有代码进行最小的更改。一个组件可以被更好的组件替换。

e）安全性 - 进行身份验证，授权，输入数据验证，避免诸如SQL注入和跨站脚本（XSS）等安全威胁 ，加密敏感数据（密码，信用卡信息等）

f）性能

1.使用合适的数据类型，例如StringBuilder，通用集合类。

2.懒加载，异步和并行处理。

3.缓存和会话/应用程序数据。

g）可扩展性 - 考虑是否支持大用户量/大数据？是否可以部署到集群？

h）可用性 - 站在用户的角度考虑下接口/API是否容易理解和使用，如果你不确定用户接口的设计，可以和业务人员一起讨论你的想法



#### **2.5、面向对象分析与设计（OOAD）原则**

单一责任原则（SRS）：不要将多个职责放在单个类或函数中，提取出单独的类和函数。

开放封闭原则：添加新功能时，不应修改现有代码。 新功能应该用新的类和函数来编写。

Liskov替换原则：子类不应改变父类的行为（含义）。子类可以用作基类的替代。

接口隔离：不要创建冗长的接口，而是根据功能将它们拆分成较小的接口。接口不应包含功能不需要的依赖项（参数）。

依赖注入：针对依赖不要硬编码，而是注入它们。

在大多数情况下，原则是相互关联的，遵循一个原则也会满足其他原则。如果遵循“单一责任原则”，则可重用性和可测试性将会加强。

在少数情况下，一个需求可能与其他需求相矛盾。因此，需要根据重要性进行权衡，例如。性能与安全性（UI，中间层，数据库）的检查和日志记录太多会降低应用程序的性能。但是有些系统，特别是与金融和银行有关的应用程序需要很多埋点，审核日志记录等。因此，在性能方面有一点妥协可以增强的安全性。