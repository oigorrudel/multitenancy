package br.xksoberbado.multitenancy.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "DATA_SOURCE_CONFIGS")
public class DataSourceConfig {

    @Id
    private String tenant;

    @Column(name = "DRIVER_CLASS_NAME")
    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
