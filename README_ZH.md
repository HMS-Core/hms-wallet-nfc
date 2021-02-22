# 华为钱包服务NFC示例代码

## 目录

* [简介](#简介)
* [安装](#安装)
* [环境要求](#环境要求)
* [示例代码](#示例代码)
* [授权许可](#授权许可)

## 简介
本示例代码展示如何使用华为钱包服务（HUAWEI Wallet Kit）NFC刷卡能力。

## 安装
运行示例代码前，您需安装Java和Maven。

## 环境要求
建议您使用Oracle Java 1.8。

## 示例代码
PassesController为统一入口类，定义了需要实现的接口。

1. 注册接口
调用注册接口注册、获取三方鉴权及授权证书。
	
2. 申请个性化token
调用requestToken方法申请个性化token。
	
3. 获取个性化数据
调用getPersonalInfo接口获取个性化数据。
	
4. 取消注册
用户删除卡券时，华为钱包将调用取消注册接口通知您卡券已删除。

## 技术支持
如果您对HMS Core还处于评估阶段，可在[Reddit社区](https://www.reddit.com/r/HuaweiDevelopers/)获取关于HMS Core的最新讯息，并与其他开发者交流见解。

如果您对使用HMS示例代码有疑问，请尝试：
- 开发过程遇到问题上[Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services)，在`huawei-mobile-services`标签下提问，有华为研发专家在线一对一解决您的问题。
- 到[华为开发者论坛](https://developer.huawei.com/consumer/cn/forum/blockdisplay?fid=18) HMS Core板块与其他开发者进行交流。

如果您在尝试示例代码中遇到问题，请向仓库提交[issue](https://github.com/HMS-Core/hms-wallet-nfc/issues)，也欢迎您提交[Pull Request](https://github.com/HMS-Core/hms-wallet-nfc/pulls)。

## 授权许可
华为钱包服务NFC刷卡能力集成示例代码经过[Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0)授权许可。
