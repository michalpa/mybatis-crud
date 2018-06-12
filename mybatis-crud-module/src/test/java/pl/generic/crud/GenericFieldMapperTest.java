package pl.generic.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.generic.crud.service.GenericFieldMapper;

public class GenericFieldMapperTest {
    private GenericFieldMapper fieldMapper;

    @Before
    public void setUp() {
        fieldMapper = new GenericFieldMapper();
    }

    @Test
    public void shouldMappingTableColumn() {
        assertThat(fieldMapper.getTableName(User.class)).isEqualTo("USERS");
    }

    @Test
    public void shouldMappingFieldMapper() {
        Map<String, String> fields = fieldMapper.getFields(User.class);
        assertThat(fields).containsKeys("EMAIL", "MOBILE", "PASSWORD");
        assertThat(fields).containsValues("#{email}", "#{mobile}", "#{password}");
    }

    @Test
    public void shouldReturnColumnIdField() {
        Field field = fieldMapper.getColumnIdField(User.class);
        assertThat(field.getName()).isEqualTo("id");
    }

}
