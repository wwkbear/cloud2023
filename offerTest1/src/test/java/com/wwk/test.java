package com.wwk;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author wwkbear
 * @create 2023-02-19-17:24
 */
public class test {
    /**
     * 总结：重点考察赋值和自增对变量值的影响。
     *  1.赋值= ，最后计算
     *  2。=右边的从左到右加载值依次压入操作数栈
     *  3.实际先算哪个，看运算符优先级
     *  4.自增、自减操作 都是直接修改变量的值，不经过操作数栈
     *  5.最后的赋值之前，临时结果也是存储在操作数栈中。
     */
    @Test
    public void test1(){
        int i = 1;
        i = i++; //1 这里i后加，所以为1，然后自增是修改临时变量的值，并不会影响到栈中的i=1
        int j = i++; //j 1 i 2
        int k = i + ++i * i++; // k=2+3 *3 ,i=4
        //代码运行结果是多少？
        System.out.println("i=" + i); //答：4
        System.out.println("j=" + j); //答：1
        System.out.println("k=" + k );//答：11

    }

    /**
     * 单例模式
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test2() throws InterruptedException, ExecutionException {
        /*
        写一个单例类 Singleton，单例设计模式。单：单一，例：实例。
        单例设计模式，即某个类在整个系统中只能有一个实例对象可被获取和使用的代码模式。
        例：JVM运行环境的Runtime类。
        一、单例模式要点：
            1.一个类只能有一个实例。（外部不能构建，构造器私有化）
            2.必须自行创建这个实例。（含有一个该类的静态变量保持这个唯一实例）
            3.必须自行向整个系统提供这个实例。（对外提供获取的实例的方法：1.直接暴露变量。2.用静态变量的get方法获取）

        二、根据创建的时机不同，能分成两类。
            饿汉式：在类初始化的时候创建实例。故没有线程安全问题。缺点：存在可能不使用的情况，浪费资源。项目启动慢，
                1.直接实例化饿汉式（简洁直观）
                2.枚举式（最简洁）
                3.静态代码块饿汉式（适合复杂实例化）

            懒汉式：即调即创建。存在线程安全问题。
                1.线程不安全（适用于单线程）
                2.线程安全（适用多线程）
                3.静态内部类形式（适用多线程）
         */
        //饿汉式：
//        System.out.println(Singleton1.INSTENCE);
//        System.out.println(Singleton2.INSTANCE);
//
//        Singleton3 instance = Singleton3.getInstance();
//        System.out.println(instance);

        //懒汉式:
        //单线程：
//        Singleton4 s1 = Singleton4.getSingleton4();
//        Singleton4 s2 = Singleton4.getSingleton4();
//        System.out.println("单线程");
//        System.out.println(s1 == s2);
        //多线程：
        Callable<Singleton4> callable = new Callable<Singleton4>() {
            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getSingleton4();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Singleton4> submit1 = executorService.submit(callable);
        Future<Singleton4> submit2 = executorService.submit(callable);

        Singleton4 singleton4 = submit1.get();
        Singleton4 singleton41 = submit2.get();

        System.out.println("多线程");
        System.out.println(singleton4 == singleton41);
        System.out.println(singleton4);
        System.out.println(singleton41);
    }

