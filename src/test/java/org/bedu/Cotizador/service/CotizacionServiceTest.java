package org.bedu.Cotizador.service;

import org.bedu.Cotizador.dto.CotizacionDTO;
import org.bedu.Cotizador.dto.ItemCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
import org.bedu.Cotizador.dto.createDTO.CreateItemCotizacionDTO;
import org.bedu.Cotizador.model.Cotizacion;
import org.bedu.Cotizador.repository.CotizacionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CotizacionServiceTest {

    @Autowired
    private CotizacionService service;

    @MockBean
    private CotizacionRepository cotizacionRepository;

    @MockBean
    private ItemCotizacionService itemCotizacionService;

    @Test
    @DisplayName("Service should be injected")
    void smokeTest() {
        assertNotNull(service);
    }

    @Test
    @DisplayName("Service should save a quotation")
    void saveQuotationTest() {
        when(cotizacionRepository.saveAndFlush(any(Cotizacion.class))).thenAnswer((params) -> {
            Cotizacion newQuotation = (Cotizacion) params.getArgument(0);
            newQuotation.setId(50);
            return newQuotation;
        });

        ItemCotizacionDTO item = new ItemCotizacionDTO();
        item.setSubtotal(new BigDecimal(200));
        when(itemCotizacionService.addItemCotizacion(any(), any())).thenReturn(item);

        when(cotizacionRepository.save(any(Cotizacion.class))).thenAnswer((params) -> {
            return params.getArgument(0);
        });

        CreateCotizacionDTO dto = new CreateCotizacionDTO();
        List<CreateItemCotizacionDTO> items = new LinkedList<>();

        items.add(new CreateItemCotizacionDTO());
        items.add(new CreateItemCotizacionDTO());

        dto.setClienteId(6785l);
        dto.setItems(items);

        CotizacionDTO result = service.crearCotizacion(dto);

        assertEquals(50, result.getId());
        assertEquals(new BigDecimal(400), result.getTotal());
    }
}
