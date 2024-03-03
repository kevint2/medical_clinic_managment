package org.example.exeption;

public class GenericExeption extends RuntimeException {
    private GenericExeption(String message) {
        super(message);
    }

    public static GenericExeption idNotNull() {
        throw new GenericExeption("Id must be null");
    }
    public static GenericExeption idNull() {
        throw new GenericExeption("Id should not be null");
    }

    public static GenericExeption usernameExists(String username) {
        throw new GenericExeption(String.format("User with username %s already exist", username));
    }

    public static GenericExeption userDoesNotExist(Long id) {
        throw new GenericExeption(String.format("User with id = %s does not exist", id));

    }
    public static GenericExeption userDoesNotExist(String username) {
        throw new GenericExeption(String.format("User with id = %s does not exist", username));

    }
    public  static  GenericExeption usernameIsNull(){
        throw  new GenericExeption("Username should not be null");
    }
    public static GenericExeption appointmentNotCompleted(){
        throw  new GenericExeption("Appointment should be Completed");
    }
    public static GenericExeption docorIsBusy(){
        throw new GenericExeption("Doctor is busy at that time");
    }
    public static  GenericExeption visitCanNotFinishBeforeStart(){
        throw new GenericExeption("The schedule can not finish before the start");
    }
}
