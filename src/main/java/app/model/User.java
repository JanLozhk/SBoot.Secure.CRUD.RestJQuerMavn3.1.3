package app.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
//@Table(name = "user1")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "email")
   private String email;

   @Column(name = "login", unique = true)
   private String login;

   @Column(name = "password")
   private String password;

   private String age;


   @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH}/*, fetch = FetchType.EAGER*/)
   @JoinTable(
           name = "user_authority",
           joinColumns = { @JoinColumn(name = "user_id") },
           inverseJoinColumns = { @JoinColumn(name = "authority_id") }
   )
   List<Authority> authorityList;

   /* @Column
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_role"))
    private Set<Role> roles;*/

   public User() {
   }

  /* public User(String firstName, String lastName, String email, String login, String password) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.login = login;
      this.password = password;
   }

   public User(String firstName, String lastName, String email, String login, String password, List<Authority> authorityList) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.login = login;
      this.password = password;
      this.authorityList = authorityList;
   }

   public User(String firstName, String lastName, String email, String login, String password, String age, List<Authority> authorityList) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.login = login;
      this.password = password;
      this.age = age;
      this.authorityList = authorityList;
   }

   public User(String firstName, String lastName, String email) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
   }

   public User(Long id, String firstName, String lastName) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
   }*/

   public String getAge() {
      return age;
   }

   public void setAge(String age) {
      this.age = age;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   @Override
   public String toString() {
      return "User{" +
//              "id=" + id +
              ", firstName='" + firstName + '\'' +
              ", lastName='" + lastName + '\'' +
              ", email='" + email + '\'' +
              '}';
   }

   public void setEmail(String email) {
      this.email = email;
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

   public List<Authority> getAuthorityList() {
      return authorityList;
   }

   public void setAuthorityList(List<Authority> authorityList) {
      this.authorityList = authorityList;
   }
}