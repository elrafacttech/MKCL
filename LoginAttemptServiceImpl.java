package mkcl.common.services;

import java.util.Calendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mkcl.os.account.LoginAccount;
import mkcl.os.model.dal.DALHelper;
import mkcl.os.model.dal.ISql;

public class LoginAttemptServiceImpl implements ILoginAttemptService {

	private ISql icrud = DALHelper.getIsql();
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAttemptServiceImpl.class);

	@Override
	public boolean updateLoginAttempt(LoginAccount loginAccount) {

		Session session = icrud.getSession();
		Transaction transaction = session.beginTransaction();
		Query query = null;
		try {
			String updateItem = "UPDATE ACT_ID_USER SET LOGIN_FAIL_ATTEMPTS=?, DISABLED_ON_DATE_=?" + " WHERE ID_=?";
			query = session.createSQLQuery(updateItem);
			query.setInteger(0, loginAccount.getLoginFailAttempts());
			query.setTimestamp(1, loginAccount.getDisabledOnDate());
			query.setString(2, loginAccount.getLoginId());
			query.executeUpdate();
			transaction.commit();
			return true;
		}

		catch (Exception e) {
			LOGGER.error("Exception Occured : ", e);
			if (transaction != null)
				transaction.rollback();
			return false;
		} finally {
			if (session != null) {
				session.close();
				session = null;
			}
			if (transaction != null) {
				transaction = null;
			}
		}

	}
}