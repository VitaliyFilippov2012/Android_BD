package bstu.fit.poibms.neva.lab6.birthdaysearch;

public class Contacts {
    private  String name;
    private  String surname;
    private  String phone;
    private  String birthday;

    public Contacts(String name,String surname,String phone, String birthday){
        this.name=name;
        this.surname=surname;
        this.phone=phone;
        this.birthday=birthday;
    }

    public String getName(){return name;}
    public String getSurname(){return surname;}
    public String getPhone(){return phone;}
    public String getBirthday(){return birthday;}

    @Override
    public String toString(){
        return "Main:";
    }

}