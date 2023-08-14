package uniandes.edu.co.parranderos.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.parranderos.modelo.Bebida;
import uniandes.edu.co.parranderos.repositorio.BebidaRepository;
import uniandes.edu.co.parranderos.repositorio.Tipo_bebidaRepository;


@Controller
public class BebidasController {

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private Tipo_bebidaRepository tipo_bebidaRepository;

    @GetMapping("/bebidas")
    public String bebidas(Model model, String ciudad, String minGrado, String maxGrado) {
        model.addAttribute("bebidas", bebidaRepository.darBebidas());
        return "bebidas";
    }

    @GetMapping("/bebidas/new")
    public String bebidaForm(Model model) {
        model.addAttribute("bebida", new Bebida());
        model.addAttribute("tipos", tipo_bebidaRepository.darTipos_bebida());
        return "bebidaNuevo";
    }

    @PostMapping("/bebidas/new/save")
    public String bebidaGuardar(@ModelAttribute Bebida bebida) {
        bebidaRepository.insertarBebida(bebida.getNombre(), bebida.getGrado_alcohol(), bebida.getTipo().getId());
        return "redirect:/bebidas";
    }

    @GetMapping("/bebidas/{id}/edit")
    public String bebidaEditarForm(@PathVariable("id") long id, Model model) {
        Bebida bebida = bebidaRepository.darBebida(id);
        if (bebida != null) {
            model.addAttribute("bebida", bebida);
            model.addAttribute("tipos", tipo_bebidaRepository.darTipos_bebida());
            return "bebidaEditar";
        } else {
            return "redirect:/bebidas";
        }
    }

    @PostMapping("/bebidas/{id}/edit/save")
    public String bebidaEditarGuardar(@PathVariable("id") long id, @ModelAttribute Bebida bebida) {
        bebidaRepository.actualizarBebida(((long) id), bebida.getNombre(), bebida.getGrado_alcohol(),
                bebida.getTipo().getId());
        return "redirect:/bebidas";
    }

    @GetMapping("/bebidas/{id}/delete")
    public String bebidaBorrar(@PathVariable("id") long id) {
        bebidaRepository.eliminarBebida(id);
        return "redirect:/bebidas";
    }

}
