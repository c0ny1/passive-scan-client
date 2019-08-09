# Passive Scan Client | Burp被动扫描流量转发插件


## 简介

```
Q1: 将浏览器代理到被动扫描器上，访问网站变慢，甚至有时被封ip，这该怎么办？
Q2: 需要人工渗透的同时后台进行被动扫描改，到底是代理到burp还是被动扫描器？
```

如果你遇到过这两个问题，那么该插件是你要寻找的东西！

插件将正常访问网站的流量与提交给被动扫描器的流量单独分开了，互不影响。

![流程图](./doc/process.png)

## 演示

可以通过插件将流量转发到各种被动式扫描器中，这里我选`xray`来演示.

![动图演示](./doc/show.gif)

## 一些被动式漏洞扫描器
* https://github.com/ysrc/GourdScanV2 ysrc出品的被动式漏洞扫描工具，基于sqlmapapi
* https://github.com/chaitin/xray 由长亭科技出品的一块被动式漏洞扫描器
* https://github.com/boy-hack/w13scan Passive Security Scanner (被动安全扫描器)
* https://github.com/fengxuangit/Fox-scan 基于sqlmapapi的主动和被动资源发现的漏洞扫描工具
* https://github.com/0xbug/SQLiScanner 一款基于sqlmapapi和Charles的被动SQL注入漏洞扫描工具
* https://github.com/zt2/sqli-hunter 基于sqlmapapi，ruby编写的漏洞代理型检测工具
* https://github.com/AttackandDefenceSecurityLab/huimwvs/ 插件转发流量

## 更多

## 浏览器插件 VS Burp插件

前有Govscan，后有xary。我就不浪费时间自己造轮子了。大佬们，为我们铺好了路，还是非常感谢的。

但是依然存在一个问题，那就是"直接设置浏览器代理到被动扫描器影响访问网站的速度"

接下来动手解决这`最后一公里`问题吧。

到底开发的是浏览器插件还是burp插件为好呢？

如果开发浏览器插件，那么岂不是每个浏览器都得开发一遍（），而且手机平台的浏览器更不好开发。

想想我们的工作主要还是在burp上，这样所有的浏览器都代理到burp，由burp插件统一将流量提交到
扫描器上。这样通用型更强写。


而且如果要渗透过程中想手工测试同时被动扫描，那么要做个2层代理。

浏览器>burp>被动扫描器>目标网站

这样不仅访问目标很慢，还很影响burp检测效果！

在当下被动扫描横行，其实细细想来确实能比主动扫描要发现更深的问题。

但是存在一个缺陷，当我同时需要手工渗透与被动扫描同时进行时，我们的浏览器到底是这是burp代理还是被动扫描器代理。
当然了最后就只能浏览器设置burp代理，burp在设置为被动扫描器代理。但是这样的结果很不好

* 网速受限制
* 一旦被动扫描器触发了目标的waf，那么我们将无法在burp中国呢进行测试。

那该怎么解决呢？


当然是把两者独立出来

当然了最好的方法可能是在浏览器上开发插件，不影响正常浏览器的情况下，单独把一份流量发送到被动扫描器上。
无奈看了浏览器的插件开发手册，没有找到相关可以实现的方法，只能先实现一个burp插件把。

https的证书，插件回默认全部信任，大家不用担心证书问题。


## 被动扫描器应该由认证功能

这里也推荐被动扫描器作者应该添加认证，至少我在插件中已经集成了认证功能。可不能让人乱代理到扫描器，不是？


## 参考文章
* [Burpsuit结合SQLMapAPI产生的批量注入插件（X10）](https://www.freebuf.com/articles/web/171622.html)