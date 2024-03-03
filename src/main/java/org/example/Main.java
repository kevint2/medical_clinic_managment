package org.example;

import org.example.config.HibernateUtils;
import org.example.daos.AppointmentDao;
import org.example.daos.DoctorDao;
import org.example.daos.UserDao;
import org.example.model.Appointment;
import org.example.model.Doctor;
import org.example.model.User;
import org.example.static_data.Role;
import org.example.static_data.Specialization;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();

        DoctorDao doctorDao = new DoctorDao(session);
        Doctor doctor =  Doctor.builder()
                .specialization(Specialization.CARDIOLOGIST)
                .username("kevint")
                .build();
//        doctorDao.create(doctor);
        AppointmentDao appointmentDao = new AppointmentDao(session ,doctorDao);
        Appointment appointment = Appointment.builder()
                .note("bla bla ")
                .patientFullname("Filan fisteku")
                .beginAt(LocalDateTime.now().plusMinutes(430))
                .endsAt(LocalDateTime.now().plusMinutes(500))
                .build();
        Appointment appointment1 = Appointment.builder()
                .note("bla bla ")
                .patientFullname("Filan fisteku")
                .beginAt(LocalDateTime.now().plusMinutes(40))
                .endsAt(LocalDateTime.now().plusMinutes(300))
                .build();
        appointmentDao.create(appointment,1L);
//        appointmentDao.create(appointment1,1L);
//        appointmentDao.cancelledAppointment(19L);
        System.out.println( appointmentDao.getAllActiveAppointmentByDoctor(1L));



    }
}