package io.github.panga.switchyard.jms.security;

import org.apache.camel.Message;
import org.switchyard.component.camel.common.composer.BindingDataCreator;
import org.switchyard.component.camel.common.composer.CamelBindingData;

public class JmsSecurityBindingDataCreator implements BindingDataCreator<CamelBindingData> {

    @Override
    public CamelBindingData createBindingData(Message message) {
        return new JmsSecurityBindingData(message);
    }
}
