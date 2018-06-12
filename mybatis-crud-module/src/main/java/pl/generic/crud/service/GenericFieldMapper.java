package pl.generic.crud.service;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pl.generic.crud.annotation.ColumnDB;
import pl.generic.crud.annotation.Id;
import pl.generic.crud.annotation.Table;

public class GenericFieldMapper {

    private static final String NESTED_OBJECT_TEMPLATE = "#'{'{0}.{1}'}'";
    private static final String OBJECT_TEMPLATE = "#'{'{0}'}'";

    public String getTableName(Class clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalArgumentException("Table annotation on class not exist");
        }

        Table table = (Table) clazz.getAnnotation(Table.class);
        return table.name();
    }

    public Map<String, String> getFields(Class clazz) {
        List<Field> fields = getColumnDBFields(clazz);
        Map<String, String> fieldsMap = new HashMap<>();
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("Column annotation on filed not defined");
        }
        fields.stream()
              .forEach(f -> {
                  Field nestedField = getColumnIdField(f.getType());
                  addFieldToMap(fieldsMap, f, nestedField);
              });

        return fieldsMap;
    }

    public Field getColumnIdField(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(f -> f.isAnnotationPresent(Id.class))
                     .findFirst()
                     .orElse(null);
    }

    public String getColumnId(Class clazz) {
        Field field = Arrays.stream(clazz.getDeclaredFields())
                            .filter(f -> f.isAnnotationPresent(Id.class))
                            .findFirst()
                            .orElse(null);
        return field != null ? field.getAnnotation(ColumnDB.class)
                                    .name()
                : null;
    }

    private void addFieldToMap(Map<String, String> fieldsMap, Field field, Field nestedField) {
        String fieldDB = field.getAnnotation(ColumnDB.class)
                              .name();
        if (nestedField != null) {
            fieldsMap.put(fieldDB, MessageFormat.format(NESTED_OBJECT_TEMPLATE, field.getName(),
                    nestedField.getName()));
        } else {
            if (!field.isAnnotationPresent(Id.class)) {
                fieldsMap.put(fieldDB, MessageFormat.format(OBJECT_TEMPLATE, field.getName()));
            }
        }
    }

    private List<Field> getColumnDBFields(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(f -> f.isAnnotationPresent(ColumnDB.class))
                     .collect(Collectors.toList());
    }
}
