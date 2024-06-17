# Utils

包含一些常用工具类
[GitHub仓库地址](https://github.com/DL-ZhangTeng/Utils)

## 引入

### gradle

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

implementation 'com.github.DL-ZhangTeng:Utils:2.3.3'
```

## 混淆

-keep public class com.zhangteng.**.*{ *; }

## 历史版本

| 版本     | 更新                                    | 更新时间                |
|--------|---------------------------------------|---------------------|
| v2.3.3 | 更新日期工具类                               | 2024/6/17 at 11:19  |
| v2.3.2 | 线程池工具增加泛型                             | 2024/5/29 at 17:01  |
| v2.3.1 | IException增加常见异常                      | 2023/9/8 at 14:30   |
| v2.3.0 | StateViewHelper重试&取消回调支持多个监听器         | 2023/5/11 at 10:40  |
| v2.2.1 | ViewBindingUtils移除Fragment反射设置mView功能 | 2023/5/4 at 18:00   |
| v2.2.0 | ViewBindingUtils重写inflate与bind分开      | 2023/4/5 at 21:34   |
| v2.1.2 | 加载中文案显示处理&相册刷新                        | 2023/4/5 at 21:34   |
| v2.1.1 | handleException过滤IException           | 2023/2/3 at 20:11   |
| v2.1.0 | ViewBindingUtils.kt                   | 2023/1/9 at 00:08   |
| v2.0.3 | 添加解压与读取本地json工具类                      | 2022/11/29 at 11:34 |
| v2.0.2 | 添加文件切割工具                              | 2022/11/7 at 17:34  |
| v2.0.1 | BitmapUtils.kt增加压缩图片方法、获取view的截图      | 2022/10/26 at 11:13 |
| v2.0.0 | 增加加载中显示抽象接口ILoadingView               | 2022/9/14 at 21:13  |
| v1.0.0 | 常用工具从BaseLibrary独立出来                  | 2022/9/2 at 20:00   |

## 赞赏

如果您喜欢BaseLibrary，或感觉BaseLibrary帮助到了您，可以点右上角“Star”支持一下，您的支持就是我的动力，谢谢

## 联系我

邮箱：763263311@qq.com/ztxiaoran@foxmail.com

## License

Copyright (c) [2020] [Swing]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
