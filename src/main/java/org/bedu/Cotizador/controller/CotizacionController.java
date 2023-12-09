    package org.bedu.Cotizador.controller;

    import org.bedu.Cotizador.dto.CotizacionDTO;
    
    import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;
   
    import org.bedu.Cotizador.service.CotizacionService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    

    @RestController
    @RequestMapping("/cotizaciones")
    public class CotizacionController {

        @Autowired
        private CotizacionService cotizacionService;

        @PostMapping
        public ResponseEntity<CotizacionDTO> guardarCotizacion(@RequestBody CreateCotizacionDTO createCotizacionDTO) {
            CotizacionDTO cotizacionDTO = cotizacionService.guardarCotizacionParaCliente(createCotizacionDTO);
            return new ResponseEntity<>(cotizacionDTO, HttpStatus.CREATED);
        }

        @GetMapping("/{cotizacionId}")
        public ResponseEntity<CotizacionDTO> obtenerCotizacion(@PathVariable Long cotizacionId) {
            CotizacionDTO cotizacionDTO = cotizacionService.obtenerCotizacion(cotizacionId);
            return new ResponseEntity<>(cotizacionDTO, HttpStatus.OK);
        }

        @PutMapping("/{cotizacionId}")
        public ResponseEntity<Void> completarCotizacion(@PathVariable Long cotizacionId) {
            cotizacionService.completarCotizacion(cotizacionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @DeleteMapping("/{cotizacionId}")
        public ResponseEntity<Void> eliminarCotizacion(@PathVariable Long cotizacionId) {
            cotizacionService.eliminarCotizacion(cotizacionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Otros endpoints según sea necesario
    }