package uniandes.edu.co.parranderos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.parranderos.modelo.Bar;
import uniandes.edu.co.parranderos.repositorio.BarRepository;
import uniandes.edu.co.parranderos.repositorio.Tipo_bebidaRepository;

import org.springframework.ui.Model;


@Controller
public class BaresController {

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private Tipo_bebidaRepository tipo_bebidaRepository;

    @GetMapping("/bares")
    public String bares(Model model, String ciudad, String tipo) {
        int NumeroDeBaresQueSirvenBebidasConMayorGradoAlcohol = barRepository
                .darNumeroDeBaresQueSirvenBebidasConMayorGradoAlcohol();
        int NumeroDeBaresQueSirvenBebidasConMenorGradoAlcohol = barRepository
                .darNumeroDeBaresQueSirvenBebidasConMenorGradoAlcohol();
        model.addAttribute("NumeroDeBaresQueSirvenBebidasConMayorGradoAlcohol",
                NumeroDeBaresQueSirvenBebidasConMayorGradoAlcohol);
        model.addAttribute("NumeroDeBaresQueSirvenBebidasConMenorGradoAlcohol",
                NumeroDeBaresQueSirvenBebidasConMenorGradoAlcohol);
        model.addAttribute("tipos", tipo_bebidaRepository.darTipos_bebida());

        if((ciudad == null || ciudad.equals("")) || (tipo ==null || tipo.equals("")))
        {
            model.addAttribute("bares", barRepository.darBares());
        }
        else{
            model.addAttribute("bares", barRepository.darBaresPorCiudadYTipoBebida(ciudad, tipo));
        }

        return "bares";
    }

    @GetMapping("/bares/new")
    public String barForm(Model model) {
        model.addAttribute("bar", new Bar());
        return "barNuevo";
    }

    @PostMapping("/bares/new/save")
    public String barGuardar(@ModelAttribute Bar bar) {
        barRepository.insertarBar(bar.getNombre(), bar.getCiudad(), bar.getPresupuesto(), bar.getCant_sedes());
        return "redirect:/bares";
    }

    @GetMapping("/bares/{id}/edit")
    public String barEditarForm(@PathVariable("id") int id, Model model) {
        Bar bar = barRepository.darBar(id);
        if (bar != null) {
            model.addAttribute("bar", bar);
            return "barEditar";
        } else {
            return "redirect:/bares";
        }
    }

    @PostMapping("/bares/{id}/edit/save")
    public String barEditarGuardar(@PathVariable("id") long id, @ModelAttribute Bar bar) {
        barRepository.actualizarBar(((long) id), bar.getNombre(), bar.getCiudad(), bar.getPresupuesto(),
                bar.getCant_sedes());
        return "redirect:/bares";
    }

    @GetMapping("/bares/{id}/delete")
    public String barEliminar(@PathVariable("id") long id) {
        barRepository.eliminarBar(id);
        return "redirect:/bares";
    }

}