import java.util.Date;

public class Certificate {
	// attributes
	private String certificateId;
	private String studentId;
	private String courseId;
	private Date releaseDate;
	
	// constructor
	public Certificate(String certificateId, String studentId, String courseId, Date releaseDate) {
		super();
		this.certificateId = certificateId;
		this.studentId = studentId;
		this.courseId = courseId;
		this.releaseDate = releaseDate;
	}
	// getters
	public String getCertificateId() {
		return certificateId;
	}
	public String getStudentId() {
		return studentId;
	}
	public String getCourseId() {
		return courseId;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}

	

}
