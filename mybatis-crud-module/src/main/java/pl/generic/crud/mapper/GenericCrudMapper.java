package pl.generic.crud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import pl.generic.crud.service.GenericSQLProvider;

public interface GenericCrudMapper<T, PK> {
    @SelectProvider(type = GenericSQLProvider.class, method = "getAll")
    List<T> getAll(@Param("table") String table);

    @SelectProvider(type = GenericSQLProvider.class, method = "getById")
    T getById(@Param("id") PK id, @Param("table") String table, @Param("column") String column);

    @InsertProvider(type = GenericSQLProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    void insert(T o);

    @UpdateProvider(type = GenericSQLProvider.class, method = "update")
    void update(T o);

    @DeleteProvider(type = GenericSQLProvider.class, method = "deleteById")
    void deleteById(@Param("id") PK id, @Param("table") String table,
            @Param("column") String column);

}
