package caren.android.com.wwd.UserListActivity;

public class UserModel {

    private String name;
    private String location;
    private String email;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        if (location != null) {
            return location;
        } else {
            return "N / A";
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        if (email != null) {
            return email;
        } else {
            return "N / A";
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        if (phone != null) {
            return phone;
        } else {
            return "N / A";
        }
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
