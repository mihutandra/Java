package repository;

import domain.Identifiable;
import domain.ValidationException;

import java.util.ArrayList;

public abstract class FileRepository<T extends Identifiable<ID>, ID> extends MemoryRepository<ID, T> {
    protected String filename;

    public FileRepository(String filename) {
        this.filename = filename;
        this.readFromFile();
    }

    public abstract void readFromFile();

    public abstract void writeToFile();


    @Override
    public void addEntity(T elem) throws ValidationException {
        super.addEntity(elem);
        writeToFile();
    }

    @Override
    public void updateEntity(ID id, T elem) throws ValidationException {
        super.updateEntity(id, elem);
        writeToFile();
    }

    @Override
    public T findById(ID id) throws ValidationException {

        return super.findById(id);
    }


    @Override

    public ArrayList<T> getAll() {
        return super.getAll();
    }
}
