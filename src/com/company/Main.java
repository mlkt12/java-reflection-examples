package com.company;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {

    public static void main(String[] args) {
        String personClassName = Person.class.getCanonicalName(); //"com.company.Person";

        Class<?> personClass = null; // convert string classname to class
        try {
            personClass = Class.forName(personClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object person = null; // invoke empty constructor
        try {
            person = personClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        String methodName = "";

        // with single parameter, return void
        methodName = "setName";
        Method setNameMethod = null;
        try {
            setNameMethod = person.getClass().getMethod(methodName, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            setNameMethod.invoke(person, "John"); // pass arg
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // without parameters, return string
        methodName = "getName";
        Method getNameMethod = null;
        try {
            getNameMethod = person.getClass().getMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String name = null; // explicit cast
        try {
            name = (String) getNameMethod.invoke(person);
            System.out.println("name: "+name);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // with multiple parameters
        for (Method method : Person.class.getDeclaredMethods()) {
            String name1 = method.getName();
            System.out.println("method name: "+name1);

            if (Modifier.isPublic(method.getModifiers())) {
                System.out.println("public method: "+method.getReturnType().getSimpleName());
            }

            if (Modifier.isPrivate(method.getModifiers())) {
                System.out.println("private method: "+method.getName());
            }

        }

        methodName = "printPerson";
        Class<?>[] paramTypes = {String.class, int.class};
        Method printPersonMethod = null;
        try {
            printPersonMethod = person.getClass().getMethod(methodName, paramTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            printPersonMethod.invoke(person, name, 3); // pass args
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}