package com.example.AssignmentGD2Java4.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BorrowRequests")
public class BorrowRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Quan hệ nhiều BorrowRequests -> 1 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private Users user;

    // Quan hệ nhiều BorrowRequests -> 1 Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BookId", nullable = false)
    private Books book;

    @Column(name = "RequestDate")
    private LocalDateTime requestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false, length = 10)
    private RequestStatus status;

    // Enum trạng thái
    public enum RequestStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}