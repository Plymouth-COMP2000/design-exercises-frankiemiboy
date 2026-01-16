# COMP2000 - Restaurant Manager Application #

This repository holds the source code for my COMP2000 project. 
The aim of the project is to create a mobile application that helps manage restaurant operations more efficiently. As stated in the coursework briefing:

> The app will have two user types: Staff and Guest. The staff side will allow restaurant employees to manage menu items and reservations, while the guest side will enable customers to browse the menu and make table reservations. 

> Both staff and guests must log in to access the application’s main features. The application will interact with a RESTful API that provides secure access to the database of the app users via standard HTTP methods. Menu and reservations should be managed locally on the device using local database (preferably SQLite)

The source code fulfills the requirements. All, excluding the notifications section, however, this will be something I plan on correcting in the future after the coursework assessment stage is complete.

## To use the application, you must satisfy the following conditions: ##

### 1. Users must be registered in order to be authenticated. ###
Authentication makes use of the RESTful API that provides secure access to the database of the **already existing** app users. Therefore, if you do not have an account already set up, you would need to create one. Creating an account is very simple, and there will be an option to register/sign up at the bottom of the login page when you start up the application. 

However, to test out the staff features, you would need to use the any one of the following credentials:

| First Name | Last Name | Email | Username | Password |
| --- | --- | --- | --- | --- |
| Jermaine | Yehman | jermaine.yehman@email.com | jermaine_yehman | nothingButNetttt |
| Linus | Torvalds | linus.linux@email.com | linus_torvalds | penguinPower91 |
| Ada | Lovelace | ada.lovelace@email.com | ada_lovelace | firstProgrammer1843 |



### 2. You must be on the University Network to access the user database through the RESTful API ###
There are two ways of satisfying this requirement. Either you use one of the University's Lab Desktops on campus, or you make use of the VPN service. Users and members of the University will know how to configure their VPN