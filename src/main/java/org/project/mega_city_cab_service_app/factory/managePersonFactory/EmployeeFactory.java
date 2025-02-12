    package org.project.mega_city_cab_service_app.factory.managePersonFactory;

    import org.project.mega_city_cab_service_app.factory.Interface.PersonFactory;
    import org.project.mega_city_cab_service_app.model.person.Employee;
    import org.project.mega_city_cab_service_app.model.Parent.Person;
    import org.project.mega_city_cab_service_app.util.JsonUtils;

    public class EmployeeFactory implements PersonFactory {
//        private final double salary;
//        private final int experience;
//
//        public EmployeeFactory(double salary, int experience) {
//            this.salary = salary;
//            this.experience = experience;
//        }

//        @Override
//        public Person createPerson(String name, String address, String mobile) {
//            return new Employee(name, address, mobile, salary, experience);
//        }
        @Override
        public Person createPerson(String jsonInput, String name, String address, String mobile, String  username ,String password) {
            double salary = Double.parseDouble(JsonUtils.extractValueFromJson(jsonInput, "salary"));
            int experience = Integer.parseInt(JsonUtils.extractValueFromJson(jsonInput, "experience"));
            return new Employee(name, address, mobile, username ,password,salary, experience);
        }

    }
