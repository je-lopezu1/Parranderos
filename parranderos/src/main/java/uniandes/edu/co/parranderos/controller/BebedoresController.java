package uniandes.edu.co.parranderos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.parranderos.modelo.Bebedor;
import uniandes.edu.co.parranderos.repositorio.BebedorRepository;

@Controller
public class BebedoresController {

    @Autowired
    private BebedorRepository bebedorRepository;


    @GetMapping("/bebedores")
    public String bebedores(Model model, String nombre) {
        
        model.addAttribute("bebedores", bebedorRepository.darBebedores());
        return "bebedores";
    }

    @GetMapping("/bebedores/new")
    public String bebedorForm(Model model) {
        model.addAttribute("bebedor", new Bebedor());
        return "bebedorNuevo";
    }

    @PostMapping("/bebedores/new/save")
    public String bebedorGuardar(@ModelAttribute Bebedor bebedor) {
        bebedorRepository.insertarBebedor(bebedor.getNombre(), bebedor.getCiudad(), bebedor.getPresupuesto());
        return "redirect:/bebedores";
    }

    @GetMapping("/bebedores/{id}/edit")
    public String bebedorEditarForm(@PathVariable("id") long id, Model model) {
        Bebedor bebedor = bebedorRepository.darBebedor(id);
        if (bebedor != null) {
            model.addAttribute("bebedor", bebedor);
            return "bebedorEditar";
        } else {
            return "redirect:/bebedores";
        }
    }

    @PostMapping("/bebedores/{id}/edit/save")
    public String bebedorEditarGuardar(@PathVariable("id") long id, @ModelAttribute Bebedor bebedor) {
        bebedorRepository.actualizarBebedor(((long) id), bebedor.getNombre(), bebedor.getCiudad(),
                bebedor.getPresupuesto());
        return "redirect:/bebedores";
    }

    @GetMapping("/bebedores/{id}/delete")
    public String bebedorBorrar(@PathVariable("id") long id) {
        bebedorRepository.eliminarBebedor(id);
        return "redirect:/bebedores";
    }

}
