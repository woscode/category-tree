package com.wos.categorytree;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Category parent = null;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OrderColumn
    private Set<Category> childes = new HashSet<>();

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parent) {
        this(name);
        this.parent = parent;
    }

    public boolean isRoot() {
        return Objects.isNull(getParent());
    }

    public boolean hasChildes() {
        return !getChildes().isEmpty();
    }

    /**
     * Compare categories by name ignoring case
     * @implNote violates the compare-equals contract
     * @throws NullPointerException – if an argument is null
     */
    @Override
    public int compareTo(Category o) {
        return Objects.compare(getName(), o.getName(), String::compareToIgnoreCase);
    }

//   public void addSubCategory(Category subCategory) {
//        if (getChildes().add(subCategory))
//            subCategory.setParent(this);
//    }
//
//    public void removeSubCategory(Category subCategory) {
//        if (getChildes().remove(subCategory))
//            subCategory.setParent(null);
//    }
}
