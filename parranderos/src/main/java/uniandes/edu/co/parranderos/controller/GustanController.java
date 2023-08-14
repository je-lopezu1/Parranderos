package uniandes.edu.co.parranderos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import uniandes.edu.co.parranderos.modelo.Bebedor;
import uniandes.edu.co.parranderos.modelo.Bebida;
import uniandes.edu.co.parranderos.modelo.Gustan;
import uniandes.edu.co.parranderos.modelo.GustanPK;
import uniandes.edu.co.parranderos.repositorio.BebedorRepository;
import uniandes.edu.co.parranderos.repositorio.BebidaRepository;
import uniandes.edu.co.parranderos.repositorio.GustanRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GustanController {

    @Autowired
    private BebedorRepository bebedorRepository;

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private GustanRepository gustanRepository;

    @GetMapping("/gustan/new")
    public String gustanForm(Model model) {
        model.addAttribute("bebidas", bebidaRepository.darBebidas());
        model.addAttribute("bebedores", bebedorRepository.darBebedores());
        return "gustanNuevo";
    }

    @PostMapping("/gustan/new/save")
    public String gustanGuardar(@ModelAttribute("id_bebida") Long idBebida,
            @ModelAttribute("id_bebedor") Long idBebedor) {

        Bebedor bebedor = bebedorRepository.darBebedor(idBebedor);
        Bebida bebida = bebidaRepository.darBebida(idBebida);
        GustanPK pk = new GustanPK(bebedor, bebida);
        Gustan gustan = new Gustan();
        gustan.setPk(pk);
        gustanRepository.insertarGustan(gustan.getPk().getId_bebedor().getId(), gustan.getPk().getId_bebida().getId());
        return "redirect:/bebidas";
    }
}
