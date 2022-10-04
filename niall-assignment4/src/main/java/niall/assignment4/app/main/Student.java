package niall.assignment4.app.main;

public class Student implements Comparable<Student> {
	// All fields declared as String as some could contain letters and/or numbers.
	// This also makes reading in .csv more straightforward, and values can be
	// parsed if needed later.
	private String studentId;
	private String studentName;
	private String course;
	private String grade;

	public Student(String studentId, String studentName, String course, String grade) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.course = course;
		this.grade = grade;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	// toString override to output files in correct format.
	@Override
	public String toString() {
		return studentId + "," + studentName + "," + course + "," + grade;
	}

	// Comparable override to sort by grade, then nulls last, in natural order.
	@Override
	public int compareTo(Student that) {
		return that.grade.compareTo(this.grade);
	}

}
