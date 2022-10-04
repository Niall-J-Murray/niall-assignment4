package niall.assignment4.app.main;

import niall.assignment4.app.service.Service;

public class App {

	private Service service = new Service();

	public static void main(String[] args) {
		new App().execute();
	}

	private void execute() {
		// File path to student-master-list.csv stored in AppConstants class.
		String file = AppConstants.FILE_IN;
		// File to be processed passed to Service class.
		service.processFile(file);
	}

}
