# Internet-shop
![Internet-shop](/images/internetShop.jpg)

# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Authors](#authors)

# <a name="purpose"></a>Project purpose
This is a template for creating an e-store.
<hr>
It has login and registration forms.

There are controllers for working with items, users, orders and buckets:
<hr>
Inject - for injection mock data,

Registration - for registering new users,

Login -  for user authentication and authorization,

Users - for displaying a list of all app users. Available for users with an ADMIN role only,

Items - for displaying  all items in stock,

Bucket - for displaying  user’s bucket. Available for users with a USER role only,

Orders - for displaying user’s order history. Available for users with a USER role only,

Logout - for logging out.
<hr>

# <a name="structure"></a>Project Structure
* Java 11
* Maven 4.0.0
* hibernate 5.4.5.Final
* javax.servlet 3.1.0
* jstl 1.2
* log4j 1.2.17
* maven-checkstyle-plugin
<hr>

# <a name="developer-start"></a>For developer

Open the project in your IDE.

Add it as maven project.

Configure Tomcat:
* add artifact;
* add sdk 11.0.3

Add sdk 11.0.3 in project struсture.

Create a schema "dbinternetshop" in any SQL database.

Use file interntetshop.src.main.java.resources.init_db.sql to create all the tables required by this app.

At interntetshop.src.main.java.Factory class use username and password for your DB to create a Connection.

At interntetshop.src.main.java.resources.hibernate.cfg.xml configure Hibernate with correct username and password.

Change a path in interntetshop.src.main.java.resources.log4j.properties. It has to reach your logFile.

Run the project.

By default there’s one user with a USER role (login = 1, password = 1) and one – with an ADMIN role (login = 2, password = 2). 
<hr>

# <a name="authors"></a>Authors
* [Iurii](https://github.com/kenu21)