    /**
     * 考察类的成员的初始化的执行顺序。详情看Father，Son
     */
    @Test
    public void test3(){
        //个人理解：代码块 和 成员变量的执行等级是一样的，就看谁在上面，然后按照静态优先
        //类的初始化顺序：成员变量 | 代码块、构造器
        //额外考察的是继承问题（test被重写），静态问题。（第二层初始化的时候就没有再去调用静态的method）
    /*
    详细的解析：
    1.类初始化过程
        1.一个类要创建实例需要先加载并初始化该类
            main方法所在的类需要先加载和初始化。

        2.一个子类要初始化需要先初始化父类

        3.一个类初始化就是执行<clinit>（）方法
            <clinit>()方法由静态类变量显示赋值代码 和 静态代码块组成
            类变量显示赋值代码和静态代码块代码从上到下顺序执行
            <clinit>()方法只执行一次

     2.实例初始化过程
        1.实例初始化就是执行<init>()方法
            <init>()方法可能重载有多个，有几个构造器就有几个<init>方法。
            <init>()方法由 非静态实例变量显式赋值 和 非静态代码块、对应构造器组成
            非静态实例变量显式赋值 和 非静态代码块 从上到下顺序执行，而对应构造器的代码最后执行
            每次创建实例对象，调用对应构造器，执行的就是对应的<init>方法
            <init>()方法的首行是super（）或super（参数），即对应父类的<init>方法
      3.重写重载
        1.override 和 overload 的区别？
            重写：关联的其他类 创建 满足重写要求的方法。
            重载：是指同类下同方法名的方法重复使用。
        2.override重写的要求：
            1.三同：方法名、返回值类型、形参列表
            2.能修改的权限修饰符（只大不小），抛出异常列表（只小不大）
        3.哪些方法不可以被重写
            1.final方法
            2.静态方法
            3.private私有 类成员
        4.对象的多态性
            1.子类重写了父类的方法，通过子类对象调用的一定是子类重写过的代码
                （类似：就近原则）（如果是父类调用被子类重写过的方法，则不会调用子类重写方法，而是调用自身的方法）
            2.非静态方法默认的调用对象是this
            3.this对象： 在构造器创建的对象 或者说 <init>方法中正在创建的对象
     */
    }

    /**
     * 考察参数传递机制。详情看 Exam4
     */
    @Test
    public void test4(){
        /*
            1.方法的参数传递机制
                复制值 方式。基本类型：复制数据值，引用类型：复制地址值。

            2.String、包装类等对象的不可变型。一旦改变值，就会改变

            3.JVM运行时会将
                局部变量 放入栈，
                常量 放入常量池，
                引用类型 放入堆
        */
    }

    /**
     * 考察实际问题，小算法题。
     */
    @Test
    public void test5(){
        //编程题：有N 步台阶，一次只能上1步或者2步，共有多少种走法？
        /*
            解决问题的算法：
            1.一般解决总有递归这种方式简单粗暴。
            2.循环迭代型
         */
        //1.递归：
        /*
            1.先从小问题着手：
            只有一级台阶的时候：只有一种走法
            只有两级台阶的时候：有两种走法（1，2）
            有三级台阶：可以利用倒推法：先退1步则变成二级台阶的方式。即有3种。或者退2步则变成一级台阶方式。即有两种走法。加起来5种
            同理4级台阶：退1步变成三级。退2步变两级。然后不断执行类似的方法，将未知问题转换到已知问题。
            同理N级台阶：（N-1） + （N-2） 然后不断循环到（1） + （2）然后全部加起来就是结果。
         */
        int n = 40;
//        long start = System.currentTimeMillis();
//        System.out.println(digui(n));//165580141
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);//656ms

