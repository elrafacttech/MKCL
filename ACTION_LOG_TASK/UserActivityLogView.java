package mkcl.oesserver.viewmodel;

import java.util.Date;

public class UserActivityLogView{
	
	private Long userActivityLogId;
    private Long userSessionId;
    private String userAction;
    private Date startTime;
    private Date endTime;
    private String duration;
    
    
	public Long getUserActivityLogId() {
		return userActivityLogId;
	}
	public void setUserActivityLogId(Long userActivityLogId) {
		this.userActivityLogId = userActivityLogId;
	}
	public Long getUserSessionId() {
		return userSessionId;
	}
	public void setUserSessionId(Long userSessionId) {
		this.userSessionId = userSessionId;
	}
	public String getUserAction() {
		return userAction;
	}
	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
    
    
}
