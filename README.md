[![Maven Central](https://maven-badges.herokuapp.com/maven-central/name.valery1707.smsc/smsc/badge.svg)](https://maven-badges.herokuapp.com/maven-central/name.valery1707.smsc/smsc)
[![Javadoc](https://javadoc.io/badge/name.valery1707.smsc/smsc.svg)](http://www.javadoc.io/doc/name.valery1707.smsc/smsc)
[![License](https://img.shields.io/github/license/valery1707/smsc-java.svg)](http://opensource.org/licenses/MIT)

[![Stories to work on](https://badge.waffle.io/valery1707/smsc-java.svg?label=next&title=Ready%20to%20work)](https://waffle.io/valery1707/smsc-java)
[![DevOps By Rultor.com](http://www.rultor.com/b/valery1707/sms-java)](http://www.rultor.com/p/valery1707/sms-java)

# smsc-java
Java library for [SMSC](https://smsc.ru/api/http/).

# Features


# How to use

First, connect the libraries `smsc`, `smsc-http-ok-http` and `smsc-json-jackson` to the project and then:

```java
SmsCenter center = new SmsCenterImpl(
    new HttpClientOkHttp(),
    new JsonMapperJackson(),
    "demo", "demo1".toCharArray()
);
MessageSend send = center
    .messages()
    .send(new Message()
        .withContact(Contact.phone("79051234567"))
        .withText("SMS text")
);
System.out.println(String.format(
    "Sent %d messages with total cost %.2f, current balance is %.2f",
    send.getCnt(), send.getCost(), send.getBalance()
));
```
