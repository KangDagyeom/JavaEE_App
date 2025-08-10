package com.example.AssignmentGD2Java4.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Title", nullable = false, length = 200)
    private String title;

    @Column(name = "Author", nullable = false, length = 100)
    private String author;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

}
