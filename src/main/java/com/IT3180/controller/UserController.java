package com.IT3180.controller;

import java.util.List;

import com.IT3180.dto.BillItemDTO;
import com.IT3180.model.User;
import com.IT3180.model.BillType;
import com.IT3180.services.UserService;
import com.IT3180.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.IT3180.services.NotificationsServices;
import com.IT3180.model.Notifications;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping ("/user")
public class UserController {
    @Autowired
    private BillService billService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationsServices notificationsServices;

    @GetMapping("/dashboard")
    public String dashboard(Model model) 
 	{
    	List<Notifications> notifications = notificationsServices.getAllNotifications();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Notifications notification : notifications) {
            // Chuyển đổi LocalDateTime thành String đã định dạng
            notification.setFormatDate(notification.getCreatedAt().format(formatter));
        }
    	model.addAttribute("notifications", notifications);
        return "user/user_dashboard";  
    } 

    @GetMapping("/billing")
    public String billing (
            @AuthenticationPrincipal UserDetails userDetails,
            Model model)
    {
        List<BillType> feeTypes = billService.getAllFeeTypes();
        model.addAttribute("feeTypes", feeTypes);
        List<BillType> contributionTypes = billService.getAllContributionType();
        model.addAttribute("contributionTypes", contributionTypes);

        String name = userDetails.getUsername();
        User user = userService.findUserByName(name);

        List<BillItemDTO> billItems = billService.getBillItems(null, user.getApartment().getId(), false, null, null);
        model.addAttribute("billItems", billItems);

        List<BillItemDTO> billItemsDone = billService.getBillItems(null, user.getApartment().getId(), true, null, null);
        model.addAttribute("billItemsDone", billItemsDone);
        return "user/billing";
    }

    @GetMapping("/billing/fee")
    public String fee (
        @AuthenticationPrincipal UserDetails userDetails,
        Model model)
    {
        List<BillType> feeTypes = billService.getAllFeeTypes();
        model.addAttribute("feeTypes", feeTypes);

        String name = userDetails.getUsername();
        User user = userService.findUserByName(name);

        List<BillItemDTO> feeItems = billService.getFeeItems(null, user.getApartment().getId(), false, null, null);
        model.addAttribute("feeItems", feeItems);

        List<BillItemDTO> feeItemsDone = billService.getFeeItems(null, user.getApartment().getId(), true, null, null);
        model.addAttribute("feeItemsDone", feeItemsDone);
        return "user/fee";
    }

	@GetMapping("/billing/contribution")
	public String contribution (
        @AuthenticationPrincipal UserDetails userDetails,
		Model model)
	{
		List<BillType> contributionTypes = billService.getAllContributionType();
		model.addAttribute("contributionTypes", contributionTypes);

        String name = userDetails.getUsername();
        User user = userService.findUserByName(name);

		List<BillItemDTO> contributionItems = billService.getContributionItems(null, user.getApartment().getId(), null, null, null);
		model.addAttribute("contributionItems", contributionItems);
		return "user/contribution";
	}
    
    @GetMapping("/notifications")
    public String notifications(Model model) {
    	List<Notifications> notifications = notificationsServices.getAllNotifications();
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Notifications notification : notifications) {
            // Chuyển đổi LocalDateTime thành String đã định dạng
            notification.setFormatDate(notification.getCreatedAt().format(formatter));
        }
    	model.addAttribute("notifications", notifications);
    	return "user/notifications";
    }
}

