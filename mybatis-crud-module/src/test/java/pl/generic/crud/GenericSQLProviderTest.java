package pl.generic.crud;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.generic.crud.service.GenericSQLProvider;

public class GenericSQLProviderTest {

    private GenericSQLProvider sqlProvider;
    private Map<String, Object> map;

    @Before
    public void setUp() {
        sqlProvider = new GenericSQLProvider<User, Integer>();
        map = new HashMap<>();
        map.put("table", "USERS");
        map.put("column", "USER_ID");
    }

    @Test
    public void shouldReturnAll() {
        String sql = sqlProvider.getAll(map);
        assertThat(sql).isEqualTo("SELECT *\n" + "FROM USERS");
    }

    @Test
    public void shouldInsert() {
        User user = User.builder()
                        .mobile("505555875")
                        .password("test")
                        .email("email")
                        .build();
        String sql = sqlProvider.insert(user);
        assertThat(sql).isEqualTo("INSERT INTO USERS\n" + " (PASSWORD, EMAIL, MOBILE)\n"
                + "VALUES (#{password}, #{email}, #{mobile})");
    }

    @Test
    public void shouldUpdate() {
        User user = User.builder()
                        .mobile("505555875")
                        .password("updateTest")
                        .email("email")
                        .build();
        String sql = sqlProvider.update(user);
        assertThat(sql).isEqualTo("UPDATE USERS\n"
                + "SET PASSWORD = #{password}, EMAIL = #{email}, MOBILE = #{mobile}\n"
                + "WHERE (USER_ID = #{id})");
    }

    @Test
    public void shouldReturnDataById() {
        String sql = sqlProvider.getById(map);
        assertThat(sql).isEqualTo("SELECT *\n" + "FROM USERS\n" + "WHERE (USER_ID = #{id})");
    }

    @Test
    public void shouldDeleteById() {
        String sql = sqlProvider.deleteById(map);
        assertThat(sql).isEqualTo("DELETE FROM USERS\n" + "WHERE (USER_ID = #{id})");
    }
}
