package co.yedam.mavenProject.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	private int replyNo;
	private int boardNo;
	private String reply;
	private String replyer;
	private Date replyDate;
}
