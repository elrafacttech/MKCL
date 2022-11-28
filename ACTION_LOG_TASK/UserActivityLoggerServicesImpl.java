package mkcl.oesserver.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import mkcl.oesserver.model.UserActivityLog;
import mkcl.oesserver.viewmodel.UserActivityLogView;
import mkcl.os.apps.model.Pagination;
import mkcl.os.apps.utilities.PaginationHelper;
import mkcl.os.model.dal.DALHelper;
import mkcl.os.model.dal.ISql;

public class UserActivityLoggerServicesImpl extends PaginationHelper implements IUserActivityLoggerServices {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserActivityLoggerServicesImpl.class);
	private static final String EXEPTIONOCCUREDSTR = "Exception Occured : ";

	private ISql userActivityLoggerCrud = DALHelper.getIsql();

	@Override
	public boolean createUserActivityLog(UserActivityLog userActivityLog) {
		boolean wasUpdated = false;

		try {
            if (userActivityLog != null) {
				userActivityLoggerCrud.save(userActivityLog);
				wasUpdated = true;
			}
		} catch (Exception e) {
			LOGGER.error(EXEPTIONOCCUREDSTR + e.getMessage());
		}

		return wasUpdated;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserActivityLogView> getObjectList(int start, Pagination pagination,
			Model model){
		List<UserActivityLogView> listUserActivityLog = null;
		try {
			long userSessionId;
			Map<String, Object> modelAttribute = new HashMap<String, Object>();
			modelAttribute = model.asMap();
			userSessionId = Long.parseLong(modelAttribute.get(
					"userSessionId").toString());

			listUserActivityLog = pagination(start, pagination.getRecordsPerPage(), model,
					userSessionId, null);
		} catch (Exception e) {
			LOGGER.error("Error occured in getObjectList", e);
		}
		return listUserActivityLog;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T, E> List<T> getObjectListForSearchString(int start, Pagination pagination, E searchText, Model model) throws Exception {

		List<UserActivityLogView> listUserActivityLog = null;
		LOGGER.info("property name ::" + pagination.getPropertyName());
		try {
			long userSessionId;
			Map<String, Object> modelAttribute = new HashMap<String, Object>();
			modelAttribute = model.asMap();
			userSessionId = Long.parseLong(modelAttribute.get(
					"userSessionId").toString());

			listUserActivityLog = pagination(start, pagination.getRecordsPerPage(), model,
					userSessionId, searchText.toString());
		
		} catch (Exception e) {
			LOGGER.error("Exception in getObjectList...", e);
		}
		return (List<T>) listUserActivityLog;
	}

	@Override
	public List<UserActivityLogView> pagination(int start, int recordsPerPage, Model model, long userSessionId,
			String searchText) {
		List<UserActivityLogView> userActivityLogList = new ArrayList<UserActivityLogView>();
		List<Object> queryResult = null;
		try {
			String query = null;
			if (searchText != null && !(searchText.equals(""))) {
				
				 query = "select ual from UserActivityLog ual where ual.userSessionId ="
						+ userSessionId
						+ " and (ual.logDateTime like '%" + searchText.toString() + "%') order by userActivityLogId asc";
				
			}
			else{
				
				 query = "select ual from UserActivityLog ual where ual.userSessionId ="
						+ userSessionId +" order by userActivityLogId asc";
				
			}
			
			queryResult = userActivityLoggerCrud.executeQuery(query,
					start, recordsPerPage);

			for (int i = 0; i < queryResult.size(); i++) {

				UserActivityLog user = (UserActivityLog) queryResult.get(i);
				UserActivityLogView userActivityLogView = new UserActivityLogView();
				Date endTime=null;
				if(i < queryResult.size()-1) {
					UserActivityLog userNext = (UserActivityLog) queryResult.get(i+1);
					endTime = userNext.getLogDateTime();
				}
			
				if(endTime ==  null) {
					endTime = user.getLogDateTime();
				}
				
				Date startTime = user.getLogDateTime();
				long sessionDuration = endTime.getTime() - startTime.getTime();
				long sessionHrs = (sessionDuration / (1000*60*60)) % 24;
	            long sessionMin = (sessionDuration / (1000*60)) % 60;
	            long sessionSec = (sessionDuration / 1000) % 60;
				
				userActivityLogView.setStartTime(startTime);
				userActivityLogView.setEndTime(endTime);
				userActivityLogView.setDuration(sessionHrs + ":" + sessionMin + ":" + sessionSec);
				userActivityLogView.setUserAction(user.getUserAction());
				userActivityLogView.setUserActivityLogId(user.getUserActivityLogId());
				userActivityLogView.setUserSessionId(user.getUserSessionId());
				userActivityLogList.add(userActivityLogView);;

			}

		} catch (Exception e) {
			userActivityLogList = null;
			LOGGER.error("Error occured in pagination" + e);
		}
		return userActivityLogList;
	}
}
