package org.example.daos;

import org.example.exeption.GenericExeption;
import org.example.model.Appointment;
import org.example.model.Report;
import org.example.static_data.Status;
import org.hibernate.Session;

public class ReportDao extends GenericDao<Report, Long>{
    private Session session;
    public ReportDao(Session session){
        super(session, Report.class);
        this.session=session;
    }
    public Report create(Report report){
        if (report.getId()!= null){
            throw GenericExeption.idNotNull();
        }else {
            Appointment appointment = report.getAppointment();
            appointment.setStatus(Status.COMPLETED);
            return super.create(report);
        }
    }
    public Report update(Report report){
        if (report.getId()==null){
            throw GenericExeption.idNull();
        }else {
            return  super.update(report);
        }
    }
}
