package io.github.panga.switchyard.jms.security;

import java.util.HashSet;
import java.util.Set;
import org.apache.camel.Message;
import org.switchyard.component.camel.common.composer.CamelBindingData;
import org.switchyard.component.common.composer.SecurityBindingData;
import org.switchyard.security.credential.Credential;

public class JmsSecurityBindingData extends CamelBindingData implements SecurityBindingData {

    private final Set<Credential> credentials = new HashSet<Credential>();

    public JmsSecurityBindingData(Message message) {
        super(message);

        if (SecureMessage.class.isInstance(message.getBody())) {
            final SecureMessage secureMessage = message.getBody(SecureMessage.class);
            credentials.addAll(secureMessage.getCredentials());
            message.setBody(secureMessage.getOriginalMessage());
        }
    }

    @Override
    public Set<Credential> extractCredentials() {
        return credentials;
    }

}
