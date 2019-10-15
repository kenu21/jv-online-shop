# Inernet-shop
![Internet-shop](/images/internetShop.jpg)

# Table of Contents
* [Project purpose](#purpose)
* [Project structure](#structure)
* [For developer](#developer-start)
* [Authors](#authors)

# <a name="purpose"></a>Project purpose
This App can be used as a basis for creating your own online store.
<hr>
This web application has forms for registering new users, authentication.

There are also controllers for working with items, users, orders and buckets.
<hr>
Inject - inject mock data.

Registration - able to register new user.

Login - user authentication and authorization.

Users - show all users. Available only for ADMIN role.

Items - show all items.

Bucket - show bucket for a current user. Available only for USER role.

Orders - show all orders for a current user. Available only for USER role.

Logout - for log out.
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

Open this project in your IDE.

Add it as maven project.

Configure Tomcat:
* add artifact;
* add sdk 11.0.3

Add sdk 11.0.3 in project strukture.

You need sql database.

Create schema "dbinternetshop".

Use file interntetshop.src.main.java.resources.init_db.sql for create all needed tables.

In interntetshop.src.main.java.mate.academy.internetshop.Factory class you have to change user and password for you DB.

In interntetshop.src.main.java.resources.hibernate.cfg.xml you have to change user and password for you DB too.

You have to change path in interntetshop.src.main.java.resources.log4j.properties for your logFile.

You can run this project.

By default you have user with role USER (login = 1, password = 1) and user with role ADMIN (login = 2, password = 2). 
<hr>

# <a name="authors"></a>Authors
* [Iurii](https://github.com/kenu21)
