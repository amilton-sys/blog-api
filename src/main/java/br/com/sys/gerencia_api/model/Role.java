package br.com.sys.gerencia_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tb_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    private String name;

    @Getter
    public enum Values {
        ADMIN(1L),
        BASIC(2L);
        final long roleId;

        Values(long roleId) {
            this.roleId = roleId;
        }
    }
}
