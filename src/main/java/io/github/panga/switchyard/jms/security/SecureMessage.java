package io.github.panga.switchyard.jms.security;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import org.switchyard.security.credential.ConfidentialityCredential;
import org.switchyard.security.credential.Credential;
import org.switchyard.security.credential.PrincipalCredential;
import org.switchyard.security.credential.SubjectCredential;

public class SecureMessage implements Serializable {

    private final Serializable originalMessage;
    private final Set<Credential> credentials;

    public SecureMessage(final Serializable message) {
        this.originalMessage = message;
        this.credentials = new HashSet<Credential>();
    }

    public Serializable getOriginalMessage() {
        return originalMessage;
    }

    public Set<Credential> getCredentials() {
        return this.credentials;
    }

    public void addCredentials(final Credential... credentials) {
        this.credentials.addAll(Arrays.asList(credentials));
    }

    public static SecureMessage fromRequest(final HttpServletRequest request, final Serializable message) {
        final SecureMessage secureMessage = new SecureMessage(message);

        if (request.isSecure()) {
            secureMessage.addCredentials(new ConfidentialityCredential(request.isSecure()));
        }
        if (request.getUserPrincipal() != null) {
            secureMessage.addCredentials(new PrincipalCredential(request.getUserPrincipal(), true));
        }
        final Subject subject;
        try {
            subject = (Subject) PolicyContext.getContext("javax.security.auth.Subject.container");
            if (subject != null) {
                secureMessage.addCredentials(new SubjectCredential(subject));
            }
        } catch (PolicyContextException ex) {
            throw new SecurityException(ex);
        }

        return secureMessage;
    }

}
