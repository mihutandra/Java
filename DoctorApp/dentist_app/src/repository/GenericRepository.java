package repository;

import domain.Identifiable;
import domain.ValidationException;

import java.util.ArrayList;

public interface GenericRepository <T extends Identifiable<ID>, ID>{

     void addEntity(T item) throws ValidationException;
     void deleteEntity(ID id) throws ValidationException;

     T findById(ID id) throws ValidationException;

     ArrayList<T> getAll();

     void updateEntity(ID id, T updatedEntity) throws ValidationException;
}
