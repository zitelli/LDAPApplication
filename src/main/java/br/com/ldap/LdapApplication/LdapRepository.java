package br.com.ldap.LdapApplication;

import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class LdapRepository {

    @Autowired
    private LdapTemplate ldapTemplate;

    // Method to find a person by their DN
	@SuppressWarnings("unchecked")
	public Person findPerson(String dn) {
        return (Person) ldapTemplate.lookup(dn, new PersonAttributesMapper());
    }
    
    // Inner class to map LDAP attributes to Person object
	@SuppressWarnings("rawtypes")
	private class PersonAttributesMapper implements AttributesMapper {
        @Override
        public Person mapFromAttributes(Attributes attrs) throws NamingException {
            Person person = new Person();
            person.setCn((String) attrs.get("cn").get());
            person.setFullName((String) attrs.get("uniqueMember").get());
            return person;
        }
    }    
     
    public List<Person> findAll() {
        return ldapTemplate.search(
                "ou=groups", // filter for persons
                "cn=Administrators", // filter for persons
                (Attributes attrs) -> {
                    String cn = (String) attrs.get("cn").get();
                    String fullName = "";
                    String lastName = (String) attrs.get("uniqueMember").get();
                    return new Person(cn, fullName, lastName);
                });
    }

    
//  public List<Person> findAll() {
//      return ldapTemplate.search(
//          "ou=people", // base DN
//          "(objectClass=person)", // filter
//          (Attributes attrs) -> {
//              String fullName = (String) attrs.get("cn").get();
//              String lastName = (String) attrs.get("sn").get();
//              return new Person(fullName, lastName);
//          }
//      );
//  }    
    
    public void create(Person person) {
        Name dn = LdapNameBuilder.newInstance("cn=" + person.getCn() + ",ou=groups")
      //                            .add("fullName", person.getFullName())
  //                                .add("cn", person.getCn())
                //                  .add("sn", person.getLastName())
                                  .build();
        Attributes attrs = buildAttributes(person);
        ldapTemplate.bind(dn, null, attrs);
    }
    
    
    public void createPersonIfParentExists(Person person) {
        // Check if parent exists (e.g., ou=system)
        Name parentDn = LdapNameBuilder.newInstance("ou=groups")
                                       .build();

        if (ldapTemplate.lookup(parentDn) != null) {
            // Parent exists; proceed to create the person
            Name dn = LdapNameBuilder.newInstance(parentDn)
            						  .add("cn", person.getCn())
                                      .build();
            Attributes attrs = buildAttributes(person);
            ldapTemplate.bind(dn, null, attrs);
        } else {
            System.out.println("Parent OU does not exist. Please create it first.");
        }
    }
    
    public void update(Person person) {
        Name dn = LdapNameBuilder.newInstance("ou=groups")
                                  .add("cn", person.getCn())
                                  .build();

        ModificationItem[] mods = new ModificationItem[2];
//        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, 
//                                       new BasicAttribute("cn", person.getFullName()));
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, 
                                       new BasicAttribute("ou", person.getFullName()));
        mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, 
                new BasicAttribute("o", person.getLastName()));        
        ldapTemplate.modifyAttributes(dn, mods);
    }
    
    public void delete(Person person) {
        Name dn = LdapNameBuilder.newInstance("cn=" + person.getCn() + ",ou=groups")
                 //                 .add("cn", person.getFullName())
                                  .build();
        ldapTemplate.unbind(dn);
    }
    
    private Attributes buildAttributes(Person p) {
        Attributes attrs = new BasicAttributes();
        
        // Define the object classes for the entry
        BasicAttribute ocAttr = new BasicAttribute("objectClass");
//        ocAttr.add("top");
        ocAttr.add("groupOfUniqueNames");
        
        // Add the object class attribute to the attributes set
        attrs.put(ocAttr);
        
        // Add other attributes for the person
//        attrs.put("ou", "users"); // Organizational Unit
//        attrs.put("objectClass", "top"); // Common Name
//        attrs.put("sn", p.getLastName()); // Surname
        attrs.put("uniqueMember","0.9.2342.19200300.100.1.1=admin,2.5.4.11=system");
        return attrs;
    }
    
    
}