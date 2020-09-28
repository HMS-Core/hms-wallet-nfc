# HMS Wallet NFC Demo

## Table of Contents

* [Introduction](#introduction)
* [Installation](#installation)
* [Supported Environment](#supported-environment)
* [Sample Code](#sample-code)
* [License](#license)

## Introduction
This is sample code on how to use WalletKit NFC capabilities. You can realize the function of NFC card swiping ability through sample code.

## Installation
Before running the demo code, you should have installed Java and Maven.

## Supported environment
Oracle Java 1.8 is recommended.

## Sample code
PassesController is a unified entry class, which defines the interfaces that need to be implemented.

1. Registration interface
You can register and obtain the three-party authentication and authorization certificate by calling the register interface.
	
2. Apply for a personalized token
You can implement the requestToken method to apply for a personalized token.
	
3. Get personalized data
You can transfer personalized data through the getPersonalInfo interface.
	
4. Unregister
When the wallet deletes the card, it will call this interface to inform you that the card has been deleted.

## Question or issues
If you want to evaluate more about HMS Core,
[r/HMSCore on Reddit](https://www.reddit.com/r/HuaweiDevelopers/) is for you to keep up with latest news about HMS Core, and to exchange insights with other developers.

If you have questions about how to use HMS samples, try the following options:
- [Stack Overflow](https://stackoverflow.com/questions/tagged/huawei-mobile-services) is the best place for any programming questions. Be sure to tag your question with 
`huawei-mobile-services`.
- [Huawei Developer Forum](https://forums.developer.huawei.com/forumPortal/en/home?fid=0101187876626530001) HMS Core Module is great for general questions, or seeking recommendations and opinions.

If you run into a bug in our samples, please submit an [issue](https://github.com/HMS-Core/hms-wallet-nfc/issues) to the Repository. Even better you can submit a [Pull Request](https://github.com/HMS-Core/hms-wallet-nfc/pulls) with a fix.

## License
HMS wallet server sample code is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
