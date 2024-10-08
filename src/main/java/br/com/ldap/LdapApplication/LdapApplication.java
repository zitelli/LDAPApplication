package br.com.ldap.LdapApplication;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LdapApplication {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LdapApplication.class, args);
        LdapRepository ldapOperations = context.getBean(LdapRepository.class);
        //Person pessoa = ldapOperations.findPerson("uid=test");
        Person pessoa = new Person("Funcionarios", "francisco", "paulo");
//Ok    ldapOperations.createPersonIfParentExists(pessoa);
//Ok    ldapOperations.create(pessoa);
//Ok    ldapOperations.update(pessoa); 
//Ok    ldapOperations.delete(pessoa); 
//Ok    List<Person> pessoa = ldapOperations.findAll();
//Ok    Person pessoa = ldapOperations.findPerson("cn=Administrators,ou=groups");
        System.out.println("Found users: " + pessoa.toString());        
	}

}
