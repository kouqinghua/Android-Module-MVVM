开发使用流程：
1. 在 module 中创建对应模块，例如登录，主页，首页，我的...
2. 创建模块后修改build.gradle文件，替换为 "apply from: '../../config_module.gradle'"，复制后需要将双引号去掉，可以在对应模块中自定义引入依赖等
3. 第二步完成后不要着急 sync ，此时会报错。将 App 和xxxApplication 拷贝到包名目录下
4. 创建 di 文件目录并创建 module 文件，ViewModel 在该文件中注入即可
5. 创建 activity 目录，创建一个 Activity，并修改AndroidManifest.xml（库类型）文件，一下代码为基础模板
    <?xml version="1.0" encoding="utf-8"?>
    <manifest package="com.module.xxx">
    </manifest>
6. 分模块启动时，需要在main 目录下创建manifest目录，并创建AndroidManifest.xml（应用类型），类似 app 模块清单文件
7. 可以sync通过了，通过后可以启动应用了（只是可以启动，模块化还未配置完）。
8. 在 lib 下 common 模块中的 router/service目录下创建对应模块的服务，同时需要在 RoutePath 文件中配置对应模块的属性（ARouter配置）
  在 xxxWrap中定义跳转页面，传参等逻辑
9. 在对应模块包目录下创建service目录并创建第 8 步中创建 service的xxxServiceImpl实现类
10. 在config.gradle文件中modules下添加对应模块名并sync。此时运行即可后续开发。
