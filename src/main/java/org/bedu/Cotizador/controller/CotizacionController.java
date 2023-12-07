    package org.bedu.Cotizador.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.bedu.Cotizador.dto.CotizacionDTO;
    import org.bedu.Cotizador.dto.FacturaDTO;
    import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
    import org.bedu.Cotizador.service.ClienteService;
    import org.bedu.Cotizador.service.CotizacionService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/cotizaciones")
    @Slf4j
    public class CotizacionController {
        @Autowired
        private CotizacionService cotizacionService;

        @Autowired
        private ClienteService clienteService;

        // Obtener cotizaciones de un cliente por ID
        @GetMapping("/cliente/{clienteId}")
        @ResponseStatus(HttpStatus.OK)
        public List<CotizacionDTO> getCotizacionesByClienteId(@PathVariable Long clienteId) {
            log.info("Obteniendo cotizaciones para el cliente con id: {}", clienteId);
            return clienteService.getCotizacionesByClienteId(clienteId);
        }

        // Crear una nueva cotización
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public CotizacionDTO crearCotizacion(@RequestBody CreateCotizacionDTO cotizacionDTO) {
            log.info("Creando nueva cotización");
            return cotizacionService.save(cotizacionDTO);
        }

        // Obtener detalles de una cotización por ID
        @GetMapping("/{cotizacionId}")
        @ResponseStatus(HttpStatus.OK)
        public CotizacionDTO getCotizacionById(@PathVariable Long cotizacionId) {
            log.info("Obteniendo detalles de la cotización con id: {}", cotizacionId);
            return cotizacionService.getCotizacionById(cotizacionId);
        }

        // Eliminar una cotización por ID
        @DeleteMapping("/{cotizacionId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteCotizacion(@PathVariable Long cotizacionId) {
            log.info("Eliminando la cotización con id: {}", cotizacionId);
            cotizacionService.deleteCotizacion(cotizacionId);
        }

        // Generar factura para una cotización por ID
        @PostMapping("/{cotizacionId}/factura")
        @ResponseStatus(HttpStatus.CREATED)
        public FacturaDTO generarFactura(@PathVariable Long cotizacionId) {
            log.info("Generando factura para la cotización con id: {}", cotizacionId);
            return cotizacionService.generarFactura(cotizacionId);
        }
    }


