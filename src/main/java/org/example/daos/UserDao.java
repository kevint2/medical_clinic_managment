package org.example.daos;

import org.example.exeption.GenericExeption;
import org.example.model.User;
import org.hibernate.Session;

import java.util.List;

public class UserDao extends GenericDao<User, Long> {
    Session session;

    public UserDao(Session session) {
        super(session, User.class);
        this.session = session;
    }

    public User create(User user) {
        if (user.getId() != null) {
            throw GenericExeption.idNotNull();
        } else {
            return super.create(user);
        }
    }

    public void delete(Long id) {

        User user = findById(id);
        if (user == null) {
            throw GenericExeption.userDoesNotExist(id);
        } else {
            super.delete(user);
        }
    }
    public User update(User user){
        if (user.getUsername()==null){
            throw  GenericExeption.usernameIsNull();
        }else if (user.getId().equals(findById(user.getId()))){
            return super.update(user);
        }else {
            throw GenericExeption.userDoesNotExist(user.getUsername());
        }
    }
}
