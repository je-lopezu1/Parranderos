package uniandes.edu.co.parranderos.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.parranderos.modelo.Bar;
import uniandes.edu.co.parranderos.modelo.Bebedor;
import uniandes.edu.co.parranderos.modelo.Frecuentan;
import uniandes.edu.co.parranderos.modelo.FrecuentanPK;
import uniandes.edu.co.parranderos.repositorio.BarRepository;
import uniandes.edu.co.parranderos.repositorio.BebedorRepository;
import uniandes.edu.co.parranderos.repositorio.FrecuentanRepository;

@Controller
public class FrecuentanController {

    @Autowired
    private BebedorRepository bebedorRepository;

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private FrecuentanRepository frecuentanRepository;

    @GetMapping("/frecuentan/new")
    public String frecuentanForm(Model model) {
        model.addAttribute("bares", barRepository.darBares());
        model.addAttribute("bebedores", bebedorRepository.darBebedores());
        return "frecuentanNuevo";
    }

    @PostMapping("/frecuentan/new/save")
    public String frecuentanGuardar(@ModelAttribute("id_bar") int idBar, @ModelAttribute("id_bebedor") Long idBebedor,
            @ModelAttribute("fecha_visita") Date fecha, @ModelAttribute("horario") String horario) {

        Bebedor bebedor = bebedorRepository.darBebedor(idBebedor);
        Bar bar = barRepository.darBar(idBar);
        FrecuentanPK pk = new FrecuentanPK(bebedor, bar, fecha, horario);
        Frecuentan frecuentan = new Frecuentan();
        frecuentan.setPk(pk);
        frecuentanRepository.insertarFrecuentan(frecuentan.getPk().getId_bebedor().getId(),
                frecuentan.getPk().getId_bar().getId(), frecuentan.getPk().getFecha_visita(),
                frecuentan.getPk().getHorario());
        return "redirect:/bebedores";
    }

}
