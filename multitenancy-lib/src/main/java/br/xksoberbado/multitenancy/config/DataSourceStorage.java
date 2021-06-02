package br.xksoberbado.multitenancy.config;

import javax.sql.DataSource;

public interface DataSourceStorage {

    DataSource get(String tenant);
}
