package br.com.jefferson.salesmanegement.domain.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_sales")
@EntityListeners(AuditingEntityListener.class)
public class Sale implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", updatable = false)
    @NotNull
    @JsonProperty(access = Access.WRITE_ONLY)
    private Group group;

    @Column(length = 128)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "sale", targetEntity = ProductSold.class)
    @JsonIgnore
    private List<ProductSold> productsSold;

}
