    package org.example.mega_city_cab_service_app.factory.managePersonFactory;

    import org.example.mega_city_cab_service_app.factory.PersonFactory;
    import org.example.mega_city_cab_service_app.model.person.Employee;
    import org.example.mega_city_cab_service_app.model.Person;

    public class EmployeeFactory implements PersonFactory {
        private final double salary;
        private final int experience;

        public EmployeeFactory(double salary, int experience) {
            this.salary = salary;
            this.experience = experience;
        }

        @Override
        public Person createPerson(String name, String address, String mobile) {
            return new Employee(name, address, mobile, salary, experience);
        }
    }