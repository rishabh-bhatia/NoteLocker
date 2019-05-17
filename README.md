# Name
Rishabh Bhatia and Aditya Kushwah

# Student ID
216242343, 217227082

# NoteLocker
A simple and secure note taking Android App which protects user data using a Lock screen. Create unlimited notes for free and keep them secure locally on your device. The app also allows you to add to-do items and remove them once you are done.
This app can be used in many ways like:
- Secure Vault
- Save passwords
- Shopping list
- To-do list
- Store banking or Credit card informations

# Platform
Android
# Link
https://github.com/rishabh-bhatia/NoteLocker

# Major Features
1. Secure data with a Password
2. Create and Manage a to-do list
3. Create and manage notes

# Major Directories
- app/src/main/java/rishabh/notelocker/MainActivity.java
- app/src/main/java/rishabh/notelocker/SplashScreen.java
- app/src/main/java/rishabh/notelocker/ToDo.java
- app/src/main/java/rishabh/notelocker/db/AccessData.java
- app/src/main/java/rishabh/notelocker/db/OpenDatabase.java
- app/src/main/java/rishabh/notelocker/CreatePasswordActivity.java
- app/src/main/java/rishabh/notelocker/EnterPasswordActivity.java
- app/src/main/java/rishabh/notelocker/Notes.java
- src/main/java/rishabh/notelocker/EditNoteActivity.java
- app/src/main/res/layout/activity_notes.xml
- app/src/main/res/layout/activity_edit_note.xml
- app/src/main/res/layout/activity_main.xml
- app/src/main/res/layout/activity_splash_screen.xml
- app/src/main/res/layout/activity_listview.xml
- app/src/main/res/layout/activity_display_note.xml
- app/src/main/res/layout/activity_to_do.xml
- app/src/main/res/layout/todo_item.xml
- app/src/main/res/menu/todo_menu.xml
- app/src/main/res/layout/activity_create_password.xml
- app/src/main/res/layout/activity_enter_password.xml
- app/src/main/res/values/colors.xml
- app/src/main/res/values/strings.xml
- app/src/main/res/values/styles.xml

# Major Classes
SplashScreen.java: This activity opens up a splashscreen displaying the logo and text of the app.

CreatePasswordActivity.java: This activity opens up the first time when app is launched and allows the user to set a numeric password.The password is stored locally and makes the application safe.

EnterPasswordActivity.java: Once the user has set the password, they have to enter correct password everytime they open the app.

MainActivity.java: This activity consists of two buttons which allows the user to open the To-do Activity or Note activity.

ToDo.java: This acitivity allows the user to set To-do tasks and once the tasks are completed, they can mark them as done which will delete the task.

AccessData.java: This activity defines constants that are used to access data in the database. 

OpenDatabase.java: This is a helper class for opening database.

Notes.java: This acitivity displays a listview of all the notes which have been saved by the user. When the user selects one of the notes a new activity is started to allow editing.

EditNoteActivity.java: This activity allows the user to edit a note. Once the note has been taken, the user can press the back button and the note will be saved.

# Note for Henry
- Please have a look at forked repositories as we have made some changes to the rules about merging with master branch.
- We have decided that in case our code has some error then we will not merge it from the forked repositories.

# Note for Developers:
- Please pull code from your own branch and push it to your own branch.
- Make sure your code is perfectly working before merging to the master branch.
