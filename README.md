# LDAPAplication

GRUD functions with connections to the LDAP Server (LDATemplate), in Docker, via port 10389.

## Note:
   In this project, Java 17 and Maven 3.6.3 were used.
   There is a need to create the config.properties file in src/main/resources/ 
   with the values ​​of the variables below:


key      = ..code for decryption..

password = ..server password..

url      = ldaps:// ..ip server..

userCpf  = ..information to be searched..

 
### GitHub reference of LDAP:

	https://github.com/talenteddeveloper/Spring-Boot-LDAP-Overview
	https://github.com/talenteddeveloper/code_collection/tree/master
	
### Commands Docker:	

	docker pull dwimberger/ldap-ad-it
	docker run -it --rm -p 10389:10389 dwimberger/ldap-ad-it
	
### Youtube videos:	

	https://youtu.be/0FwOcZNjjQA?si=sie8Zimu6siRZGNq
	https://youtu.be/-6fAr13j1AM?si=YnVI5rL49soHHCWN
	https://youtu.be/-wDUChgvYgU?si=PA2o3TzdKxJXsG_H
    