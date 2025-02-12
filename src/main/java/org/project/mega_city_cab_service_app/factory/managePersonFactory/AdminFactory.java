    package org.project.mega_city_cab_service_app.factory.managePersonFactory;

    import org.project.mega_city_cab_service_app.factory.Interface.PersonFactory;
    import org.project.mega_city_cab_service_app.model.person.Admin;
    import org.project.mega_city_cab_service_app.model.Parent.Person;
//    public class AdminFactory implements PersonFactory {
//        @Override
//        public Person createPerson(String name, String address, String mobile) {
//            return new Admin(name, address, mobile);
//        }
    public class AdminFactory implements PersonFactory {
        @Override

        public Person createPerson(String jsonInput, String name, String address, String mobile, String  username , String password) {
            return new Admin(name, address, mobile,  username , password);
        }
    }





