package br.com.ironijunior.stock.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Product {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @OneToOne(optional = true, fetch = FetchType.EAGER)
    private Stock stock;

    public Product() {
    }

    public Product(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }
}
