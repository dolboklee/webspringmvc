package kr.or.nextit.notice.model;

public class Notice {

	private int notice_seq_no;
	private String notice_title;
	private String notice_content;
	private String notice_user_name;
	private String notice_reg_date;
	
	public void Notice() {}

	public int getNotice_seq_no() {
		return notice_seq_no;
	}

	public void setNotice_seq_no(int notice_seq_no) {
		this.notice_seq_no = notice_seq_no;
	}

	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public String getNotice_user_name() {
		return notice_user_name;
	}

	public void setNotice_user_name(String notice_user_name) {
		this.notice_user_name = notice_user_name;
	}

	public String getNotice_reg_date() {
		return notice_reg_date;
	}

	public void setNotice_reg_date(String notice_reg_date) {
		this.notice_reg_date = notice_reg_date;
	}

	
	
	
	
	
}
