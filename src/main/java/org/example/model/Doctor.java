package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.static_data.Specialization;

import java.util.List;

@Entity
@Data
@Table(name = "doctors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "appointments")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;
    @Column(unique = true)
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialization specialization;
    @OneToMany(mappedBy = "doctor")
    private List<Appointment>appointments;



}
