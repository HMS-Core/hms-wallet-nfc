# HMS Wallet NFC Demo

## table of Contents

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
	
## License
HMS wallet server sample code is licensed under the [Apache License, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
