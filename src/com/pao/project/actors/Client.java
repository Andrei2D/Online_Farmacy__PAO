package com.pao.project.actors;

public class Client extends User{

    private transient String email;
    private transient String telephone;

    public Client () {
        super();
    }

    public Client(int uniqID, String username, String password,
                  String email, String telephone) {
        super(uniqID, username, password);
        this.email = email;
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        String ID = "ID: " + uniqID;
        String usr = "Username: " + username;
        String pass = "Password: " + password;
        String mail = "e-mail: " + email;
        String phone = "Telephone: " + telephone;
        return "C |" + ID + " | " + usr + " | " + pass + " | " + mail + " | " + phone;
    }

    @Override
    public String[] dataToStore() {
        String[] data = new String[5];
        data[0] = String.valueOf(uniqID);
        data[1] = username;
        data[2] = password;
        data[3] = email;
        data[4] = telephone;

        return  data;
    }


    @Override
    public void setData(String[] data) {

        uniqID = Integer.valueOf(data[0]);
        username = data[1];
        password = data[2];
        email = data[3];
        telephone = data[4];

    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    @Override
    public int getClassMask() {
        return CLIENT;
    }

    // SPECIFIC METHODS FOR CHANGING PRODUCTS
}