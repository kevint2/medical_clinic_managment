package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.static_data.Status;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "appointments")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;
    private String patientFullname;
    private String note;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @Column(name = "begin_at",nullable = false)
    private LocalDateTime beginAt;
    @Column(name = "ends_at",nullable = false)
    private LocalDateTime endsAt;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @OneToOne(mappedBy = "appointment")
    private Report report;
}
