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
