package org.example.daos;

import org.example.exeption.GenericExeption;
import org.example.model.Doctor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class DoctorDao extends GenericDao<Doctor, Long> {
    private Session session;

    public DoctorDao(Session session) {
        super(session, Doctor.class);
        this.session = session;
    }

    public Doctor create(Doctor doctor) {
        if (doctor.getId() != null) {
            throw GenericExeption.idNotNull();
        } else {
            return super.create(doctor);
        }
    }

    public void delete(Long id) {
        Doctor doctor = findById(id);
        if (doctor == null) {
            throw GenericExeption.userDoesNotExist(id);
        } else {
            super.delete(doctor);
        }
    }

    public List<Doctor> findDoctorBySpecialization(String specialization) {
        String query = "select record from Doctor record where record.specialization = :specialization";
        Query<Doctor> findDoctorBySpecializationQuery = session.createQuery(query, Doctor.class);
        findDoctorBySpecializationQuery.setParameter("specalization", "%s".concat(specialization).concat("%s"));
        return findDoctorBySpecializationQuery.getResultList();
    }

    public Doctor findDoctorById(Long id) {
        Doctor doctor = findById(id);
        if (doctor == null) {
            throw GenericExeption.userDoesNotExist(id);
        } else {
            return doctor;
        }
    }
}