        //2.循环迭代：
        /*
            1.同样先从小问题入手
            一二级台阶，1，2两种方法。
            三级台阶：是二级台阶和一级台阶的相加形式。f（3）= f（2）+f（1）
            四级台阶：是三级台阶和二级台阶的相加形式。f（4）= f（3）+f（2）.由于f（3）已经被计算过是已知值。所以把第一项换成刚刚计算好的即可。
            同理五级，N级。不断从已知值向未知值计算。（与递归的运算方式不一样，递归是从未知计算到已知。）
         */
        long start = System.currentTimeMillis();
        System.out.println(loop(n));//165580141
        long end = System.currentTimeMillis();
        System.out.println(end - start);//<1ms
    }
    public int digui(int n){
        if (n < 1){
            throw new RuntimeException();
        }
        if (n == 1 || n == 2){
            return n;
        }
        return digui(n - 2) + digui(n - 1);
    }
    public int loop(int n){
        if (n < 1){
            throw new RuntimeException();
        }
        if (n == 1 || n == 2){
            return n;
        }
        int sum = 0;
        int one = 1;//走一步
        int tow = 2;//走两步

        for (int i = 3; i <= n; i++) {
            sum = tow + one;
            one = tow;//退两步(3退2 走1步)
            tow = sum;//退一步（3退1 走两步）
        }
        return sum;
    }

    /**
     *  考察了 值传递机制，局部变量，成员实例变量，成员类变量，类初始化顺序,变量的作用域
     */
    @Test
    public void test6(){
        //静态的变量 为类变量。所有实例共享。
        //实例成员变量 为实例独有一份。
        //非静态代码块中，有声明变量且同名时，考虑就近原则只会使用局部变量。不会使用成员变量。
        //方法的形参列表，也是同名变量时，没有特别强调则就近原则。不影响成员变量。
        //初始化顺序：静态优先，成员变量 与 代码块 平等按上下顺序，构造器最后。
        /*
            一、局部变量与成员变量的区别：
                1.声明位置
                    局部变量声明在：方法体{}，形参，代码块{}中
                    成员变量声明在：类中 方法外。
                        类变量：有static修饰
                        实例变量：没static修饰
                2.修饰符
                    1.局部变量只能final修饰
                    2.成员变量能：public ，protected，private,final,static,volatile,transient
                3.值存储位置
                    1.局部变量：栈
                    2.类变量：方法区
                    3.实例变量：堆
                4.作用域
                    1.局部变量：从声明开始处，到所属的 } 右大括号结束
                    2.类变量：当前类中 类名.xx（类名可省），其他类中，类名，对象名都能调用。
                    3.实例变量：当前类中 this.xx（this可省），其他类中，对象名调用。
                    注：类名，this可省是在没有同名变量的情况下。如果有同名需要注明。一般开发不会同名。
                5.声明周期
                    1.局部变量：每个线程，每一次调用执行都是新的生命周期
                    2.类变量：跟随着类的生命周期，该类所有对象共享。
                    3.实例变量：跟随着实例对象的生命周期，每一个对象独有一份。
                 6.默认值
                     1.局部变量没有默认值
                     2.成员变量有默认值
            二、JVM虚拟机内存模型：
                方法区：用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据
                堆：存放对象实例。（对象实例和数组）
                虚拟机栈：存储局部变量表等。局部变量表存放了编译期各种基本数据类型、对象引用。执行完成自动释放。
                本地方法栈：
                程序计数器：
                其中：方法区、堆 所有线程共享数据。其余独立线程私有。

         */
    }

    /**
     * Spring bean 作用域之间有什么区别？
     * spring 事务传播行为 和 事务隔离级别
     */
    @Test
    public void test7(){
        /*
            1.什么是java bean？
                java bean从概念来说就是java对象实体，用于代表该类代理对象。一般用于被springioc容器进行管理使用。
             2.bean作用域之间有什么区别？
                要修改bean作用域使用scope属性来指定bean的作用域。
                bean有singleton单例模式，prototype多例模式，request请求，session会话四种作用域
                其中Singlton是默认值，当IOC容器一创建就会创建bean实例，而且只创建一个。以单实例方式存在。调用getBean或者bean引用都会返回这个实例。
                prototype创建IOC容器时不会创建bean实例，而是在调用getBean时才会创建bean实例。每调用一次都会新创建一个实例。所以为多例
                request每次HTTP请求都会创建一个新的Bean，该作用域仅适用于Web环境。WebApplicationContext环境。
                session同一个HTTP会话共享一个Bean，不同会话之间使用不同的Bean。同上也是该作用域仅适用于Web环境。WebApplicationContext环境。
         */
        /*
            1.事务传播行为：就是事务嵌套问题。
            例：一个方法开启事务，该方法中调用其他方法同样也开启事务且包含完整。那么一旦出现异常是执行哪个程度的事务回滚。这个就是事务传播行为要考虑问题。
                事务传播行为通过Transactional中的 propagation属性进行设置。
                事务传播行为有7种，但常用的REQUIRED、REQUIRED_NEW这两种。REQUIRED为传播行为默认值。
                REQUIRED是执行最外层事务，一旦内部事务出现异常则整个方法进行回滚。即事务向最外层传播。
                REQUIRED_NEW则是在该层级多次执行，且该方法还处于事务中，则新开一个新事务。也就是该层级的将拦截其他事务向上传播。只在该层级传播。
                多个事务之间是隔离的。只要该层级不是所有的事务都异常则不会影响上层事务。可能出现执行部分成功的情况。

            2.事务隔离级别：事务之间读取数据库时，考虑并发问题会出现一些数据不同步问题。
                Transactional中的 isolation属性进行设置。
                    事务隔离级别有4种：读未提交，读已提交，可重复读，串行化。
                    数据不同步问题有3种：脏读，不可重复读，幻读。
                        读未提交，会出现 脏读，不可重复读，幻读。
                        读已提交，会出现 不可重复读，幻读 （Oracle默认值）
                        可重复读，会出现 幻读 （MYSQL默认值，且MySQL利用技术解决在可重复读隔离级别的幻读问题）
                        串行化，但性能不好，因为会把整个表进行加锁。导致其他方法只能等待。
         */
    }

    /**
     * SpringMVC web网页请求参数中带中文后台解析是乱码的时候，怎么处理？
     * SpringMVC 简单谈一下工作流程。
     */
    @Test
    public void test8(){
        /*
            1.一般这种情况是双方没有沟通好使用什么字符集进行通信。
            这时先确定好字符集，例如多数情况使用UTF-8字符集来解析中文。
            那么在SpringMVC的配置文件中配置一个过滤器CharacterEncodingFilter 该过滤器中的属性encoding，forceEncoding控制请求、响应的字符集编码
            配置时添加一个过滤器Filter标签，再进行成员变量初始化。encoding 为UTF-8 ，forceEncoding 为true。
            注：上面是用于Post请求的字符集设置。
            2.如果是get请求，则需要在tomcat服务器的配置文件server.xml中修改第一个connector 添加URIEncoding=UTF-8
         */
        /*
            SpringMVC 简单谈一下工作流程：
                SpringMVC核心是DispatchServlet，HandleMapping，HandlerAdapter
                用户发送请求——DispatchServlet（中央控制器）——HandlerMapping（处理器映射器）——（返回所有Handler，处理器拦截器）Dis（中央控制器）
                ——通过处理器适配器HandlerAdapter获取具体处理器Handler（又称controller）——处理完成后返回ModelAndView对象（即使异常也会返回）
                ——处理器适配器HandlerAdapter再将ModelAndView返回给DispatchServlet——中央处理器再调用 视图解析器 解析得到view
                ——再通过视图渲染技术（要么是thymeleaf，要么是其他）渲染view——响应给用户
         */
    }

    /**
     * MyBatis 中当实体类中的属性名和表中的字段名不一样，怎么办？
     */
    /*
        1.首先确定映射的类的属性名与数据库表字段名的命名规则。映射的类属性名使用驼峰法，数据库使用下划线法。
            则在MyBaits配置文件中开启settings标签中的 setting name=“mapUnderscoreToCamelCase” value=true 即可。

        2.如果是属性名和字段名存在不同，并非命名规则时。
            1.要么在查数据库的SQL中 起字段别名与类属性名保持一致。
            2.要么在Mapper映射配置文件中，设置resultMap type=要映射的全类名， id=随便设一个唯一id
                id标签映射主键，其余result标签映射字段名和类属性名。

        总结：
            1.是命名规则引起的问题驼峰和下划线，则开启MyBatis全局配置开启驼峰命名规则。
            2.写SQL语句时起别名
            3.编写映射配置文件Mapper中的resultMap来自定义映射规则
     */

    /**
     * linux系统，常用服务类相关命令？
     *
     */

    /*
        1.首先确定linux系统：是centos 还是其他。
        以centos为例：centos6 ， centos7服务类命令存在差异。
        2.注册在系统中的标准化程序：方便统一的管理方式（常用）centos6
            service 服务名 start
            service 服务名 stop
            .. .. restart
            .. .. reload
            .. .. status
            例：service network status
        3.查看服务的方法 /etc/init.d 服务名
        4.通过chkconfig 命令设置自启动
            查看服务chkconfig --list|grep xxx
            例：查看服务 chkconfig --list
            例：设置服务运行级别 chkconfig --level 5 服务名 on

        5.运行级别runlevel
            开机，BIOS，/boot，init进程，运行级别，运行级对应的服务
        6.查看默认级别：vi/etc/inittab
        7.Linux 系统有7种运行级别（runlevel）：常用的是级别3和5
            0：系统停机状态，系统默认运行级别不能设为0，否则不能正常启动
            1：单用户工作状态，root权限，用于系统维护，禁止远程登录（不支持网络）
            2：多用户状态（没有NFS），不支持网络
            3：完全多用户状态（有NFS（网络）），登录后进入控制台命令行模式
            4：系统未使用（保留）
            5：X11控制台，登录后进入图形GUI模式
            6：系统正常关闭并重启，默认运行级别不能设置为6，否则不能正常启动
     */
    /*
        1.注册在系统中的标准化程序：方便统一的管理方式（常用）centos7
            systemctl start 服务名(xxxx.service)
            systemctl restart 服务名(xxxx.service)
            systemctl stop 服务名(xxxx.service)
            .. reload 服务名
            .. status 服务名
            例：systemctl status network
               systemctl status firewalld 防火墙

        2.查看服务的方法 /usr/lib/systmed/system
        3.查看服务命令
            systemctl list-unit-files |grep 服务名 （|grep 筛选服务）
            systemctl --type service

        4.设置服务自启动
            systemctl enable 服务名  （开启自启动）
            systemctl disable 服务名 （关闭自启动）
     */

    /**
     * git分支相关命令，实际应用
     */
    /*
        一、git分支相关命令
            1.创建分支
                git branch <分支名>
                git branch -v 查看分支

            2.切换分支
                git checkout <分支名>
                git checkout -b <分支名> //该命令先检查是否存在分支，不存在则直接创建并切换分支。所以创建新分支并编辑时使用该命令更方便

            3.合并分支
                先切换到要合并的分支 git checkout <分支名>
                再进行合并 git merge <分支名> //<分支名>该分支名是要合并过来的分支。

            4.删除分支
                先切换到其他分支 git checkout <分支名>
                再删除分支 git branch -D <分支名>

        二、实际应用（git工作流）（现在的github默认主分支已经变成main）
            1.但git的版本还是master主分支，在创建仓库时注意。最好在一开始就修改默认主分支
            2.项目使用git开发工作流程
                1.main主分支 基础上创建开发分支develop 且和main分支保持版本一致。
                2.如果出现bug 在main分支基础上 创建 热修分支hotfix，测试完成后合并到main主分支。再同步到develop分支
                3.其他组员开发一个模块在其他分支，开发完成。创建一个测试分支 release.xx分支。测试完成合并main。同时合并develop

                总体逻辑：保证线上运行的main分支始终保证正常服务。其余分支根据情况创建对应意义的分支即可。
     */

    /**
     * Redis持久化有几种类型，他们的区别
     * 讲区别其实就是讲每个的优缺点即可。
     */
    /*
        1.Redis持久化有两只类型：
            1.RDB （Redis DataBase）
            2.AOF （Append Of File）

        2.RDB
            1.概念：
                在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是业内人说的 快照Snapshot。
                它恢复时是将快照文件直接读到内存中。
            2.备份如何执行：
                使用RDB，Redis会单点创建（fork）一个子进程来进行持久化，
                会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。
                整个过程中，主进程是不进行任何IO操作的。这确保极高的性能。
            3.恢复备份：
                如果进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加高效。
                RDB的缺点是最后一次持久化后的数据可能丢失。
                （RDB是数据层面的直接恢复，AOF是日志级别的逐一执行命令恢复）
            4.RDB的备份
                先通过config get dir 查询rdb文件的目录
                将*.rdb的文件拷贝到别的地方
            5.RDB的恢复
                关闭Redis
                先吧备份的文件拷贝到工作目录下
                启动Redis，备份数据会直接加载
            6.RDB的优点
                节省磁盘空间
                恢复速度块
            7.RDB的缺点
                虽然Redis在fork时使用写时拷贝技术，但是如果数据庞大时还是比较消耗性能。
                RDB有一个备份窗口，在备份周期间隔时间，一旦宕机会丢失最后一次的快照的修改。

        3.AOF
            1.概念：
               以日志的形式来记录每个写操作，将Redis执行过的所有指令记录下来日志文件中。（读操作不记录）
               只能追加日志，不能改写已记录日志。（备份方式）
               恢复数据时，读取日志文件的操作命令重新执行构建数据。（即：按照日志从上到下依次执行）
            2.AOF的优点
                备份机制更稳建，丢失数据概率更低
                可读日志文本，通过操作AOF可以处理误操作
            3.AOF的缺点
                比起RDB占用更多的磁盘空指
                恢复备份速度慢（毕竟要一条日志命令执行）
                每次写都同步的话，有一定的性能压力
                存在bug造成不能恢复（这个Redis目前本身问题）
     */

    /**
     * MySQL什么时候适合建索引，什么时候不适合建索引。
     *
     */
    /*
        1.什么是索引？
            MySQL官方对索引的定义为：索引index 是帮助MySQL 高效获取数据的数据结构。
            可以得到索引的本质：索引是数据结构
        2.优势
            1.类似图使馆建索引，提高数据检索的效率，降低数据库IO成本
            2.通过索引列 对数据进行排序，降低数据排序的成本，降低了CPU的消耗

        3.劣势
            1.降低更新表的速度（插入insert，更新update，删除delete）
            因为索引本质是数据结构，要想其中加入数据自然麻烦。
            2.实际上索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，索引列表是占用空间。

        4.根据以上信息确定什么时候适合创建索引。
            1.主键自动建立唯一索引
            2.频繁查询的字段，加索引
            3.多表查询的对应的字段，加索引
            4.确定要创建索引：是单个索引，还是组合索引？组合索引性价比更高
            5.该字段会排序，加索引
            6.统计或者分组的字段，加索引（分组的时候，内部会做一次排序。故加索引会加快分组速度）

        5.什么时候不适合创建索引
            1.表记录太少（几百条-几千条）（效果不明显，没必要）
            2.经常修改的字段或者表，不加
            3.不会进行过滤的字段Where，不加
            4.Where过滤性不好的字段，不加（例：男女这种字段，过滤只能过一半。反正就是过滤效果不好的字段）
     */

    /**
     * JVM垃圾回收机制，GC发生在JVM哪部分，有几种GC，它们的算法是什么
     *
     */
    /*
        1.GC发生在JVM的heap堆空间中，有两种GC：Minor GC ，Full GC
        GC是什么（分代收集算法）：
            次数上频繁收集Young区（新生代区）Minor GC
            次数上较少收集Old区 Full GC
            永久区Perm 没有GC （基本不动）

        2.GC算法总体概述：
            有4种算法：
                1.引用计数法
                    已不再采用。
                    缺点：
                        每次对象赋值都要维护计数器，且计数器本身也有性能消耗
                        较难处理循环引用
                2.复制算法（Copying） （Minor GC 所使用的算法）
                    从内存中读取存活对象，将其拷贝到另一个内存空间。
                    优点：
                        1.没有标记和清除的过程，效率高
                        2.没有内存碎片，可用bump-the-pointer实现快速内存分配
                    缺点：需要双倍空间进行复制腾挪

                3.标记清除算法（Mark-Sweep）（Full GC 会使用）
                    从根集合开始扫描，对存活对象进行标记。
                    再进行二次扫描将没标记的对象进行清除。（没有被回收的区域使用free-list记录该区域）
                    优点：不需要额外空间
                    缺点：
                        1.要两次扫描（2n），耗时间
                        2.会产生内存碎片

                4.标记压缩算法（Mark-Compact）（Full GC 会使用）
                    从根集合开始扫描，对存活对象进行标记。
                    再进行二次扫描将没标记的对象进行清除。
                    再进行压缩将存活对象移动到一起，腾出连续空间。
                    优点：没有内存碎片（可用bump-the-pointer），不需要额外空间
                    缺点：
                        1.需要移动对象成本
                        2.要两次扫描（2n），耗时间

                5.标记清除压缩算法（Mark-Sweep-Compact）（Full GC 默认使用）
                    集合了标记清除和标记压缩的优点，达到性能，空间利用的平衡。
                    不需要每次清除后都压缩，而是一个适合的时机再进行压缩。
                    优点：
                        1.没有内存碎片（可用bump-the-pointer），不需要额外空间

     */
}
