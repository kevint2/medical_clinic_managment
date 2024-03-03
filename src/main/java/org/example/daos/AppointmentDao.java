package org.example.daos;


import org.example.exeption.GenericExeption;
import org.example.model.Appointment;
import org.example.model.Doctor;
import org.example.static_data.Status;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDao extends GenericDao<Appointment, Long> {
    private final Session session;
    private final DoctorDao doctorDao;


    public AppointmentDao(Session session, DoctorDao doctorDao) {
        super(session, Appointment.class);
        this.session = session;
        this.doctorDao = doctorDao;
    }

    public Appointment create(Appointment appointment, Long id) {
        List<Appointment> appointments = getAllActiveAppointmentByDoctor(id);
        if (appointment.getId() != null) {
            throw GenericExeption.idNotNull();
        } else {
            appointments.forEach(appointment1 -> {
                        if (!isTimeFree(appointment, appointment1)) {
                            throw GenericExeption.docorIsBusy();
                        }
                    }
            );
            Doctor doctor = doctorDao.findDoctorById(id);
            appointment.setDoctor(doctor);
            appointment.setStatus(Status.ACTIVE);
            return super.create(appointment);
        }

    }

    public List<Appointment> getAllActiveAppointmentByDoctor(Long id) {
        String query = "select records from Appointment records where doctor.id = :id and records.status= :status and records.beginAt > :localdate ";
        Query<Appointment> getAllActiveAppointmentByDoctorQuery = session.createQuery(query, Appointment.class);
        getAllActiveAppointmentByDoctorQuery.setParameter("id", id);
        getAllActiveAppointmentByDoctorQuery.setParameter("status", Status.ACTIVE);
        getAllActiveAppointmentByDoctorQuery.setParameter("localdate", LocalDateTime.now());
        return getAllActiveAppointmentByDoctorQuery.getResultList();
    }

    public Appointment cancelledAppointment(Long id) {
        Appointment appointment = findById(id);
        if (appointment == null) {
            throw GenericExeption.userDoesNotExist(id);
        } else {
            appointment.setStatus(Status.CANCELLED);
            return super.update(appointment);
        }
    }


    private boolean isTimeFree(Appointment appointment, Appointment appointment1) {
        if ((appointment.getBeginAt().isBefore(appointment1.getBeginAt()) &&
                appointment.getEndsAt().isAfter(appointment1.getEndsAt()))) {
            return false;
        }
        if (
                appointment.getBeginAt().isAfter(appointment1.getBeginAt()) &&
                        appointment.getBeginAt().isBefore(appointment1.getEndsAt())) {
            return false;
        }
        if (appointment.getEndsAt().isAfter(appointment1.getBeginAt()) &&
                appointment.getEndsAt().isBefore(appointment1.getEndsAt())) {
            return false;
        }
        if (appointment.getBeginAt().isEqual(appointment1.getBeginAt()) &&
                appointment.getEndsAt().isEqual(appointment1.getEndsAt())) {
            return false;
        }
        if (appointment.getBeginAt().isAfter(appointment.getEndsAt())) {
            return false;
        }
        return true;
    }
}
