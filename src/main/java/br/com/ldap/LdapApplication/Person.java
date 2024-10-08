package br.com.ldap.LdapApplication;

public class Person {
    private String cn;
    private String fullName;
    private String lastName;

    public Person() {}

    public Person(String cn) {
    	this.cn = cn;
    }
    
    public Person(String cn, String fullName, String lastName) {
    	this.cn = cn;
    	this.fullName = fullName;
        this.lastName = lastName;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
        		"cn='" + cn + '\'' +
                ", fullName='" + fullName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}