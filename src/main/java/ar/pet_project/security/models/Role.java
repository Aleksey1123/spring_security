package ar.pet_project.security.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @TableGenerator(
            name="AutoIncrement",
            allocationSize=1)

    @Id
    @GeneratedValue(generator = "AutoIncrement")
    @Column(name = "role_id")
    private Integer roleId;

    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
