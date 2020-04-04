package br.com.jefferson.salesmanegement.domain.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.jefferson.salesmanegement.domain.dto.UserDto;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 128)
    @NotNull
    private String name;

    @Column(length = 128, unique = true)
    @Email
    @NotNull
    private String mail;

    @Column(length = 128)
    @Size(min = 6, max = 128)
    @NotNull
    private String password;

    @OneToMany(mappedBy = "user", targetEntity = Group.class)
    @JsonIgnore
    private List<Group> groups;

    @OneToMany(mappedBy = "user", targetEntity = Sale.class)
    @JsonIgnore
    private List<Sale> sales;

    @OneToMany(mappedBy = "user", targetEntity = Categorie.class)
    @JsonIgnore
    private List<Categorie> categories;

    @OneToMany(mappedBy = "user", targetEntity = Product.class)
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy = "user", targetEntity = ProductSold.class)
    @JsonIgnore
    private List<ProductSold> productsSold;

    public UserDto toUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(this.id);
        userDto.setMail(this.mail);
        userDto.setName(this.name);
        return userDto;
    }

}
