package com.management.app.Controllers;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.management.app.Entity.Device;
import com.management.app.Entity.Institution;
import com.management.app.Entity.Notification;
import com.management.app.Entity.User;
import com.management.app.Security.CustomUserDetails;
import com.management.app.Service.IDeviceService;
import com.management.app.Service.IInstitutionService;
import com.management.app.Service.INotificationService;
import com.management.app.Service.IUserService;
import com.management.app.Service.SendEmail;

@Controller
@RequestMapping
public class NotificationController {

	@Autowired
	private IUserService UserService;
	
	@Autowired
	private IDeviceService DeviceService;
	
	@Autowired
	private INotificationService NotificationService;
	
	@Autowired
	private IInstitutionService InstitutionService;
	
	@Autowired
	private SendEmail sendEmail;
	
	@GetMapping("/newnotification/{id}")
	public String newnoti(@PathVariable(value = "id")Long id,Map<String,Object>map, Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		Notification notification = new Notification();
		Institution institution = InstitutionService.findOne(id);
		List<Device> devices = DeviceService.findbyInstitution(id);
		model.addAttribute("devices", devices);
		model.addAttribute("institution",institution);
		map.put("notification", notification);
		
		return "newnotification";
		
	}
	
	@PostMapping("/newnotification/{id}")
	public String savenoti(@PathVariable(value = "id")Long id,    		
    		@Valid Notification notification,
    		@RequestParam(value = "equipo",defaultValue = "1")Long idequipo,
    								  BindingResult result,
    								  Model model,
    								  RedirectAttributes flash,
    								  SessionStatus status) {
		Device device = DeviceService.findOne(idequipo);
		LocalDateTime now = LocalDateTime.now();
		Timestamp today = Timestamp.valueOf(now);
		Authentication auth = SecurityContextHolder
			    .getContext()
			    .getAuthentication();
		CustomUserDetails userDetail = (CustomUserDetails) auth.getPrincipal();
		User user = UserService.findbyname(userDetail.getUsername());
		notification.setTypes(device.getType());
		notification.setCaller(user.getNombre()+" "+user.getApellido());
		notification.setDatecall(today);
		notification.setDevice(device);
		notification.setServices(device.getService());
		notification.setState(0);
		notification.setUsercall(user);
		
	
		sendEmail.sendEmail("biomedicahusrt@gmail.com", device.getInstitution().getNombre()+": "+ device.getName_device()+" "+device.getMarca()+" "+device.getModelo()+" "+device.getSerie(), notification.getDescription());
		
		
		
		flash.addAttribute("realizado", "Llamado realizado con exito");
		
		return "redirect:/servicellamados";
	}
	@PostMapping("/assignotification/{id}")
	public String savenoti(@PathVariable(value = "id")Long id,BindingResult result,
			  @RequestParam(value = "user",defaultValue = "1")Long iduser,
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		Notification notification = NotificationService.findOne(id);
		notification.setUserrta(UserService.findOne(iduser));
		notification.setState(1);
		return "redirect:/listnotification";
	}
	@PostMapping("/attend/{id}")
	public String savenoti(@PathVariable(value = "id")Long id,BindingResult result,
			Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		return "redirect:/listnotification";
	}
}
