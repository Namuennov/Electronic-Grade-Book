# Electronic-Grade-Book
Electronic grade book application using Java, Hibernate and Java Swing (and JavaFX on second branch)

There are actually 2 different applications: the first on the "main-teacherSide" branch and the second on the "FXEtude-studentSide" branch.

=TEACHER SIDE APPLICATION= ("main-teacherSide" branch)
It is the Electronic Grade Book application that represents teacher-side grade book (you can add students, give them points and do other things that teachers do).

To run program, run the main method in file Electronic-Grade-Book/src/main/java/Program.java. You must have SQL database installed and running!

If you want to have your database filled with sample data, you have to uncomment 4th line in file Electronic-Grade-Book/src/main/java/Program.java before running main method.
It always creates sample data, even if they already exist, so don't do this thing many times, because your sample data will be multiplied! Just make 4th line commented again after first run.

Wait few seconds in main menu after running, because Hibernate needs a while to ,,warm up".



=STUDENT SIDE APPLICATION= ("FXEtude-studentSide" branch)
It is the Electronic Grade Book application that represents student-side grade book (you can check your grades, enroll on the course and do other things that students do).

To run program, run the main method in file Electronic-Grade-Book/src/main/java/com/example/paoim_lab6/ProgramFX.java.

Application on this branch use nor Hibernate nor SQL, so the data won't be persisted!
