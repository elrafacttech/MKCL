package mkcl.oesserver.services;

import java.util.List;

import org.springframework.ui.Model;

import mkcl.oesserver.model.UserActivityLog;
import mkcl.oesserver.viewmodel.UserActivityLogView;

public interface IUserActivityLoggerServices {
	/**
	 * Used for creating a new User Activity Log entry
	 * 
	 * @return
	 */
	boolean createUserActivityLog(UserActivityLog userActivityLog);
	
	
	/**
	 * Method to get Summary Session users list with start and end .
	 * 
	 * @param model,userSessionId,start,recordsPerPage
	 * @return List<UserSession>
	 * @throws Exception
	 */
	List<UserActivityLogView> pagination(int start, int recordsPerPage,Model model, long userSessionId, String searchText);
}
