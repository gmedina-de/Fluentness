package com.sample.models;

import org.fluencyframework.ann.Column;
import org.fluencyframework.ann.Table;
import org.fluencyframework.mvc.Model;

@Table("person")
public class Person implements Model {

    @Column(pk = true)
    private int id;
    @Column
    private String name;
    @Column
    private String surname;

    public int getId() {
        return id;
    }

    public Person setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Person setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Person setSurname(String surname) {
        this.surname = surname;
        return this;
    }
}
