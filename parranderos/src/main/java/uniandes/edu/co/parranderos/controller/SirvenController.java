package uniandes.edu.co.parranderos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import uniandes.edu.co.parranderos.modelo.Bar;
import uniandes.edu.co.parranderos.modelo.Bebida;
import uniandes.edu.co.parranderos.modelo.Sirven;
import uniandes.edu.co.parranderos.modelo.SirvenPK;
import uniandes.edu.co.parranderos.repositorio.BarRepository;
import uniandes.edu.co.parranderos.repositorio.BebidaRepository;
import uniandes.edu.co.parranderos.repositorio.SirvenRepository;

@Controller
public class SirvenController {

    @Autowired
    private SirvenRepository sirvenRepository;

    @Autowired
    private BarRepository barRepository;

    @Autowired
    private BebidaRepository bebidaRepository;

    @GetMapping("/sirven/new")
    public String sirvenForm(Model model) {
        model.addAttribute("bares", barRepository.darBares());
        model.addAttribute("bebidas", bebidaRepository.darBebidas());
        return "sirvenNuevo";
    }

    @PostMapping("/sirven/new/save")
    public String sirvenGuardar(@ModelAttribute("id_bar") int idBar,
            @ModelAttribute("id_bebida") Long idBebida, @ModelAttribute("horario") String horario) {

        Bar bar = barRepository.darBar(idBar);
        Bebida bebida = bebidaRepository.darBebida(idBebida);
        SirvenPK pk = new SirvenPK(bar, bebida, horario);
        Sirven sirven = new Sirven();
        sirven.setPk(pk);
        sirvenRepository.insertarSirven(sirven.getPk().getId_bar().getId(), sirven.getPk().getId_bebida().getId(),
                sirven.getPk().getHorario());
        return "redirect:/bares";
    }

}
