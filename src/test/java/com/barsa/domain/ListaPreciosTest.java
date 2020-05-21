package com.barsa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.barsa.web.rest.TestUtil;

public class ListaPreciosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaPrecios.class);
        ListaPrecios listaPrecios1 = new ListaPrecios();
        listaPrecios1.setId(1L);
        ListaPrecios listaPrecios2 = new ListaPrecios();
        listaPrecios2.setId(listaPrecios1.getId());
        assertThat(listaPrecios1).isEqualTo(listaPrecios2);
        listaPrecios2.setId(2L);
        assertThat(listaPrecios1).isNotEqualTo(listaPrecios2);
        listaPrecios1.setId(null);
        assertThat(listaPrecios1).isNotEqualTo(listaPrecios2);
    }
}
