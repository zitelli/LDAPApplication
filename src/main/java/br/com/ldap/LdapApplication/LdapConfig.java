package br.com.ldap.LdapApplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DefaultTlsDirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.base}")
    private String ldapBase;

    @Value("${ldap.userDn}")
    private String ldapUserDn;

    @Value("${ldap.password}")
    private String ldapPassword;	
	
    @Bean
    public LdapContextSource ldapContextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(ldapUrl); // LDAP server URL
        contextSource.setBase(ldapBase); // Base DN
        contextSource.setUserDn(ldapUserDn); // User DN
        contextSource.setPassword(ldapPassword); // Password
        // Enable TLS for secure connections
//        DefaultTlsDirContextAuthenticationStrategy tlsStrategy = new DefaultTlsDirContextAuthenticationStrategy();
//        contextSource.setAuthenticationStrategy(tlsStrategy);
        contextSource.afterPropertiesSet(); // Initialize the context source
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(ldapContextSource());
    }
}
