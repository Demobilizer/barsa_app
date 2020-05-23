package com.barsa.repository;

import com.barsa.domain.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositoryExtended extends ClienteRepository {
    Cliente findByNoIdentificacion(Integer noIdentification);
}
