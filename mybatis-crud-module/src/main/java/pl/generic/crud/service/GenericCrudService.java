package pl.generic.crud.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import pl.generic.crud.mapper.GenericCrudMapper;

public abstract class GenericCrudService<T, PK> {
    private GenericCrudMapper<T, PK> mapper;
    private GenericFieldMapper fieldMapper = new GenericFieldMapper();
    private Class clazz;

    public GenericCrudService(GenericCrudMapper<T, PK> mapper) {
        this.mapper = mapper;
        final ParameterizedType type = (ParameterizedType) this.getClass()
                                                               .getGenericSuperclass();
        clazz = (Class<T>) type.getActualTypeArguments()[0];
    }

    public List<T> getAll() {
        return mapper.getAll(fieldMapper.getTableName(clazz));
    }

    public void insert(T o) {
        mapper.insert(o);
    }

    public void update(T o) {
        mapper.update(o);
    }

    public T getById(PK id) {
        return (T) mapper.getById(id, fieldMapper.getTableName(clazz),
                fieldMapper.getColumnId(clazz));
    }

    public void deleteById(PK id) {
        mapper.deleteById(id, fieldMapper.getTableName(clazz), fieldMapper.getColumnId(clazz));
    }

}
