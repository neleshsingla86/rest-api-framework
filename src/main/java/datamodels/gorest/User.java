package datamodels.gorest;

import datamodels.common.BaseModel;

/**
 * Java representation of User object as defined by https://gorest.co.in/
 *
 * <b>NOTE: It kind of uses builder pattern to make it easier to set it in the consumer classes</b>
 */
public class User extends BaseModel<User> {

    private String name;
    private String email;
    private String gender;
    private String status;

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public User setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public User setStatus(String status) {
        this.status = status;
        return this;
    }
}
