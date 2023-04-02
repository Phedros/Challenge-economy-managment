package challenge.economy.management.demo.controller;

import challenge.economy.management.demo.domain.InformeRequest;
import challenge.economy.management.demo.domain.InformeResponse;
import challenge.economy.management.demo.model.entity.Factura;
import challenge.economy.management.demo.service.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factura")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;

    @PostMapping("/guardar")
    public void guardar(@RequestBody Factura factura){
        facturaService.save(factura);
    }

    @GetMapping("/listar")
    public List<Factura> listar() {
        return facturaService.findAll();
    }

    @GetMapping("/listar/{id}")
    public Factura listarPorId(@PathVariable Integer id){
        return facturaService.findById(id);
    }

    @DeleteMapping("/borrar/{id}")
    public void borrar(@PathVariable Integer id){
        facturaService.delete(id);
    }

    @GetMapping("/informe")
    public InformeResponse gastoPeriodo(@RequestBody InformeRequest solicitudInformePeriodo){
        return facturaService.informePeriodo(solicitudInformePeriodo);
    }
    @GetMapping("/impagas")
    public List<Factura> listarFacturasImpagas(){
        return facturaService.listarFacturasImpagas();
    }

//    @GetMapping("/empresa")
//    public List<Factura> listarFacturasPorEmpresa(@RequestBody String empresa){
//        return facturaService.listarFacturasPorEmpresa(empresa);
//    }

    @GetMapping("/empresa/{empresa}")
    public List<Factura> listarPorEmpresa(@PathVariable String empresa) {
        return facturaService.listarPorEmpresa(empresa);
    }

//    @GetMapping("/propietario")
//    public List<Factura> listarFacturasPorPropietario(@RequestBody String propietario) {
//        return facturaService.listarFacturasPorPropietario(propietario);
//    }

    @GetMapping("/propietario/{propietario}")
    public List<Factura> listarPorPropietario(@PathVariable String propietario) {
        return facturaService.listarPorPropietario(propietario);
    }

    @GetMapping("/vencidas")
    public List<Factura> listarFacturasVencidas(){
        return facturaService.listarFacturasVencidas();
    }

}
