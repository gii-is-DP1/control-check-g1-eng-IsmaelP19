package org.springframework.samples.petclinic.vacination;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vaccination")
public class VaccinationController {

    @Autowired
    private VaccinationService vs;

    @Autowired
    private PetService ps;

    @GetMapping(path = "/create") 
    public String createVaccination(ModelMap m) {

        Vaccination vac = new Vaccination();
        m.addAttribute("vaccination", vac);

        List<Vaccine> lv = vs.getAllVaccines();

        m.addAttribute("vaccines", lv);

        List<Pet> lp = ps.findAllPets().stream().collect(Collectors.toList());

        m.addAttribute("pets", lp);
        
        return "vaccination/createOrUpdateVaccinationForm";
    }


    @PostMapping(path = "/create")
    public String saveVaccination(@Valid Vaccination v, BindingResult result, ModelMap model) throws UnfeasibleVaccinationException {
        
        String view = "vaccination/createOrUpdateVaccinationForm";

        if(result.hasErrors()) {
            model.addAttribute("vaccination", v);
            model.addAttribute("vaccines", vs.getAllVaccines());
            model.addAttribute("pets", ps.findAllPets());            

            if(v.getVaccinatedPet().getType() != v.getVaccine().getPetType()) {
                model.addAttribute("error", "La mascota seleccionada no puede recibir la vacuna especificada.");
                return view;
            }
            

            return "welcome";

        } else {

            vs.save(v);

            return "welcome";
            

        }
        
    }



    
}
