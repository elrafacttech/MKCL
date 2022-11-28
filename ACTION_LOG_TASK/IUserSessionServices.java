package mkcl.oesserver.services;

import java.util.List;

import org.springframework.ui.Model;

import mkcl.oesserver.model.UserSession;

public interface IUserSessionServices {
	/**
	 * Used for creating a new User Session entry
	 * 
	 * @return
	 */
	long createUserSession(UserSession userSession);

	/**
	 * Used for retrieving a User Session entry
	 * 
	 * @return
	 */
	UserSession getUserSession(Long userSessionId);

	/**
	 * Used for updating an existing User Session entry
	 * 
	 * @return
	 */
	boolean updateUserSession(UserSession userSession);
	
	
	/**
	 * Method to get Session users list with start and end .
	 * 
	 * @param model,userId,start,recordsPerPage
	 * @return List<UserSession>
	 * @throws Exception
	 */
	List<UserSession> pagination(int start, int recordsPerPage,Model model, String userId, String searchText);
}
