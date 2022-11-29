/**
 * 
 */
package mkcl.oesserver.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mkcl.oes.commons.SessionHelper;
import mkcl.oespcs.model.Client;
import mkcl.oesserver.services.OESClientUserServicesImpl;
import mkcl.oesserver.services.UserActivityLoggerServicesImpl;
import mkcl.oesserver.services.UserSessionServicesImpl;
import mkcl.os.apps.model.Constants;
import mkcl.os.apps.model.Pagination;
import mkcl.os.apps.utilities.MKCLUtility;
import mkcl.os.apps.utilities.PaginationHelper;

/**
 * @author R@ghu
 * ## BR 25028
 * 
 */
@Controller
@RequestMapping("actionLog")
public class ActionLogUserController extends PaginationHelper implements
		ApplicationContextAware {

	//Log
	private static final Logger LOGGER = LoggerFactory.getLogger(ActionLogUserController.class);
	
	//List Service Implementation Class
	private OESClientUserServicesImpl oesClientUserServicesImplObj;
	private UserSessionServicesImpl userServicesImpl;
	private UserActivityLoggerServicesImpl userActivityServicesImpl;
	
	private static final String PAGINATION = "pagination";
	
	//List Binding
	private static final String USERLIST = "UserLogList";
	private static final String USERSESSION = "UserSessionLogList";
	private static final String USERACTIVITY = "UserActivityLogList";
	
    //Redirect Page
	private static final String USERLISTVIEW = "actionLog/userList";
	private static final String USERSESSIONLISTVIEW = "actionLog/userSessionLogList";
	private static final String USERACTIVITYLISTVIEW = "actionLog/userActivityLogList";
	
	
	/*
	 * Get errorPage and exception string constants from constants class.
	 */
	private static final String ERRORPAGE = Constants.ERRORPAGE;
	private static final String EXCEPTIONSTRING = Constants.EXCEPTIONSTRING;

	

	
	/**
	 * Method to bind date with with given format.
	 * 
	 * @param dataBinder
	 * @param locale
	 */
	@InitBinder
	public void initBinder(WebDataBinder dataBinder, Locale locale,
			HttpServletRequest request) {
		Properties properties = null;
		String dateFormatString;
		SimpleDateFormat dateFormat;

		properties = MKCLUtility.loadMKCLPropertiesFile();

		dateFormatString = properties.getProperty("global.dateFormat");

		dateFormat = new SimpleDateFormat(dateFormatString);
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * Get method for User List
	 * @param model
	 * @param request
	 * @param locale
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/userLogList" }, method = RequestMethod.GET)
	public String listGet(Model model, HttpServletRequest request, Locale locale) {
		LoggerFactory.getLogger(ActionLogUserController.class).debug(
				"Method Started");
		try {

			if (request.getSession().getAttribute("loginStatus") == null) {
				return "redirect:../login/loginpage";
			}
			// 1 Call to method add message.
			addMessage(request, model, locale);
			
			// 2 Get searchText.
			String searchText = request.getParameter("searchText");

			// get OES client id from session
			Client client = SessionHelper.getClient(request);
			model.addAttribute("clientID", client.getClientID());

			// 3 Get Object list.
			Pagination pagination = new Pagination();
			pagination.setPropertyName("");
			pagination.setListName(USERLIST);
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("");
				oesClientUserServicesImplObj.pagination(null, pagination,
						searchText, model);
			} else {
				oesClientUserServicesImplObj.pagination(null, pagination,
						null, model);
			}
			return USERLISTVIEW;
		} catch (Exception ex) {

			LoggerFactory.getLogger(ActionLogUserController.class).error(
					ex.getMessage());
			model.addAttribute(Constants.EXCEPTIONSTRING, ex.getMessage());
			return Constants.ERRORPAGE;
		}
	}

	
	@RequestMapping(value = { "/userSessionLogList" }, method = RequestMethod.GET)
	public String listGetSession(Model model, HttpServletRequest request, Locale locale) {
		
		LoggerFactory.getLogger(ActionLogUserController.class).debug(
				"Method Started");
		try {

			
			  if (request.getSession().getAttribute("loginStatus") == null) { return
			  "redirect:../login/loginpage"; }
			 
			// 1 Call to method add message.
			addMessage(request, model, locale);
			
			// 2 Get searchText.
			String searchText = request.getParameter("searchText");

			// get user id and user name from Request
			model.addAttribute("userId", request.getParameter("userId"));
			model.addAttribute("userName", request.getParameter("userName"));
			
			// get user Id from Request
			//model.addAttribute("userId", "recruitlive");

			// 3 Get Object list.
			Pagination pagination = new Pagination();
			pagination.setPropertyName("");
			pagination.setListName(USERSESSION);
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("");
				userServicesImpl.pagination(null, pagination,
						searchText, model);
			} else {
				userServicesImpl.pagination(null, pagination,
						null, model);
			}
			return USERSESSIONLISTVIEW;
		} catch (Exception ex) {

			LoggerFactory.getLogger(ActionLogUserController.class).error(
					ex.getMessage());
			model.addAttribute(Constants.EXCEPTIONSTRING, ex.getMessage());
			return Constants.ERRORPAGE;
		}
	}
	
	
	@RequestMapping(value = { "/userActivityLogList" }, method = RequestMethod.GET)
	public String listGetSUmmary(Model model, HttpServletRequest request, Locale locale) {
		
		LoggerFactory.getLogger(ActionLogSummaryController.class).debug(
				"Method Started");
		try {

			
			  if (request.getSession().getAttribute("loginStatus") == null) { return
			  "redirect:../login/loginpage"; }
			 
			// 1 Call to method add message.
			addMessage(request, model, locale);
			
			// 2 Get searchText.
			String searchText = request.getParameter("searchText");

			// get user session id,start time,end time,user id,user name, from Request
			model.addAttribute("userSessionId", request.getParameter("userSessionId"));
			model.addAttribute("startTime", request.getParameter("startTime"));
			model.addAttribute("endTime", request.getParameter("endTime"));
			model.addAttribute("userId", request.getParameter("userId"));
			model.addAttribute("userName", request.getParameter("userName"));
			
			String endTime = request.getParameter("endTime");
			
			// get user Id from Request
			//model.addAttribute("userSessionId", "8");

			// 3 Get Object list.
			Pagination pagination = new Pagination();
			pagination.setPropertyName("");
			pagination.setListName(USERACTIVITY);
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("");
				userActivityServicesImpl.pagination(null, pagination,
						searchText, model);
			} else {
				userActivityServicesImpl.pagination(null, pagination,
						null, model);
			}
			return USERACTIVITYLISTVIEW;
		} catch (Exception ex) {

			LoggerFactory.getLogger(ActionLogSummaryController.class).error(
					ex.getMessage());
			model.addAttribute(Constants.EXCEPTIONSTRING, ex.getMessage());
			return Constants.ERRORPAGE;
		}
	}
	


	
	/**
	 * Post method for User List Pagination - Previous
	 * @param model
	 * @param pagination
	 * @param request
	 * @param searchText
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/prevActionSession" }, method = RequestMethod.POST)
	public String prevPostActionSession(Model model,
			@ModelAttribute(PAGINATION) Pagination pagination,
			HttpServletRequest request, String searchText) {


				// get user Id from Request
				model.addAttribute("userId", request.getParameter("userId"));
				model.addAttribute("userName", request.getParameter("userName"));

		pagination.setListName(USERSESSION);
		try {
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("sessionId");
				userServicesImpl.paginationPrev(null, pagination,
						searchText, model);
			} else {
				userServicesImpl.paginationPrev(null, pagination,
						searchText, model);
			}
		} catch (Exception ex) {
			LOGGER.error("exception while calling paginationPrev method.");
		}
		return USERSESSIONLISTVIEW;

	}
	
	/**
	 * Post method for User List Pagination - Next
	 * @param model
	 * @param pagination
	 * @param request
	 * @param searchText
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/nextActionSession" }, method = RequestMethod.POST)
	public String nextPostActionSession(Model model,
			@ModelAttribute(PAGINATION) Pagination pagination,
			String searchText, HttpServletRequest request) {

		// get user Id from Request
		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("userName", request.getParameter("userName"));
		
		
		pagination.setListName(USERSESSION);
		try {
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("sessionId");
				userServicesImpl.paginationNext(null, pagination,
						searchText, model);
			} else {
				userServicesImpl.paginationNext(null, pagination,
						searchText, model);
			}
		} catch (Exception ex) {
			LOGGER.error("exception while calling nextPost OESCLIENT User Controller  method.");
		}
		return USERSESSIONLISTVIEW;
	}

	

	
	
	/**
	 * Post method for User List Pagination - Previous
	 * @param model
	 * @param pagination
	 * @param request
	 * @param searchText
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/prevActionSummary" }, method = RequestMethod.POST)
	public String prevPostActionSummary(Model model,
			@ModelAttribute(PAGINATION) Pagination pagination,
			HttpServletRequest request, String searchText) {


		// get user session id,start time,end time,user id,user name, from Request
		model.addAttribute("userSessionId", request.getParameter("userSessionId"));
		model.addAttribute("startTime", request.getParameter("startTime"));
		model.addAttribute("endTime", request.getParameter("endTime"));
		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("userName", request.getParameter("userName"));

		pagination.setListName(USERACTIVITY);
		try {
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("userActivityLogId");
				userActivityServicesImpl.paginationPrev(null, pagination,
						searchText, model);
			} else {
				userActivityServicesImpl.paginationPrev(null, pagination,
						searchText, model);
			}
		} catch (Exception ex) {
			LOGGER.error("exception while calling paginationPrev method.");
		}
		return USERACTIVITYLISTVIEW;

	}

	/**
	 * Post method for User List Pagination - Next
	 * @param model
	 * @param pagination
	 * @param request
	 * @param searchText
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/nextActionSummary" }, method = RequestMethod.POST)
	public String nextPostActionSummary(Model model,
			@ModelAttribute(PAGINATION) Pagination pagination,
			String searchText, HttpServletRequest request) {

		// get user session id,start time,end time,user id,user name, from Request
		model.addAttribute("userSessionId", request.getParameter("userSessionId"));
		model.addAttribute("startTime", request.getParameter("startTime"));
		model.addAttribute("endTime", request.getParameter("endTime"));
		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("userName", request.getParameter("userName"));
		
		
		pagination.setListName(USERACTIVITY);
		try {
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("userActivityLogId");
				userActivityServicesImpl.paginationNext(null, pagination,
						searchText, model);
			} else {
				userActivityServicesImpl.paginationNext(null, pagination,
						searchText, model);
			}
		} catch (Exception ex) {
			LOGGER.error("exception while calling nextPost OESCLIENT User Controller  method.");
		}
		return USERACTIVITYLISTVIEW;
	}

	

	
	/**
	 * Post method for User List Pagination - Previous
	 * @param model
	 * @param pagination
	 * @param request
	 * @param searchText
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/prev" }, method = RequestMethod.POST)
	public String prevPost(Model model,
			@ModelAttribute(PAGINATION) Pagination pagination,
			HttpServletRequest request, String searchText) {

		// get OES client id from session
		Client client = SessionHelper.getClient(request);
		model.addAttribute("clientID", client.getClientID());

		pagination.setListName(USERLIST);
		try {
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("id");
				oesClientUserServicesImplObj.paginationPrev(null, pagination,
						searchText, model);
			} else {
				oesClientUserServicesImplObj.paginationPrev(null, pagination,
						searchText, model);
			}
		} catch (Exception ex) {
			LOGGER.error("exception while calling paginationPrev method.");
		}
		return USERLISTVIEW;

	}
	
	/**
	 * Post method for User List Pagination - Next
	 * @param model
	 * @param pagination
	 * @param request
	 * @param searchText
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/next" }, method = RequestMethod.POST)
	public String nextPost(Model model,
			@ModelAttribute(PAGINATION) Pagination pagination,
			String searchText, HttpServletRequest request) {

		// get OES client id from session
		Client client = SessionHelper.getClient(request);
		model.addAttribute("clientID", client.getClientID());

		pagination.setListName(USERLIST);
		try {
			if (searchText != null && !(searchText.equals(""))) {
				pagination.setPropertyName("id");
				oesClientUserServicesImplObj.paginationNext(null, pagination,
						searchText, model);
			} else {
				oesClientUserServicesImplObj.paginationNext(null, pagination,
						searchText, model);
			}
		} catch (Exception ex) {
			LOGGER.error("exception while calling nextPost OESCLIENT User Controller  method.");
		}
		return USERLISTVIEW;
	}

	



	/**
	 * Get method to Cancel Client User List
	 * @param model
	 * @return String this returns the path of a view
	 */
	@RequestMapping(value = { "/cancel" }, method = RequestMethod.GET)
	public String cancelGet(Model model) {
		try {
			return "redirect:actionLog";
		} catch (Exception ex) {
			LOGGER.error("Excption while cancelGet in OESClientController:"+ex.getMessage());
			model.addAttribute(EXCEPTIONSTRING, ex);
			return ERRORPAGE;
		}
	}

	
	/**
	 * Adds the message.
	 *
	 * @param request the request
	 * @param model the model
	 * @param locale the locale
	 */
	protected void addMessage(HttpServletRequest request, Model model,
			Locale locale) {
		String messageId = request.getParameter("messageid");
		if (messageId != null && messageId.trim().length() != 0) {
			MKCLUtility.addMessage(Integer.parseInt(messageId), model, locale);
		}
	}

	
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		oesClientUserServicesImplObj = (OESClientUserServicesImpl) applicationContext.getBean("OESClientUserService");
		userServicesImpl = (UserSessionServicesImpl) applicationContext.getBean("UserSessionServices");
		userActivityServicesImpl = (UserActivityLoggerServicesImpl) applicationContext.getBean("UserActivityLoggerServices");
	}

}
