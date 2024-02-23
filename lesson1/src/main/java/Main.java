import com.github.javafaker.Faker;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        //Создать список из 1_000 рандомных чисел от 1 до 1_000_000
        List<Integer> list = Stream.generate(() -> ThreadLocalRandom.current().nextInt(1000001))
                .limit(1000)
                .collect(Collectors.toList());

        //Найти максимальное
        System.out.println(list.stream().max(Comparator.naturalOrder()).get());

        //Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
        System.out.println(list.stream().filter(it -> it > 500000).map(it -> it * 5 - 150).reduce(0, (a, b) -> a + b));

        //Найти количество чисел, квадрат которых меньше, чем 100_000
        System.out.println(list.stream().filter(it -> it * it < 100000).collect(Collectors.toList()));


        //Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
        //Создать список из 10-20 сотрудников
        List<Employee> employeeList = createEmployees();

        //Вывести список всех различных отделов (department) по списку сотрудников
        List<String> departments = employeeList.stream().map(it -> it.getDepartment()).distinct().collect(Collectors.toList());
        System.out.println(departments);


        //Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
        employeeList.stream().filter(it -> it.getSalary() < 10000).forEach(it -> it.setSalary(it.getSalary() * 1.2));
        System.out.println(employeeList);

        //Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
        Map<String, List<Employee>> listMap = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toList()));
        System.out.println(listMap);

        //Из списка сотрудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
        Map<String, Double> salaryMap = employeeList.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(salaryMap);
    }

    public static List<Employee> createEmployees() {
        Faker faker = new Faker();
        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            employeeList.add(new Employee(
                    faker.name().firstName(),
                    faker.number().numberBetween(18, 64),
                    faker.number().numberBetween(2500, 3500),
                    faker.job().title()));
        }

        return employeeList;
    }
}