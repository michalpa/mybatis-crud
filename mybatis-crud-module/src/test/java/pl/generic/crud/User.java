package pl.generic.crud;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.generic.crud.annotation.ColumnDB;
import pl.generic.crud.annotation.Id;
import pl.generic.crud.annotation.Table;

@Data
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class User {

    public User() {
        // empty constructor
    }

    @Id
    @ColumnDB(name = "USER_ID")
    private Integer id;

    @ColumnDB(name = "EMAIL")
    private String email;

    private String name;

    private String surname;

    @ColumnDB(name = "MOBILE")
    private String mobile;

    @ColumnDB(name = "PASSWORD")
    private String password;

    private Date creationDate;

    private boolean verified;

    private boolean removed;
}