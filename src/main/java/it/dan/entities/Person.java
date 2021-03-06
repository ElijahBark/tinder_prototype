package it.dan.entities;

public class Person {
    private String login;
    private String name;
    private String photoUrl;
    private String password;
    private Boolean sex;

    public Person() {

    }

    public Person(String login, String name, String photoUrl, Boolean sex, String password) {
        this.login = login;
        this.name = name;
        this.photoUrl = photoUrl;
        this.sex = sex;
        this.password = password;
    }


    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
