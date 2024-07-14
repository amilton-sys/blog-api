package br.com.sys.gerencia_api.domain.model.code;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_codes")
@Data
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code_id;
    @Column(nullable = false)
    private int code;
    @CreationTimestamp
    private Instant creation_timestamp;
    private boolean active;
    @Column(nullable = false, unique = true)
    private String email;

    public Long getCode_id() {
        return code_id;
    }

    public void setCode_id(Long code_id) {
        this.code_id = code_id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Instant getCreation_timestamp() {
        return creation_timestamp;
    }

    public void setCreation_timestamp(Instant creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
