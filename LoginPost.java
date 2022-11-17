/**
 *
 */
package mkcl.os.apps.controllers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mkcl.common.services.ILoginAttemptService;
import mkcl.oes.commons.CaptchaUtil;
import mkcl.oes.commons.FileUploadHelper;
import mkcl.oes.commons.SessionHelper;
import mkcl.oesserver.model.AccessType;
import mkcl.oesserver.model.ClientUserAssociation;
import mkcl.oesserver.security.CSRFTokenManager;
import mkcl.oesserver.services.IClientUserAssociationServices;
import mkcl.oesserver.utilities.CheckIsClientAdmin;
import mkcl.os.account.LoginAccount;
import mkcl.os.account.services.ILoginAccountServices;
import mkcl.os.apps.model.Constants;
import mkcl.os.apps.model.User;
import mkcl.os.apps.services.CacheService;
import mkcl.os.apps.services.ISession;
import mkcl.os.apps.services.IUserServices;
import mkcl.os.apps.services.SecurityHelper;
import mkcl.os.apps.utilities.MKCLUtility;
import mkcl.scu.services.ExamEventGlobalSettingsServicesImpl;

/**
 * The Class LoginPost.
 *
 * @author avinashp
 * @author anupamd
 */
@Controller
@RequestMapping("loginpost")
public class LoginPost implements ApplicationContextAware {

	/** The Constant USERSTRING. */
	private static final String USERSTRING = "user";

	/** The Constant LOGINVIEW. */
	private static final String LOGINVIEW = "login/loginpage";

	/** The login account services. */
	private ILoginAccountServices loginAccountServices;

	private IUserServices userServices;

	private ISession sessionWrap;

	private IClientUserAssociationServices clientUserAssociationServices;

	private ExamEventGlobalSettingsServicesImpl exevGlobalSettingsImpl;

	/** The login failed attempt services. */
	private ILoginAttemptService loginAttemptService;

