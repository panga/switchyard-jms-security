# SwitchYard JMS Security

[SWITCHYARD-2172](https://issues.jboss.org/browse/SWITCHYARD-2172) - Propagate Security Context to JMS bindings POC

This library propagates security context to SwitchYard JMS bindings.

The helper method ```SecureMessage.fromRequest``` creates the following credentials: PrincipalCredential, ConfidentialityCredential and SubjectCredential based on JAAS and JACC contexts.

* Build

```mvn clean install```

* Maven

```xml
<dependency>
    <groupId>io.github.panga</groupId>
    <artifactId>switchyard-jms-security</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

* Usage (WildFly)

```java
@ApplicationScoped
public class QueueSender {

    @Inject
    private JMSContext jmsContext;

    @Inject
    private HttpServletRequest servletRequest;

    @Resource(mappedName = "java:/jms/Queue")
    private Queue destination;

    public void sendToQueue(final Serializable object) {
        final SecureMessage message = SecureMessage.fromRequest(servletRequest, object);
        jmsContext.createProducer().send(destination, message);
    }
}
```
