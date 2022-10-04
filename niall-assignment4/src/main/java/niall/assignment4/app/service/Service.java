package niall.assignment4.app.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import niall.assignment4.app.main.AppConstants;
import niall.assignment4.app.main.Student;

public class Service {

	// Method to take in file to be processed, and call other methods to break down
	// processing into logical steps.
	public void processFile(String filePath) {
		// Names of courses to be filtered and sorted.
		String course1 = "APMTH";
		String course2 = "COMPSCI";
		String course3 = "STAT";

		// File is passed to first two methods. First to count number of students, then
		// to read the file contents.
		int numberOfStudents = studentCounter(filePath);
		String fileAsString = readCsv(filePath);

		// File contents parsed to array of Student objects.
		Student[] students = buildStudentArray(fileAsString, numberOfStudents);
		// Students are sorted, first by grade then separated by course name.
		Student[] sortedStudents = sortStudents(students, course1);
		// Method to print to file.
		outputFile(sortedStudents, course1);
		// Previous two methods called again to sort by different class names.
		sortedStudents = sortStudents(students, course2);
		outputFile(sortedStudents, course2);
		sortedStudents = sortStudents(students, course3);
		outputFile(sortedStudents, course3);
	}

	public int studentCounter(String filePath) {
		int numberOfStudents = 0;
		BufferedReader fileReader = null;

		try {
			fileReader = new BufferedReader(new FileReader(filePath));

			// Skip first line containing table headings, then increment count for every
			// line read in until null.
			fileReader.readLine();
			while ((fileReader.readLine()) != null) {
				numberOfStudents++;
			}
		} catch (IOException e) {
			System.out.println("Error reading file!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("I/O Exception Error!");
				e.printStackTrace();
			}
		}
		return numberOfStudents;
	}

	public String readCsv(String filePath) {

		BufferedReader fileReader = null;
		String fileString = "";

		try {
			fileReader = new BufferedReader(new FileReader(filePath));
			String fileLine;
			// To read but not store first line of table headers.
			// File read in and returned as String, to be parsed to array later.
			fileReader.readLine();
			while ((fileLine = fileReader.readLine()) != null) {
				fileString += fileLine + ",";
			}
		} catch (IOException e) {
			System.out.println("Error reading file!");
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				System.out.println("I/O Exception Error!");
				e.printStackTrace();
			}
		}
		return fileString;
	}

	public Student[] buildStudentArray(String fileString, int numberOfStudents) {

		// Split String from file by "," and store as String array.
		String[] studentArray = fileString.split(",");
		// Convert string array to array of Student objects.
		Student[] students = new Student[numberOfStudents];
		// for loop with two incremental variables.
		// i++ to step through and update each index of student array.
		// j+=4 to add the 4 associated pieces of data to respective student in array.
		for (int i = 0, j = 0; i < students.length && j < studentArray.length; i++, j += 4) {
			students[i] = new Student(studentArray[j], studentArray[j + 1], studentArray[j + 2], studentArray[j + 3]);
		}
		return students;
	}

	public Student[] sortStudents(Student[] students, String courseName) {

		// Student implements Comparable to sort students by grade and handle null
		// entries by placing them last.
		Arrays.sort(students, Comparator.nullsLast(Comparator.naturalOrder()));
		String courseNameAllCaps = courseName.toUpperCase();
		// As number of students in each course is unknown, array length is set to same
		// as array of all students.
		// Null entries will be assigned as not null(i.e. "0") below, and filtered out
		// later.
		Student[] studentsByCourse = new Student[students.length];

		// Check the course name passed to method and add students of that course to
		// array to be returned. Any remaining array indexes set to "0" to filter later.
		if (courseNameAllCaps.equals("APMTH")) {
			for (int i = 0, j = 0; i < students.length && j < studentsByCourse.length; i++, j++) {
				if (students[i].getCourse().contains(courseNameAllCaps)) {
					studentsByCourse[j] = students[i];
				} else {
					studentsByCourse[j] = new Student("0", "0", "0", "0");
				}
			}
		} else if (courseNameAllCaps.equals("COMPSCI")) {
			for (int i = 0, j = 0; i < students.length; i++, j++) {
				if (students[i].getCourse().contains(courseNameAllCaps)) {
					studentsByCourse[j] = students[i];
				} else {
					studentsByCourse[j] = new Student("0", "0", "0", "0");
				}
			}
		} else if (courseNameAllCaps.equals("STAT")) {
			for (int i = 0, j = 0; i < students.length; i++, j++) {
				if (students[i].getCourse().contains(courseNameAllCaps)) {
					studentsByCourse[j] = students[i];
				} else {
					studentsByCourse[j] = new Student("0", "0", "0", "0");
				}
			}
		} else {
			System.out.println("Invalid course type.");
		}

		return studentsByCourse;
	}

	public void outputFile(Student[] studentsByCourse, String courseName) {

		// Sorted and filtered array for each course passed, along with course name.
		// Array printed to appropriate file path based on course name.
		String fileName = "";
		if (courseName.equals("APMTH")) {
			fileName = AppConstants.FILE_OUT1;
		} else if (courseName.equals("COMPSCI")) {
			fileName = AppConstants.FILE_OUT2;
		} else if (courseName.equals("STAT")) {
			fileName = AppConstants.FILE_OUT3;
		} else {
			System.out.println("Error printing file!");
			System.out.println("Invalid course selection.");
		}

		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			// Print file header on first line of each file.
			writer.write(AppConstants.FILE_HEADER);

			// Iterate through array, if index is not null or "0", write to file.
			for (Student student : studentsByCourse) {
				if (student.getCourse() != null && student.getCourse() != "0") {
					writer.newLine();
					writer.write(student.toString());
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file!");
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("I/O Exception Error!");
				e.printStackTrace();
			}
		}
	}
}