	/**
	 * Login post.
	 *
	 * @param model   the model
	 * @param user    the user
	 * @param session the session
	 * @param errors  the errors
	 * @param locale  the locale
	 * @return the string
	 */
	@RequestMapping(value = { "loginpage" }, method = RequestMethod.POST)
	public String loginPost(Model model, @ModelAttribute(USERSTRING) LoginAccount user, BindingResult errors,
			Locale locale, HttpServletRequest request, HttpSession session) {
		String userImagePath;
        //Client client = SessionHelper.getClient(request);

		try {

           // Validate user
			if (errors.hasErrors()) {
				model.addAttribute("loginId", user.getLoginId());
				return "redirect:../login/loginpage?messageid=" + MessageConstants.INVALID_USERNAME;
			}

            //Captch checking
			if (request.getParameter("captcha").isEmpty()
					|| !CaptchaUtil.validateCaptcha(session, request.getParameter("captcha"))) {
                // MKCLUtility.addMessage(MessageConstants.INVALID_CAPTCHA, model, locale);
				model.addAttribute("loginId", user.getLoginId());
				return "redirect:../login/loginpage?messageid=" + MessageConstants.INVALID_CAPTCHA;
			}

            // fetch user from database
			LoginAccount dbUser = loginAccountServices.getLoginAccountByLoginId(user.getLoginId());
			Properties properties = MKCLUtility.loadPropertiesFile(locale);

			if (dbUser == null) {
				model.addAttribute("loginId", user.getLoginId());
				return "redirect:../login/loginpage?messageid=" + MessageConstants.INVALID_USERNAME;
			}

            // check password encryption Required
			if (sessionWrap.getFromSession("passwordEncryptionReq") != null
					&& sessionWrap.getFromSession("passwordEncryptionReq").toString().equals("1")) {
                // get salt added hash value
				String saltMd5 = this.getsaltMD5(dbUser.getPassword(), sessionWrap.getFromSession("rnum").toString());
				dbUser.setPassword(saltMd5);
			}

			User isValidUser = userServices.getUserById(dbUser.getLoginId());
			
			/** Start CR-25028 the login failed Attempt Validation */
			/** Get Value from Property for LoginBlockDuration ,BlockOnNoFailedLogins */
			String loginBlockDuration = properties.getProperty("LoginBlockDuration");
			String blockOnNoFailedLogins = properties.getProperty("BlockOnNoFailedLogins");

			/** Covert to Integer */
			int convertBlockDuration = Integer.parseInt(loginBlockDuration);
			int convertBlockFailedCount = Integer.parseInt(blockOnNoFailedLogins);

			/** Get Login Attempts */
			int loginFailedAttemptsCount = dbUser.getLoginFailAttempts();
	
			
			Date currentDate = Calendar.getInstance().getTime();
			DateTime currentTimeStamp = new DateTime(currentDate);
			
			/** Validate Password */
			if (!dbUser.getPassword().equals(user.getPassword()) || isValidUser.getIsDeleted()) {
				model.addAttribute("loginId", user.getLoginId());
				loginFailedAttemptsCount = loginFailedAttemptsCount + 1; //Increment Login Failed Attempt Count
				dbUser.setLoginFailAttempts(loginFailedAttemptsCount);
				
				/** Checking Disabled is null or not */
				if (dbUser.getDisabledOnDate() == null) {
					/** Checking with convertBlockFailedCount is equal to  loginFailedAttemptsCount */
					if (loginFailedAttemptsCount == convertBlockFailedCount) { 
						dbUser.setDisabledOnDate(currentDate);
						loginAttemptService.updateLoginAttempt(dbUser);
						return "redirect:../login/loginpage?messageid=" + MessageConstants.INVALID_USERNAME;
					} else {
						dbUser.setDisabledOnDate(null);
						loginAttemptService.updateLoginAttempt(dbUser);
						return "redirect:../login/loginpage?messageid=" + MessageConstants.INVALID_USERNAME;
					}

				} else {
					/** Password not Match - Validate Disabled Date and Time difference */
					Date disabledDate = dbUser.getDisabledOnDate();
					DateTime disabledDateTimestamp = new DateTime(disabledDate);
					/** Time difference Validation */
					int blockDurationDiff = getDateDifference(disabledDateTimestamp,currentTimeStamp);
					
					if (blockDurationDiff <= convertBlockDuration) {
						return "redirect:../login/loginpage?messageid=" + MessageConstants.LOGIN_ATTEMPT_ERROR_MESSAGE;
					} else {
						/** Password not match and time difference is above 30 minutes, set login Attempt 1 and disabled null */
						dbUser.setLoginFailAttempts(1);
						dbUser.setDisabledOnDate(null);
						loginAttemptService.updateLoginAttempt(dbUser);
						return "redirect:../login/loginpage?messageid=" + MessageConstants.INVALID_USERNAME;
					}

				}
			} 
			else {
				/** Password Match - Validate disabled date and time difference */
				if (dbUser.getDisabledOnDate() != null) {
						Date disabledDate = dbUser.getDisabledOnDate();
						DateTime disabledDateTimestamp = new DateTime(disabledDate);
						/** Time difference Validation */
						int blockDurationDiff = getDateDifference(disabledDateTimestamp,currentTimeStamp);
						
							if (blockDurationDiff <= convertBlockDuration) {
								return "redirect:../login/loginpage?messageid=" + MessageConstants.LOGIN_ATTEMPT_ERROR_MESSAGE;
							}else {
								/** After Successfully login , set login Attempt 0 and DisabledOnDate null */
								dbUser.setLoginFailAttempts(0);
								dbUser.setDisabledOnDate(null);
								loginAttemptService.updateLoginAttempt(dbUser);
							}
					
				     }else {
				    	    /** Password Match - After Successfully login , set login Attempt 0 and DisabledOnDate null */
				    	    dbUser.setLoginFailAttempts(0);
				    	    dbUser.setDisabledOnDate(null);
							loginAttemptService.updateLoginAttempt(dbUser);
				     }

			}
			/** End CR-25028 The login failed Attempt Validation */
			
			
			sessionWrap.addToSession("loginStatus", "1");

			/* *********Set Client in session************* */
			ClientUserAssociation clientUserAssociation = clientUserAssociationServices
					.getClientUserAssociationWithClientByUserID(dbUser.getLoginId());
            //loggedUser = userServices.getUserById(dbUser.getLoginId());
			if (clientUserAssociation != null && isValidUser != null) {
				userImagePath = FileUploadHelper.getRelativeFolderPath(request, "UserImagesUploadPath");
				clientUserAssociation.setUser(isValidUser);
				dbUser.setProfileImagePath(userImagePath + isValidUser.getPictureId());
				dbUser.setDataObject(clientUserAssociation);
			}
            // fetch groupIds of loginUser
			dbUser.setGroupIdList(SecurityHelper.getGroupIdsOfUser(dbUser));
            // fetch roleIds of loginUser
			dbUser.setRoleIdList(CacheService.getRoleIdsFromGroupIds(dbUser.getGroupIdList()));
            // fetch deny groupIds of user.
			dbUser.setDenyGroupIdList(SecurityHelper.getDenyGroupIdsOfUser(dbUser));
            // add user to session
			sessionWrap.addToSession(USERSTRING, dbUser);

			if (dbUser.getIsAdministrator()) {
				sessionWrap.addToSession("isAdministrator", "true");

			}

			if (!dbUser.getIsAdministrator() && !CheckIsClientAdmin.IsClientAdmin(dbUser.getGroupIdList())) {
                  // Fetch User Item Bank Language Association map
				Map<AccessType, Map<Long, String>> associatedLang = clientUserAssociationServices
						.getUserItemBankAssocioationLanguage(clientUserAssociation.getClientUserAssociationID(),
								SessionHelper.getClient(request).getClientID());
               // Add UserItemBank associated Languages to the session
				sessionWrap.addToSession("itemBankAssLang", associatedLang);
			}

           //generate CSRF token and add it to Session
			CSRFTokenManager.generateCSRFToken(session);

			return "redirect:../common/home?messageid=" + MessageConstants.SUCCESSFULLY_LOGIN;

		} catch (Exception ex) {
			LoggerFactory.getLogger(LoginController.class).error(ex.getMessage(), ex);
			model.addAttribute(Constants.EXCEPTIONSTRING, ex);
			return Constants.ERRORPAGE;
		}

	}
	
