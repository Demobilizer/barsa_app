package com.barsa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.barsa.web.rest.TestUtil;

public class PedidoCabeceraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PedidoCabecera.class);
        PedidoCabecera pedidoCabecera1 = new PedidoCabecera();
        pedidoCabecera1.setId(1L);
        PedidoCabecera pedidoCabecera2 = new PedidoCabecera();
        pedidoCabecera2.setId(pedidoCabecera1.getId());
        assertThat(pedidoCabecera1).isEqualTo(pedidoCabecera2);
        pedidoCabecera2.setId(2L);
        assertThat(pedidoCabecera1).isNotEqualTo(pedidoCabecera2);
        pedidoCabecera1.setId(null);
        assertThat(pedidoCabecera1).isNotEqualTo(pedidoCabecera2);
    }
}
