package pl.generic.crud.service;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class GenericSQLProvider<T, PK> {
    private GenericFieldMapper fieldMapper = new GenericFieldMapper();

    public String getAll(Map map) {
        String table = (String) map.get("table");
        return new SQL() {
            {
                SELECT("*");
                FROM(table);
            }
        }.toString();
    }

    public String insert(T o) {
        SQL sql = new SQL();
        sql.INSERT_INTO(fieldMapper.getTableName(o.getClass()));
        fieldMapper.getFields(o.getClass())
                   .forEach((k, v) -> sql.VALUES(k, v));
        return sql.toString();
    }

    public String update(T o) {
        SQL sql = new SQL();
        sql.UPDATE(fieldMapper.getTableName(o.getClass()));
        fieldMapper.getFields(o.getClass())
                   .forEach((k, v) -> sql.SET(k + " = " + v));
        sql.WHERE(fieldMapper.getColumnId(o.getClass()) + " = #{id}");
        return sql.toString();
    }

    public String getById(Map map) {
        String table = (String) map.get("table");
        String column = (String) map.get("column");
        return new SQL() {
            {
                SELECT("*");
                FROM(table);
                WHERE(column + " = #{id}");
            }
        }.toString();
    }

    public String deleteById(Map map) {
        String table = (String) map.get("table");
        String column = (String) map.get("column");
        return new SQL() {
            {
                DELETE_FROM(table);
                WHERE(column + " = #{id}");
            }
        }.toString();
    }
}
