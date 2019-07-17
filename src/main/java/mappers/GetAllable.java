package mappers;

import models.Model;

import java.util.Collection;

public interface GetAllable {
    Collection<Model> getAll() throws Exception;
}
