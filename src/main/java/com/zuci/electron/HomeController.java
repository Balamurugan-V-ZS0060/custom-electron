package com.zuci.electron;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zuci.electron.config.AppConfig;
import com.zuci.electron.request.ForgetPwdRequest;
import com.zuci.electron.request.RecreatePwdRequest;
import com.zuci.electron.request.ResetPwdRequest;
import com.zuci.electron.response.ForgetPwdResponse;
import com.zuci.electron.response.Response;
import com.zuci.electron.utils.Utils;
import com.zuci.electron.websocket.OutputMessage;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	FindByIndexNameSessionRepository<? extends Session> sessions;

	@Autowired
	public AppConfig appConfig;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@RequestMapping("/")
	public ModelAndView index(Principal principal, ModelMap model, HttpServletRequest request) {
		System.out.println("HomeController.index() : "+request.getSession(false).getId());
		if (checkIfUserHasAlreadyLoggedIn(principal)) {
			return new ModelAndView("redirect:/alreadyLoggedIn", model);
		}

		return new ModelAndView("home", model);
	}
	
	@RequestMapping("/clearprevioussession")
	public String clearPreviousSession(Principal principal, ModelMap model, HttpServletRequest request) {
		OutputMessage out = new OutputMessage(principal.getName(), "LOGOUT",
				new SimpleDateFormat("HH:mm").format(new Date()));
		simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/message", out);
		
		return "home.html";
	}

	@RequestMapping("/clearcurrentsession")
	public String clearCurrentSession(Principal principal, ModelMap model, HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "login.html";
	}
	
	@RequestMapping("/loginerror")
	public String loginError() {
		return "loginerror.html";
	}

	@RequestMapping("/applogin")
	public String applogin() {
		return "applogin.html";
	}

	@RequestMapping("/home")
	public ModelAndView home(Principal principal, ModelMap model, @RequestHeader("Referer") String referer, HttpServletRequest request) {
		System.out.println("HomeController.home() : "+request.getSession(false).getId());
		System.out.println("HomeController.home() : "+referer);
		if (checkIfUserHasAlreadyLoggedIn(principal)) {
			return new ModelAndView("redirect:/alreadyLoggedIn", model);
		}
		return new ModelAndView("home", model);
	}

	@RequestMapping(value = "/alreadyLoggedIn", method = RequestMethod.GET)
	public String alreadyLoggedIn(Model model) {
		return "alreadyloggedin.html";
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "alreadyLoggedIn", required = false) String alreadyLoggedIn, Model model) {
		String message = null;
		if (error != null) {
			message = "Username or Password is incorrect !!";
		}
		if (logout != null) {
			message = "You have been successfully logged out !!";
		}
		if (alreadyLoggedIn != null) {
			message = "There is a session already present !!";
		}
		model.addAttribute("message", message);
		return "login.html";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(Model model) {
		System.out.println("HomeController.logoutPage()");
		model.addAttribute("message", "You have been successfully logged out !!");
		return "login.html";
	}

	@RequestMapping(value = "/forgetpwd", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity<Response> forgetPwd(@RequestBody ForgetPwdRequest request) {
		// model.addAttribute("message", "Mail sent to : "+request.getEmailId());
		try {
			Utils.sendForgetPasswordMail(appConfig, request.getEmailId());
			ForgetPwdResponse fPwdRes = new ForgetPwdResponse("0000", "Success");

			fPwdRes.setMessage("Password reset link is sent to your mail id plese check.");
			return ResponseEntity.ok(fPwdRes);
		} catch (Exception ex) {
			logger.error("Exception in forget password", ex);
			ForgetPwdResponse fPwdRes = new ForgetPwdResponse("0001", "Failure");
			fPwdRes.setMessage("Unable to send link to reset the password. Please contact help desk");
			return ResponseEntity.ok(fPwdRes);
		}

	}

	@RequestMapping("/recreatepwd")
	public String recreatePwd(Model model) {
		model.addAttribute("recreatePwdRequest", new RecreatePwdRequest());
		return "recreatepwd.html";
	}

	@RequestMapping(value = "/processrecreatepwd", method = RequestMethod.POST)
	public String processRecreatePwd(@ModelAttribute RecreatePwdRequest recreatePwdRequest, Model model) {
		try {
			Optional<User> userOpt = userRepo.findByEmailId(recreatePwdRequest.getEmailid());
			if (userOpt.isPresent()) {
				User user = userOpt.get();
				if (!recreatePwdRequest.getConfirmpassword().equals(recreatePwdRequest.getNewpassword())) {
					model.addAttribute("message", "New and confirm passwords is not matching. Please check");
					model.addAttribute("code", "1");
					return "recreatepwd.html";
				}
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String hashedPassword = encoder.encode(recreatePwdRequest.getConfirmpassword());
				user.setPassword(hashedPassword);
				userRepo.save(user);
				model.addAttribute("message", "Password reset was Successful");
				model.addAttribute("code", "0");
				Utils.sendPwdResetSuccessMail(appConfig, recreatePwdRequest.getEmailid());
				return "recreatepwdstatus.html";
			}
		} catch (Exception ex) {
			logger.error("Exception in recreate password", ex);
		}
		model.addAttribute("message", "Password reset was NOT successful. Please contact help desk");
		model.addAttribute("code", "2");
		return "recreatepwdstatus.html";

	}

	@RequestMapping("/resetpwd")
	public String resetPwd(Model model) {
		model.addAttribute("resetPwdRequest", new ResetPwdRequest());
		return "resetpwd.html";
	}

	@RequestMapping(value = "/processresetpwd", method = RequestMethod.POST)
	public String processResetPwd(@ModelAttribute ResetPwdRequest resetPwdRequest, Model model, Principal principal) {
		try {
			System.out.println("HomeController.processResetPwd() principal.getName() : " + principal.getName());
			Optional<User> userOpt = userRepo.findByEmailId(principal.getName());
			if (userOpt.isPresent()) {
				User user = userOpt.get();
				if (!resetPwdRequest.getConfirmpassword().equals(resetPwdRequest.getNewpassword())) {
					model.addAttribute("message", "New and confirm passwords is not matching. Please check");
					model.addAttribute("code", "1");
					return "resetpwd.html";
				}
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String hashedPassword = encoder.encode(resetPwdRequest.getConfirmpassword());

				user.setPassword(hashedPassword);
				userRepo.save(user);
				model.addAttribute("message", "Password reset was Successful");
				model.addAttribute("code", "0");
				Utils.sendPwdResetSuccessMail(appConfig, principal.getName());
				return "resetpwdstatus.html";
			}
		} catch (Exception ex) {
			logger.error("Exception in recreate password", ex);
		}
		model.addAttribute("message", "Password reset was NOT successful. Please contact help desk");
		model.addAttribute("code", "1");
		return "resetpwd.html";

	}

	public boolean checkIfUserHasAlreadyLoggedIn(Principal principal) {
		Collection<? extends Session> usersSessions = this.sessions.findByPrincipalName(principal.getName()).values();
		System.out.println("HomeController.checkIfUserHasAlreadyLoggedIn() : " + usersSessions);
		usersSessions.forEach(session->{
			if(session.isExpired()) {
				session.getId();
			}
			System.out.println("HomeController.checkIfUserHasAlreadyLoggedIn() : "+session.getId());
		});
		return usersSessions.size() > 1;
	}
}