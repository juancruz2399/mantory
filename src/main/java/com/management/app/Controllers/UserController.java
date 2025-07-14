package com.management.app.Controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Authority;
import com.management.app.Entity.User;
import com.management.app.Service.IAuthorityService;
import com.management.app.Service.IUserService;

@Controller
@RequestMapping 
public class UserController {

	@Autowired
    private IUserService UserService;
	
	@Autowired
	private IAuthorityService AuthorityService;
	
	@GetMapping("/users")
    public String users(Model model){
        model.addAttribute("titulo","Listar Usuarios");
        model.addAttribute("usuarios", UserService.listUsers());
        return "users";
    }
	@GetMapping("/createuser")
    public String crearusuarios(Model model,Map<String, Object> map) {
    	User user = new User();
    	List<Authority> roles = new ArrayList<Authority>();
    	roles = AuthorityService.lisAuthorities();
    	map.put("user",user);
    	model.addAttribute("auths", roles);
    	return "newuser";
    }

	@PostMapping("/createuser")
    public String guardar(@Valid User user,
            BindingResult result,
            @RequestParam(value = "password",defaultValue = "123456")String password,
            @RequestParam(value = "auths",defaultValue = "NONE")String auth,
            Model model,
            RedirectAttributes flash,
            SessionStatus status) {
			
    		if(password.equals("123456")||auth.equals("NONE")) {
    			
    			flash.addAttribute("Clave", "Clave no valida o rol no asignado");
    			return "redirect:/users";
    		}
    		else {
    			Set<Authority> roles = new HashSet<>();
    			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    			String rawPassword = password;
    			String encodedPassword = encoder.encode(rawPassword);
    			
    			user.setPassword(encodedPassword);
    			user.setEstado(true);
    			roles.add(AuthorityService.findOne(Long.valueOf(auth)));
    			user.setRoles(roles);
    			UserService.save(user);
    			return "redirect:/users";
    		}
			
	}
	@GetMapping("/edituser/{id}")
	public String editusers(@PathVariable(value = "id")Long id, 
			Model model,Map<String, Object> map,BindingResult result,
			  RedirectAttributes flash,
			  SessionStatus status) {
		User user = UserService.findOne(id);
    	List<Authority> roles = new ArrayList<Authority>();
    	roles = AuthorityService.lisAuthorities();
    	map.put("user",user);
    	model.addAttribute("auths", roles);
		return "edituser";
	}
	@PostMapping("/edituser/{id}")
	public String saveditusers(@PathVariable(value = "id")Long id, 
			@Valid User user,
			Model model,Map<String, Object> map,BindingResult result,
			@RequestParam(value = "password",defaultValue = "123456")String password,
            @RequestParam(value = "auths",defaultValue = "NONE")String auth,
			  RedirectAttributes flash,
			  SessionStatus status) {
		User useredit = UserService.findOne(id);
		if(password.equals("123456")||auth.equals("NONE")) {
			
			flash.addAttribute("Clave", "Clave no valida o rol no asignado");
			return "redirect:/users";
		}
		else {
			Set<Authority> roles = new HashSet<>();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = password;
			String encodedPassword = encoder.encode(rawPassword);
			useredit.setApellido(user.getApellido());
			useredit.setCargo(user.getCargo());
			useredit.setEstado(user.isEstado());
			useredit.setInstitution(user.getInstitution());//obs
			useredit.setNombre(user.getNombre());
			useredit.setPassword(encodedPassword);
			useredit.setEstado(true);
			roles.add(AuthorityService.findOne(Long.valueOf(auth)));
			useredit.setRoles(roles);
			UserService.save(user);
			return "redirect:/users";
		}

	}
	
	
}
