package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String surname;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public static void main(String[] args) {
        List<Student> italianStudents = new ArrayList<>();
        List<Student> germanStudents = new ArrayList<>();

        String italianView = "CREATE VIEW italian_students AS \n" +
                "SELECT first_name, last_name FROM students WHERE country = 'Italy';";

        String germanView = "CREATE VIEW german_students AS \n" +
                "SELECT first_name, last_name FROM students WHERE country = 'Germany';";

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/newdb",
                    "root",
                    "developerCamu*@");

            Statement italianStudentsStatement = connection.createStatement();
            italianStudentsStatement.executeUpdate(italianView);

            Statement germanStudentsStatement = connection.createStatement();
            germanStudentsStatement.executeUpdate(germanView);


            Statement showItalianStudents = connection.createStatement();
            ResultSet resultSetShowIta = showItalianStudents.executeQuery("SELECT * FROM italian_students;");

            Statement showGermanStudents = connection.createStatement();
            ResultSet resultSetShowDeu = showGermanStudents.executeQuery("SELECT * FROM german_students;");

            while (resultSetShowIta.next()){
                String firstName = resultSetShowIta.getString("first_name");
                String lastName = resultSetShowIta.getString("last_name");

                italianStudents.add(new Student(firstName, lastName));

            }

            while (resultSetShowDeu.next()){
                String firstName = resultSetShowDeu.getString("first_name");
                String lastName = resultSetShowDeu.getString("last_name");

                germanStudents.add(new Student(firstName, lastName));
            }

        }catch (SQLException e){
            System.out.println("SQL exception: " + e.getMessage());
        }

    }
}
