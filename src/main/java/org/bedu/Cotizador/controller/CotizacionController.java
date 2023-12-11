    package org.bedu.Cotizador.controller;

    import org.bedu.Cotizador.dto.CotizacionDTO;
    
    import org.bedu.Cotizador.dto.createDTO.CreateCotizacionDTO;

    import org.bedu.Cotizador.mapper.CotizacionMapper;
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

        @Autowired
        private CotizacionMapper cotizacionMapper;

        @PostMapping
        public ResponseEntity<CotizacionDTO> crearCotizacion(@RequestBody CreateCotizacionDTO createCotizacionDTO) {
            CotizacionDTO cotizacionDTO = cotizacionService.crearCotizacion(createCotizacionDTO);
            return new ResponseEntity<>(cotizacionDTO, HttpStatus.CREATED);
        }
    }