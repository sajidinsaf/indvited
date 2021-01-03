package com.ef.model.member;

import java.sql.Timestamp;

import com.ef.common.FieldCannotBeMutatedException;

public class Member {

  private String id;
  private String firstname;
  private String lastname;
  private String username;
  private String email;
  private String phone;
  private String password;
  private MemberType memberType;
  private Timestamp date_registered;
  private Timestamp timestamp_of_last_login;

  public Member() {

  }

  public Member(String id, String firstname, String lastname, String username, String email, String phone,
      MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login) {
    super();
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.email = email;
    this.phone = phone;
    this.memberType = memberType;
    this.date_registered = date_registered;
    this.timestamp_of_last_login = timestamp_of_last_login;
  }

  public Member(String id, String firstname, String lastname, String username, String email, String phone,
      String password, MemberType memberType, Timestamp date_registered, Timestamp timestamp_of_last_login) {
    super();
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.memberType = memberType;
    this.date_registered = date_registered;
    this.timestamp_of_last_login = timestamp_of_last_login;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    checkForMutation(id, "id");
    this.id = id;
  }

  public String getFirstName() {
    return firstname;
  }

  public void setFirstName(String firstname) {
    checkForMutation(firstname, "firstname");
    this.firstname = firstname;
  }

  public String getLastName() {
    return lastname;
  }

  public void setLastName(String lastname) {
    checkForMutation(lastname, "lastname");
    this.lastname = lastname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    checkForMutation(username, "username");
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    checkForMutation(email, "email");
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    checkForMutation(phone, "phone");
    this.phone = phone;
  }

  public MemberType getMemberType() {
    return memberType;
  }

  public void setMemberType(MemberType memberType) {
    checkForMutation(memberType, "memberType");
    this.memberType = memberType;
  }

  public Timestamp getDateRegistered() {
    return date_registered;
  }

  public void setDate_registered(Timestamp date_registered) {
    checkForMutation(date_registered, "date_registered");
    this.date_registered = date_registered;
  }

  public Timestamp getTimestamp_of_last_login() {
    return timestamp_of_last_login;
  }

  public void setTimestamp_of_last_login(Timestamp timestamp_of_last_login) {
    checkForMutation(timestamp_of_last_login, "timestamp_of_last_login");
    this.timestamp_of_last_login = timestamp_of_last_login;
  }

  private void checkForMutation(Object o, String fieldName) {
    if (o != null) {
      throw new FieldCannotBeMutatedException(fieldName);
    }
  }

  @Override
  public String toString() {
    return "Member [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
        + ", email=" + email + ", phone=" + phone + ", memberType=" + memberType + ", date_registered="
        + date_registered + ", timestamp_of_last_login=" + timestamp_of_last_login + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((date_registered == null) ? 0 : date_registered.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
    result = prime * result + ((memberType == null) ? 0 : memberType.hashCode());
    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
    result = prime * result + ((timestamp_of_last_login == null) ? 0 : timestamp_of_last_login.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Member other = (Member) obj;
    if (date_registered == null) {
      if (other.date_registered != null)
        return false;
    } else if (!date_registered.equals(other.date_registered))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (firstname == null) {
      if (other.firstname != null)
        return false;
    } else if (!firstname.equals(other.firstname))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (lastname == null) {
      if (other.lastname != null)
        return false;
    } else if (!lastname.equals(other.lastname))
      return false;
    if (memberType == null) {
      if (other.memberType != null)
        return false;
    } else if (!memberType.equals(other.memberType))
      return false;
    if (phone != other.phone)
      return false;
    if (timestamp_of_last_login == null) {
      if (other.timestamp_of_last_login != null)
        return false;
    } else if (!timestamp_of_last_login.equals(other.timestamp_of_last_login))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

}
