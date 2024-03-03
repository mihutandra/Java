package repository;

import domain.Identifiable;
import domain.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemoryRepository<ID,T extends Identifiable<ID>> implements GenericRepository<T, ID>
{
    protected Map<ID,T> repo = new HashMap<>();

    @Override
    public void addEntity(T item) throws ValidationException {
        if(repo.containsKey(item.getId())){
            throw new ValidationException("The element already exists.");
        }
        repo.put(item.getId(),item);
    }

    @Override
    public void deleteEntity(ID id) throws ValidationException {
        if (!repo.containsKey(id)) {
            throw new ValidationException("The element doesn't exist");
        } else {
            repo.remove(id);
            //writeToFile();
        }
    }
    @Override
    public T findById(ID id) throws ValidationException {

        if(!repo.containsKey(id)){
            throw new ValidationException("The id doesn't exits");}
        return repo.get(id);
    }
    @Override
    public ArrayList<T> getAll(){
        ArrayList<T> rez = new ArrayList<>(repo.values());
        return rez;
    }

    @Override
    public void updateEntity(ID id, T updatedEntity) throws ValidationException {

        if(!repo.containsKey(id)){
            throw new ValidationException("The element doesn't exits");
        }
        else{
            repo.put(id, updatedEntity);
        }
    }
}
