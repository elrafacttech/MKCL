package mkcl.oesserver.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import mkcl.oesserver.model.UserSession;
import mkcl.os.apps.model.Pagination;
import mkcl.os.apps.utilities.PaginationHelper;
import mkcl.os.model.dal.DALHelper;
import mkcl.os.model.dal.ISql;

public class UserSessionServicesImpl extends PaginationHelper implements IUserSessionServices {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserSessionServicesImpl.class);
	private static final String EXEPTIONOCCUREDSTR = "Exception Occured : ";

	private ISql userSessionCrud = DALHelper.getIsql();
	

	@Override
	public long createUserSession(UserSession userSession) {
		long userSessionId = 0;
		Session session = null;
		Transaction transaction = null;

		try {
            if (userSession != null) {
            	session = userSessionCrud.getSession();
    			transaction = session.beginTransaction();

            	userSessionId = (Long) session.save(userSession);

            	session.flush();
    			transaction.commit();
			}
		} catch (Exception e) {
			LOGGER.error(EXEPTIONOCCUREDSTR + e.getMessage());

			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		} finally {
			if (transaction != null) {
				transaction = null;
			}
			if (session != null && session.isConnected()) {
				session.close();
				session = null;
			}
		}

		return userSessionId;
	}

	@Override
	public UserSession getUserSession(Long userSessionId) {
		UserSession userSession = null;

		try {
			userSession = userSessionCrud.findOne(UserSession.class, "sessionId", userSessionId);		
		} catch (Exception e) {
			LOGGER.error(EXEPTIONOCCUREDSTR + e.getMessage());
		}

		return userSession;
	}

	@Override
	public boolean updateUserSession(UserSession userSession) {
		boolean wasUpdated = false;
		try {
            if (userSession != null) {
            	userSessionCrud.update(userSession);
            	wasUpdated = true;
			}
		} catch (Exception e) {
			LOGGER.error(EXEPTIONOCCUREDSTR + e.getMessage());
		}

		return wasUpdated;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserSession> getObjectList(int start, Pagination pagination,
			Model model){
		List<UserSession> listUserSession = null;
		try {
			String userId;
			Map<String, Object> modelAttribute = new HashMap<String, Object>();
			modelAttribute = model.asMap();
			userId = modelAttribute.get(
					"userId").toString();

			listUserSession = pagination(start, pagination.getRecordsPerPage(), model,
					userId, null);
		} catch (Exception e) {
			LOGGER.error("Error occured in getObjectList", e);
		}
		return listUserSession;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T, E> List<T> getObjectListForSearchString(int start, Pagination pagination, E searchText, Model model) throws Exception {

		List<UserSession> listUserSession = null;
		LOGGER.info("property name ::" + pagination.getPropertyName());
		try {
			String userId;
			Map<String, Object> modelAttribute = new HashMap<String, Object>();
			modelAttribute = model.asMap();
			userId = modelAttribute.get(
					"userId").toString();

			listUserSession = pagination(start, pagination.getRecordsPerPage(), model,
					userId, searchText.toString());
		
		} catch (Exception e) {
			LOGGER.error("Exception in getObjectList...", e);
		}
		return (List<T>) listUserSession;
	}

	@Override
	public List<UserSession> pagination(int start, int recordsPerPage, Model model, String userId, String searchText) {
		// TODO Auto-generated method stub
		
		List<UserSession> userSessionList = new ArrayList<UserSession>();
		List<Object> queryResult = null;
		try {
			String query = null;
			if (searchText != null && !(searchText.equals(""))) {
				
				 query = "select us from UserSession us where us.username ='"
						+ userId
						+ "' and (us.startTime like '%" + searchText.toString() + "%' or us.endTime like '%" + searchText.toString() + "%')";
				
			}
			else{
				
				 query = "select us from UserSession us where us.username ='"
						+userId+"'";
				
			}
			
			queryResult = userSessionCrud.executeQuery(query,
					start, recordsPerPage);

			for (int i = 0; i < queryResult.size(); i++) {

				UserSession UserSession = (UserSession) queryResult.get(i);
				userSessionList.add(UserSession);

			}

		} catch (Exception e) {
			userSessionList = null;
			LOGGER.error("Error occured in pagination" + e);
		}
		return userSessionList;

	}
}
