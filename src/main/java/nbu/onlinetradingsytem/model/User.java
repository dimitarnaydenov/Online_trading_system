package nbu.onlinetradingsytem.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;
    
    private String password;

    @ManyToOne()
    private Role role;

    @OneToMany(mappedBy = "user",
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    private List<BoughtProducts> boughtProducts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PreRemove
    private void preRemove() {
        boughtProducts.forEach( p -> p.setUser(null));
    }

    public List<BoughtProducts> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<BoughtProducts> boughtProducts) {
        this.boughtProducts = boughtProducts;
    }
}
