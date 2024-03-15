package com.Relearn;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String name = "Jey";

//        Function<String, Boolean> upperCase = String::isBlank;
        Function<String, String> upperCase = String::toUpperCase; //this really doesn't print anything
        System.out.println("this is " +  upperCase.apply(name)); //until we call the static method

        Function<String, String> lastName = s -> s.concat(" Okechukwu");
        Function<String, String> upperCaseLastName = upperCase.andThen(lastName); //merging two lambdas
        System.out.println(upperCaseLastName.apply(name));

        upperCaseLastName = upperCase.compose(lastName); //compose method works on the var in the brackets first,then the initial var:
        System.out.println(upperCaseLastName.apply(name)); // makes the 'lastName' 'upperCase' first, then does its job: String::toUpperCase

        //we don't have to work with the same types at all times:
        Function<String, String[]> f0 = upperCase.andThen(s -> s.concat(" Okey")).andThen(s -> s.split(" ")); // what we do before the last statement is not a problem for our return type, only the last expression is needed to be the return type
        System.out.println(Arrays.toString(f0.apply("Jeyson")));


        Function<String, String> f1 = upperCase.andThen(s -> s.concat(" Okey")).andThen(s -> s.split(" ")).andThen(s -> s[1].toUpperCase() + ", " + s[0]);
        System.out.println(f1.apply(name));

        Function<String, Integer> f2 = upperCase.andThen(s -> s.concat(" Okey")).andThen(s -> s.split(" ")).andThen(s -> String.join(", " + s)).andThen(String::length);
        //again, the interim methods return types don't matter. They just have to provide adequate and useful results, and the final expression the actual return type

        //the andThen() is also available on the BiFunction< > and BinaryOperator< > but not for any interface that have a primitive argument like IntFunction< >, DoubleFunction< >, and LongFunction< >.

        System.out.println("-------------------------");

        // the andThen() is also available on the Consumer< > as well
        String[] names = {"Ann", "Ayo", "Mide"};

        Consumer<String> s0 = s -> System.out.print(s.charAt(0)); //gets the first letter
        Consumer<String> s1 = System.out::println; // gets each item on a new line;

        Arrays.asList(names).forEach(s0.andThen(s -> System.out.print(" - ")).andThen(s1));


        //THE CONVENIENCE METHODS ON A PREDICATE:
        System.out.println("-----------------------");
        Predicate<String> p0 = s -> s.startsWith("J");
        Predicate<String> p1 = s -> s.endsWith("p");
        Predicate<String> p2 = s -> s.equals("Jey");
        Predicate<String> p3 = s -> s.equalsIgnoreCase("JeY");

        Predicate<String> p04 = p1.and(p0); //if p1 AND p0 are true
        Predicate<String> p05 = p1.or(p3); //if p1 OR p3 are true

//        String and = "and";
//        Function<String, String> upperCaese = String::toUpperCase;
//        upperCaese.apply(and);
//        System.out.println("p1 " + upperCaese.apply(and) + " p0 " + p04.test(name));

        System.out.println("p1 AND p0 " + p04.test(name));
        System.out.println("p1 OR p3 " + p05.test(name));

        //returning the opposite value (Necessary? maybe for encryption - mark out what you want reversed and combine all. eg -results 1, 9, and 4 are opposite, that way only you know)
        Predicate<String> p06 = p1.and(p0).negate();
        System.out.println(p06.test(name));


        //CONVENIENCE METHODS ON COMPARATOR

        System.out.println("-------------------------");
        record Person (String firstName, String lastName){}

        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Peter", "Pan"),
                new Person("Peter", "Parker"),
                new Person("Minnie", "Mouse"),
                new Person("Micky", "Mouse")
        ));

        people.sort((person1, person2) -> person1.lastName.compareTo(person2.lastName));
        people.forEach(System.out::println);

        System.out.println("------------ using Comparator.comparing--------------");
        people.sort(Comparator.comparing(Person::firstName));
        people.forEach(System.out::println);

        System.out.println("------------ using Comparator.thenComparing--------------");
        people.sort(Comparator.comparing(Person::firstName).thenComparing(Person::lastName)); //sorts by the the firstName, then the lastName
        people.forEach(System.out::println);

        System.out.println("------------ using reversed() -----------");
        people.sort(Comparator.comparing(Person::firstName).thenComparing(Person::lastName).reversed()); //reverses the sequence
        people.forEach(System.out::println);

    }
}
