package com.IT3180.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.IT3180.dto.BillItemDTO;
import com.IT3180.dto.UserDTO;
import com.IT3180.model.Apartment;
import com.IT3180.model.BillType;
import com.IT3180.model.Resident;
import com.IT3180.services.UserService;
import com.IT3180.services.ApartmentService;
import com.IT3180.services.BillService;
import com.IT3180.services.ResidentService;

@Controller
@RequestMapping ("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private ApartmentService apartmentService;
	@Autowired
	private ResidentService residentService;
	@Autowired
	private BillService billService;
	 @GetMapping("/dashboard")
	    public String dashboard(Model model) 
	 	{
		 	Long numApartment = apartmentService.getTotalApartments();
		 	model.addAttribute("numApartment",numApartment);
		 	Long numResident = residentService.getTotalResidents();
		 	model.addAttribute("numResident", numResident);
		 	Long numUser = userService.getTotalUser();
		 	model.addAttribute("numUser", numUser);
		 	Long numFee = billService.getAllBillType();
		 	model.addAttribute("numFee", numFee);
	        return "admin/admin_dashboard";  
	    }
	 
	@GetMapping("/account")
	public String account (Model model)
	{
		List<Apartment> apartments = apartmentService.getAllApartments();
		model.addAttribute("apartments", apartments);
		List<com.IT3180.model.User> users = userService.getAllUsers();
    	model.addAttribute("users", users);
		return "admin/account";
	}
	
	@GetMapping ("/resident")
	public String resident(Model model)
	{
		List<com.IT3180.model.Apartment> apartments = apartmentService.getAllApartments();
    	model.addAttribute("apartments", apartments);
    	
    	long totalApartments = apartmentService.getTotalApartments();
        model.addAttribute("totalApartments", totalApartments);
        
        long totalResidents = residentService.getTotalResidents();
        model.addAttribute("totalResidents", totalResidents);
        
        long emptyApartments = apartmentService.getEmptyApartmentCount();
        model.addAttribute("emptyApartments", emptyApartments);
        
		return "admin/resident";
	}
	
	 @PostMapping("/account/delete/{id}")
	 public String deleteAccount(@PathVariable Long id) 
	 {
	        userService.deleteUser(id);  
	        return "redirect:/admin/account";  
	 }
	 
	 @PostMapping("/account/save")
	    public String saveUser(@ModelAttribute UserDTO userDto) {

	        userService.saveUser(userDto);
	        return "redirect:/admin/account";
	  }
	 
	 @PostMapping("/apartment/save")
	    public String saveApartment(@RequestParam Long id, 
                @RequestParam(required = false) Long idHouseholder, 
                @RequestParam(required = false) String phone, 
                @RequestParam Double area) {
		 	
		 	Apartment apartment = new Apartment(id, idHouseholder, phone, area);
		    apartmentService.createApartment(apartment);
		    return "redirect:/admin/resident";
	  }
	 
	 @PostMapping("/apartment/update")
	    public String saveApartment(@RequestParam Long idHouseholder, @RequestParam Long id,
             @RequestParam String phone) {
		 	
		    apartmentService.updateApartment(id, idHouseholder, phone);
		    return "redirect:/admin/resident";
	  }
	 
	 @PostMapping("/apartment/delete/{id}")
	 public String deleteApartment(@PathVariable Long id) 
	 {
	        apartmentService.deleteApartment(id);
	        return "redirect:/admin/resident";  
	 }
	 
	 @PostMapping("/resident/save")
	    public String saveApartment(@RequestParam Long apartment_id, @RequestParam String name, 
                @RequestParam String relationshipWithHouseholder, 
                @RequestParam(required = false)  String cccd,
                @RequestParam(required = false)  String email,
                @RequestParam String status) {
		 	
		 	Optional<Apartment> optionalApartment = apartmentService.getApartmentById(apartment_id);
		 	Apartment apartment = optionalApartment.orElseThrow(() -> new RuntimeException("Apartment không tồn tại"));
		 	if (cccd == "") {
		 		cccd = null;
		 	}
		 	if (email == "") {
		 		email = null;
		 	}
		 	Resident resident = new Resident(name, relationshipWithHouseholder, cccd, email,status, apartment);
		 	residentService.createResident(resident);
	        return "redirect:/admin/resident";
	  }
	 
	 	@GetMapping("/resident/{apartmentId}")
	    @ResponseBody
	    public List<Resident> showResidents(@PathVariable Long apartmentId) {
	        List<Resident> residents = residentService.findResidentsByApartmentId(apartmentId);
	        return residents; // Trả về danh sách cư dân dạng JSON
	    }
	 	
	 // Thêm endpoint xóa cư dân qua AJAX
	    @PostMapping("/resident/delete/{residentId}")
	    @ResponseBody
	    public String deleteResident(@PathVariable Long residentId) {
	        residentService.deleteResident(residentId); // Giả sử ResidentService có phương thức này
	        return "success"; // Trả về chuỗi để báo thành công
	    }
	    
		@GetMapping("/billing")
		public String billing (
				@RequestParam(required = false) String billTypeName,
				@RequestParam(required = false) Long apartmentId,
				@RequestParam(required = false) Boolean status,
				@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
				@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
				Model model)
		{
			List<BillType> feeTypes = billService.getAllFeeTypes();
			model.addAttribute("feeTypes", feeTypes);
			List<BillType> contributionTypes = billService.getAllContributionType();
			model.addAttribute("contributionTypes", contributionTypes);
			List<BillType> billTypes = billService.getAllBillTypes();
			model.addAttribute("billTypes", billTypes);
			List<BillItemDTO> billItems = billService.getBillItems(billTypeName, apartmentId, status, fromDate, toDate);
			model.addAttribute("billItems", billItems);
			List<Apartment> apartments = apartmentService.getAllApartments();
			model.addAttribute("apartments", apartments);
			return "admin/billing";
		}

		@PostMapping("/billing/addFeeType")
		public String addFeeType(@ModelAttribute BillType billType) {
			billType.setIsContribution(false);
			billService.addBillType(billType);
			return "redirect:/admin/billing";
		}

		@PostMapping("/billing/addContributionType")
		public String addContributionType(@ModelAttribute BillType billType) {
			billType.setIsContribution(true);
			billService.addBillType(billType);
			return "redirect:/admin/billing";
		}

		@PostMapping("/billing/type/delete/{id}")
		public String deleteBillType(@PathVariable Long id)
		{
			billService.deleteBillType(id);
			return "redirect:/admin/billing";
		}

		@PostMapping("/billing/addBillItem")
		public String addBillItem(@ModelAttribute BillItemDTO billItemDTO) {
			if (billItemDTO.getStatus() == null) {
				billItemDTO.setStatus(false);
			}
			billService.addBillItem(billItemDTO);
			System.out.println(billItemDTO.getApartmentId());
			System.out.println(billItemDTO.getBillTypeName());
			System.out.println(billItemDTO.getStatus());
			System.out.println(billItemDTO.getDueDate());
			return "redirect:/admin/billing";
		}

		@PostMapping("/billing/item/delete/{id}")
		public String deleteBillItem(@PathVariable Long id)
		{
			billService.deleteBillItem(id);
			return "redirect:/admin/billing";
		}

   
}
