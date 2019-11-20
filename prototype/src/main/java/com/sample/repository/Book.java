package com.sample.repository;

import org.fluentness.repository.crud.Model;

import javax.persistence.*;

@Entity
@Table(name = "song")
public class Book implements Model {

    private int id;
    private String title;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

}
