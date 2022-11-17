package mkcl.os.apps.controllers;

/**
 * The Class MessageConstants.
 * 
 * @author 
 */
public final class MessageConstants {

	/* Success Messages */
	/** The Constant SUCCESSFULLY_ADDED_RECORD. */
	public static final int SUCCESSFULLY_ADDED_RECORD = 1;

	/** The Constant SUCCESSFULLY_UPDATED_RECORD. */
	public static final int SUCCESSFULLY_UPDATED_RECORD = 2;

	/** The Constant SUCCESSFULLY_DELETED_RECORD. */
	public static final int SUCCESSFULLY_DELETED_RECORD = 3;

	/** The Constant SUCCESSFULLY_UPLOADED_FILE. */
	public static final int SUCCESSFULLY_UPLOADED_FILE = 4;

	/** The Constant SUCCESSFULLY_UPDATED_FILE. */
	public static final int SUCCESSFULLY_UPDATED_FILE = 5;

	/** The Constant SUCCESSFULLY_APPLIED_THEME. */
	public static final int SUCCESSFULLY_APPLIED_THEME = 6;

	/** The Constant SUCCESSFULLY_SUBMITTED_FORM. */
	public static final int SUCCESSFULLY_SUBMITTED_FORM = 7;

	/* Email Messages */
	/** The Constant SUCCESSFULLY_SENT_EMAIL. */
	public static final int SUCCESSFULLY_SENT_EMAIL = 8;

	/** The Constant SUCCESSFULLY_ACTIVATED_ACCOUNT. */
	public static final int SUCCESSFULLY_ACTIVATED_ACCOUNT = 9;

	/** The Constant SUCCESSFULLY_SENT_ACTIVATION_KEY. */
	public static final int SUCCESSFULLY_SENT_ACTIVATION_KEY = 10;

	/** The Constant SUCCESSFULLY_REGISTERED_ACCOUNT. */
	public static final int SUCCESSFULLY_REGISTERED_ACCOUNT = 11;

	public static final int SUCCESSFULLY_SAVED=104;

	/* Failure Messages */
	/** The Constant FAILED_TO_ADD. */
	public static final int FAILED_TO_ADD = 21;

	/** The Constant FAILED_TO_UPDATE. */
	public static final int FAILED_TO_UPDATE = 22;

	/** The Constant FAILED_TO_DELETE. */
	public static final int FAILED_TO_DELETE = 23;

	/** The Constant UNABLE_TO_APPLY_THEME. */
	public static final int UNABLE_TO_APPLY_THEME = 24;

	/** The Constant UNABLE_TO_CREATE_CONNECTION. */
	public static final int UNABLE_TO_CREATE_CONNECTION = 25;

	/** The Constant FAILED_TO_SEND_EMAIL. */
	public static final int FAILED_TO_SEND_EMAIL = 26;

	/** The Constant FAILED_ACTIVATED_ACCOUNT. */
	public static final int FAILED_ACTIVATED_ACCOUNT = 27;

	/* Warning messages */
	/** The Constant WARNING_CHECK_PARAMS. */
	public static final int WARNING_CHECK_PARAMS = 41;

	/** The Constant WARNING_NO_RECORD_FOUND. */
	public static final int WARNING_NO_RECORD_FOUND = 42;

	/* User Messages 61-onwards */
	/** The Constant INVALID_USERNAME. */
	public static final int INVALID_USERNAME = 61;
	
	/** The Constant LOGIN_ATTEMPT_ERROR_MESSAGE. */
	public static final int LOGIN_ATTEMPT_ERROR_MESSAGE = 5555;

	/** The Constant USER_ALREADY_EXIST. */
	public static final int USER_ALREADY_EXIST = 62;

	/** The Constant USER_DOESNOT_EXIST. */
	public static final int USER_DOESNOT_EXIST = 63;

	/** The Constant SUCCESSFULLY_LOGIN. */
	public static final int SUCCESSFULLY_LOGIN = 64;

	/** The Constant SUCCESSFULLY_REGISTERED. */
	public static final int SUCCESSFULLY_REGISTERED = 65;

	/** The Constant TIME_TO_LIVE. */
	public static final int TIME_TO_LIVE = 3600;

	/** The Constant SUBMENU_LIMIT. */
	public static final int SUBMENU_LIMIT = 5;

	/** For Checking in publish Event**/

	public static final int FAILED_ACTIVATED_Event =111;
	/** If event havnt been activated**/
	/**If event caqche is not reset**/
	public static final int FAILED_RESET_EVENTCACHE =112;
	public static final int WEEK_SCHEDULE_CANNOT_GENERATE_WITH_EXEVENT =113;
	public static final int SCHEDULE_LOCATION_CANNOT_CHANGE =114;
	public static final int PAPER_ADD_NOT_YET_COMPLETED = 32;
	public static final int PAPER_CONFIG_NOT_YET_COMPLETED = 31;
	public static final int EVENT_PUBLISHED_SUCCESSFULLY = 33;
	public static final int PAPER_ATTEMPTED_DATE_NOT_IN_EXAMEVENT_DATE = 34;
	public static final int SCHEDULE_NOT_ASSOCIATED_TO_EVENT=35;
	public static final int COLLECTION_NOT_ADDED_FOR_EVENT=36;
	public static final String LOGIN_STATUS = "loginStatus";

	public static final int REALSED_ITEMBANK_TO_EXAMEVENT = 51;
	public static final int ITEMBANK_CANNOT_RELEASE_TO_EXAMEVENT = 52;
	public static final int NO_ZIP_AVAILABLE_TO_UPDATEIMAGES = 53;
	public static final int FAILDED_TO_ADD_SCHEDULEMASTER = 54;
	public static final int FAILDED_TO_UPDATE_SCHEDULEMASTER = 57;
	public static final int ALREADY_EXISTS_SCHEDULE = 56;
	public static final int INVALIDFILETYPE =55;
	public static final int PAPER_NOT_ATTACHED_WITH_DC =58;
	public static final int SWFFILE_ZERO_SIZE =59;
	