	/*
	 * Date Diffreence Caluclation
	 */

	public int getDateDifference(DateTime disabledDateTimestamp,DateTime currentTimeStamp) {
		return org.joda.time.Minutes.minutesBetween(disabledDateTimestamp, currentTimeStamp)
				.getMinutes();
	}

	/*
	 * calculate salt MD5 value
	 */
	/**
	 * Gets the salt md5.
	 *
	 * @param input   the input
	 * @param saltKey the salt key
	 * @return the salt m d5
	 */
	private String getsaltMD5(String input, String saltKey) {
		String saltMd5 = null;
		String salt = input + saltKey;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(salt.getBytes(), 0, salt.length());
			saltMd5 = new BigInteger(1, digest.digest()).toString(16);
			while (saltMd5.length() < 32) {
				saltMd5 = "0" + saltMd5;
			}
		} catch (NoSuchAlgorithmException e) {
			LoggerFactory.getLogger(LoginPost.class).error("Error: ", e);
		}

		return saltMd5;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		loginAccountServices = (ILoginAccountServices) applicationContext.getBean("LoginAccountServices");
		userServices = (IUserServices) applicationContext.getBean("UserServices");
		sessionWrap = (ISession) applicationContext.getBean("SessionService");
		clientUserAssociationServices = (IClientUserAssociationServices) applicationContext
				.getBean("ClientUserAssociationService");
		exevGlobalSettingsImpl = (ExamEventGlobalSettingsServicesImpl) applicationContext
				.getBean("examEventGlobalSettingsServicesImpl");
		loginAttemptService = (ILoginAttemptService) applicationContext.getBean("LoginAttemptService");

	}
}