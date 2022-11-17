package mkcl.common.services;

import java.util.List;

import mkcl.os.account.LoginAccount;

public interface ILoginAttemptService {
	
	boolean updateLoginAttempt(LoginAccount loginAccount);

}