	public static final int EXAMEVENT_NAME_AND_CODE_ALREADY_EXISTS =1009;
	public static final int EXAMEVENT_CODE_ALREADY_EXISTS =1010;
	public static final int EXAMEVENT_NAME_ALREADY_EXISTS =1011;
	public static final int PROBLEM_IN_CREATING_IDENTICAL_QUESTIONPAPER =1012;
	public static final int DISPLAY_CATEGORY_ARE_NOT_DEFINED_IN_EXEV_DEFAULT_LANGUAGE =1013;
	public static final int ERROR_IN_PROCESSING_DATA =1014;
	public static final int SCHEDULE_EXTENDED_SUCCESSFULLY =1015;
	public static final int PROBLEM_IN_EXTENDING_SCHEDULE =1016;
	public static final int PROBLEM_IN_EXTENDING_EXAMEVENT_ENDDATE =1017;
	public static final int DELETE_SCHEDULE_EXCEPT_WEEK =1018;
	public static final int EXAMEVENT_ARCHIVEED_SUCCESSFULLY =1019;
	public static final int PROBLEM_IN_EXAMEVENT_ARCHIVE =1020;
	public static final int NO_EXAMEVENT_ARE_AVAILABLE_TO_ARCHIVE =1021;
	public static final int ERROR_WHILE_FECHING_EXAMEV_ARCHIVE =1022;
	public static final int SUCCESSFUL_APPLICATION_EVENT_ASSOCIATION =1023;
	public static final int PROBLEM_IN_ASSOCIATIONL_APPLICATION_EVENT =1024;
	public static final int APPNAME_ALREADY_EXISTS =1025;
	public static final int EVENT_APPLICATION_ASSOCIATION_ALREADY_EXISTS =1026;
	public static final int EVENT_RESULTDATA_TRANSFER_STATUS_MSG =1027;
	
	public static final int ALLOCATIONFOREVALUATIONVALIDATIONMSG1 =1128;
	public static final int REALLOCATIONFOREVALUATIONVALIDATIONMSG1 =1129;
	
	public static final int ITEMBANK_DEALLOCATED_SUCCESSFULLY =66;
	public static final int ITEMBANK_DEALLOCATION_FAILED =68;
	public static final int Unabl_ToGet_ExamEvent_FAILED =80;
	
	
	public static final int PASSWORD_CHNANGED_SUCCESSFULLY = 1130;
	
	public static final int ERROR_DIFFICULTY_LEVEL_ITEM_COUNT = 95;
	public static final int INVALID_CAPTCHA=134;
	
	public static final int INFO_VENUE_CODE_DATA_NOT_AVAIL = 12;
	public static final int RETURN_URL_NOT_EXISTS=135;
	
	
	public static final int ITEMCREATION_ALLOCATION_SUCCESSFULL =1130;
	public static final int ITEMCREATION_ALLOCATION_FAILED =1131;
	
	public static final int ITEM_NOT_FOUND=136;
	public static final int WARNING_PLEASE_ENTER_DATA=137;
	
	public static final int FAILED_TO_ADD_ITEM = 138;
	
	public static final int ITEMWISESCHEME_MISMATCH_TOTALMARKS = 645;
	
	public static final int PROBLEM_UPDATING_ITEM_USAGE_INFORMATION =1101;
	
	public static final int GLOBAL_SETTING_NOT_FOUND =1102;
	
	public static final int INVALID_IMPORT_USER_TYPE =1103;
	public static final int ERROR_UPLOADING_USER_TEMPLATE =1104;
	public static final int INVALID_EXAM_EVENT_ID =1105;
	
	public static final int MOE_REPORT_GENERATE_FAILED =1106;
	public static final int DATA_NOT_FOUND=1107;
	
	public static final int IMPORT_ONGOING=1108;
	public static final int CANDIDATE_NOT_FOUND=1109;
	public static final int CANDIDATE_IMPORTED=1110;
	public static final int ATTEMPTS_MISMATCH=1111;
	public static final int INTERNAL_SERVER_ERROR=1112;
	public static final int UNSUPPORTED_ITEM_TYPE=1113;
	
	public static final int SUPER_PROCTOR_ALLOCATED=195;
	public static final int SUPER_PROCTOR_FAILED=595;
	public static final int SUPER_PROCTOR_FAILED_To_ALLOCATE=1995;
	public static final int SUPER_PROCTOR_FAILED_To_ALLOCATE_ExamEvent=1996;
	public static final int SUPER_PROCTOR_FAILED_To_ALLOCATE_PaperId=1997;
	public static final int SUPER_PROCTOR_FAILED_To_ALLOCATE_userId=1998;
	
	
	public static final int ALLOCATIONFORCANDIDATEVALIDATIONMSG1 =1200;
	public static final int ALLOCATIONFORCANDIDATEVALIDATIONCOUNTMORETHANONE =1201;
	public static final int ALLOCATIONFORCANDIDATEVALIDATIONCUNALLOCATEDCOUNT =1202;
	public static final int ALLOCATIONFORCANDIDATEVALIDATIONEMAXLIMIT =1203;
	public static final int ALLOCATIONFORCANDIDATEVALIDATIONEEMPTY =1204;
	
	public static final int ITEMCOPYSUCCESSFULLY =1205;
	public static final int ITEMCOPYFAILED =1206;
	/**
	 * Instantiates a new message constants.
	 */
	private MessageConstants() {
	}
}
