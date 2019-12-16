package ergasia2;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EnterAppointment {

	static Scanner m = new Scanner(System.in);

	public static String[] questionsToBegin() {

		String[] t1 = new String[3];
		boolean found = false;

		String eidikotita = Menu.chooseService(); // �������� ����������
		t1[1] = eidikotita;
		System.out.println("���������");
		String search = m.next();// ������������ ���� ���������
		do {
			if (Menu.filterAndPrintServices(search, eidikotita) == false) {
				System.out.println("����� ������� ��� ���������� ��� ��������: " + eidikotita + ";");
				String a = m.next();
				if (a.contains("���")) {
					System.out.println("���������");
					search = m.next();// ������������ ���� ���������
				} else {
					eidikotita = Menu.chooseService(); // �������� ����������
					t1[1] = eidikotita;
					System.out.println("���������");
					search = m.next();
				}

			} else {
				found = true;
			}
		} while (!found);

		// ������� ����� �� �� ������������
		// ��� ���������� ���
		m.nextLine();
		String choice = m.nextLine();// ���������� �� ������ ��� ������� ���������
		t1[2] = choice;

		String ans = Menu.chooseCriterion(); // ���������� �� ��������
		t1[0] = ans;
		return t1;
	}

	
	public static int choiceDuration(String[] t1) {

		int duration = 0;

		for (int j = 0; j < 22; j++) {

			if (t1[1].toLowerCase().contains(Services.eidikotites.get(j))) {

				for (int i = 0; i < Services.eidikotitesoles.get(j).size(); i++) {

					if (Services.eidikotitesoles.get(j).get(i).getName().toLowerCase().contains(t1[2])) {
						duration = Services.eidikotitesoles.get(j).get(i).getDuration();
						break;
					}
				}
			}
		}
		return duration;
	}
		
	public static void basedOnDate(String[] t1, int duration, Table[][] calendar) { // ���� �����������--> ans == "1"

		Random rand = new Random();
		int validcode = 0;
		String stringid = null;
		String stringvalidcode = null;
		String stringcode = null;
		String eidikotita = t1[1];
		String anstime = "����";
		int exists = 0;
		String protasi = "no protasi";
		int day = 0;
		int month = 0;

		while ((anstime.toLowerCase().contains("����")) || (exists == 0)) { // ����� ����� �� ��� ���������� ���� ���
																			// �����
																			// ������ ��� ����� �� ������� ��� ������
																			// ���
			int md[] = new int[3];
			if (protasi.contains("�������")) {
				md = suggestion(month, day, eidikotita, duration, calendar);
				day = md[1];
				month = md[0];
				exists = md[2];
			} else {
				System.out.println("�������� �������� ��� ���������� ��� ����������");
				System.out.println("�����: ");
				month = m.nextInt();
				System.out.println("�����: ");
				day = m.nextInt();

				exists = calendar[month][day].checkingFreehours(calendar[month][day].getTable(), eidikotita, duration);
				// �������� ���������� ��� ��� ������ �������= �������� ����� ������� ���� �����
				// ��� �������
				// ���� ������� �� ��������� = ������� ������ �� ����� ���� ��� �������.
			}
			int totalcells;

			if (exists != 0) { // �������� ����� ����

				System.out.println(
						"�������� ��� ��� ��� ���������� (�������� ���� �� ��� ��� ������� ����� ��������� ���)");
				anstime = m.next();

				if (!(anstime.contains("����"))) { // ��� ������� ������ ��� ��� ���� ���

					for (int k = 1; k <= 16; k++) {

						if (duration <= 30) {

							if (calendar[month][day].getTable()[k][0].contains(anstime)) {

								validcode = rand.nextInt(100000);
								stringvalidcode = String.valueOf(validcode);
								stringid = String.valueOf(Client.clients.get(Client.numOfClients - 1).getId());
								stringcode = stringvalidcode + stringid;
								calendar[month][day].getTable()[k][exists] = "  " + stringcode + "     ";
								break;
							}
						} else {
							totalcells = (duration / 30);
							if (calendar[month][day].getTable()[k][0].contains(anstime)) {
								validcode = rand.nextInt(100000);
								int ip = 0;
								if ((duration % 30) == 0) {
									ip = 0;
								} else {
									ip = 1;
								}
								for (int l = 0; l < (totalcells + ip); l++) {
									stringvalidcode = String.valueOf(validcode);
									stringid = String.valueOf(Client.clients.get(Client.numOfClients - 1).getId());
									stringcode = stringvalidcode + stringid;
									calendar[month][day].getTable()[k + l][exists] = "  " + stringcode + "     ";
								}
								break;
							}
						}   // ���� �� �������� ������� ���� ��� ���� ��� ���������� �� �� �������� ���
							// ������ ��� �� ���������� ����� ��� ������
					}
				} else { // ��� ������ ���� ������ ��� ���� ��� ��� ������� ������ ��� --> ��� ����
							// ������
					System.out.println("�������������� ��� �� ���������� ���� ��� ������ �� ��� \n"
							+ "��� �������, �������� ��������� ��������� �������� ��� �� ��� �������������� � ������ \n"
							+ "��� �� ��� ����������� ��������� ���� �� �������� �����������");
					protasi = m.next();
					exists = 0;
					// anstime = "1";
				}
				// ������ ���� ������������ table ��� ������� � ������� ��� ���������� ��� ����
				// -�� ���
				// k ��� ������ ��� ����� ��� ������� ��� ��� checkingFreehours ��� ��� exists-
				// ��� toString
				// ��� ������ ���, ��� ����� �� ��� ������ ��� �� numOfClients -1 ���� ����

			} else { // �� ���� ������ ���� ������ ��� ���� (exists == 0) --> ��� ���� ������
				System.out.println("��� ������� ��������� ���� ��� ���� ��� ������������ ����,\n "
						+ "�� ���������� ������������ �� �������� ��� ��� �������� ���������� � ������ ��� �� ��� �����������\n "
						+ "��������� ���� �� �������� �����������!");
				protasi = m.next();
			}
		}
		System.out.println("���� ��� " + Client.clients.get(Client.numOfClients - 1).getName() + " "
				+ Client.clients.get(Client.numOfClients - 1).getSurname() + "!\n�� �������� ��� ������������ ��� ��� "
				+ day + "/" + month + "/2019\n" + "�������: " + t1[2] + " (���������: " + t1[1] + ")\n"
				+ "������� ������������: " + stringvalidcode);
	}
	

	// kat= "katachorish", doc = null  --> ��� ��� ������ ����������
	// � ���� ������� ��������������� ��� ���� ������ ��������, ��� ������� �� ��� ������ ��� ���������� �� ���� ������� ���������
	// � ������ ���������� ����������� ����������� ������� ��� ������� 
	
	public static void basedOnDateAndEmp(String[] t1, int duration, String kat, String doc, Table[][] calendar) {   
		// ����  ��� �������--> ans == "2"

		Random rand = new Random();
		int validcode = 0;
		String stringid = null;
		String stringvalidcode = null;
		String stringcode = null;
		String anstime = " ���� ";
		int exists = 0;
		String protasi = null;
		String eidikotita = t1[1];
		ArrayList<String> doc_names = new ArrayList<String>();
		doc_names = Employees.returnDocNames(eidikotita);
		int doc_num = doc_names.size();
		int day = 0;
		int month = 0;
		int md[] = new int[3];
		String doctor = null;

		while ((anstime == " ���� ") || (exists == 0)) { // ����� ����� �� ��� ���������� ���� ��� �����
															// ������ ��� ����� �� ������� ��� ������ ���

			if (protasi != "�������") {
				System.out.println("�������� �������� ��� ���������� ��� ����������");
				System.out.println("�����: ");
				month = m.nextInt();
				System.out.println("�����: ");
				day = m.nextInt();

				if (kat == "katachorish") {
					System.out.println("���� �������� ��� ������ ��� ����������");
					for (int d = 0; d <= doc_num; d++) {
						System.out.println(doc_names.get(d));
					}
					doctor = m.nextLine();
				} else {
					doctor = doc;
				}

				exists = calendar[month][day].checkingFreehours(calendar[month][day].getTable(), eidikotita, duration,
						doctor); // � ���� ��� �������

			} else {
				if (kat == "katachorish") {
					System.out.println("�������� ��� ������ ��� ����������");
					doctor = m.nextLine();
				} else {
					doctor = doc;
				}
				md = suggestion(month, day, eidikotita, duration, doctor, calendar);
				day = md[1];
				month = md[0];
				exists = md[2];
			}

			int totalcells = 0;

			if (exists != 0) { // ����� ����� ����
				System.out.println(
						"�������� ��� ��� ��� ���������� (�������� ���� �� ��� ��� ������� ����� ��������� ���)");
				anstime = m.nextLine();

				if (anstime != "����") { // ��� ������� ������ ��� ��� ���� ���

					for (int k = 1; k <= 16; k++) {

						if (duration <= 30) {
							if (calendar[month][day].getTable()[k][0] == anstime) {
								validcode = rand.nextInt(100000);
								stringvalidcode = String.valueOf(validcode);
								stringid = String.valueOf(Client.clients.get(Client.numOfClients - 1).getId());
								stringcode = stringvalidcode + stringid;
								calendar[month][day].getTable()[k][exists] = "   " + stringcode;
								break;
							}
						} else {// ���� �� �������� ������� ���� ��� ���� ��� ���������� �� �� �������� ���
								// ������ ��� �� ���������� ����� ��� ������
							totalcells = (duration / 30);
							if (calendar[month][day].getTable()[k][0] == anstime) {
								int ip = 0;
								if ((duration % 30) == 0) {
									ip = 0;
								} else {
									ip = 1;
								}
								for (int l = 0; l < (totalcells + ip); l++) {
									validcode = rand.nextInt(100000);
									stringvalidcode = String.valueOf(validcode);
									stringid = String.valueOf(Client.clients.get(Client.numOfClients - 1).getId());
									stringcode = stringvalidcode + stringid;
									calendar[month][day].getTable()[k + l][exists] = "   " + stringcode;
								}
								break;
							}
						}
					}
				} else { // ��� ��� ������� ������ ��� ��� ����� ��� ����� --> ��� ���� ������
					System.out.println("�������������� ��� �� ���������� ���� ��� ������ �� ��� "
							+ "��� �������, �������� ��������� ��������� �������� ��� �� ��� �������������� � ������ "
							+ "��� �� ��� ����������� ��������� ���� �� �������� �����������! ");
					protasi = m.next();
				}
			} else { // ��� ����� ����� ���� --> ���� ������
				System.out.println(
						"��� ������� ��������� ���� ��� ���� ��� ������������ ���� �� ��� ������������ ������, "
								+ "�� ���������� ������������ �� �������� ��� ��� �������� ���������� � ������ ��� �� ��� ����������� "
								+ "��������� ���� �� �������� �����������! ");
				protasi = m.next();
			}
		}
		System.out.println("���� ��� " + Client.clients.get(Client.numOfClients - 1).getName() + " "
				+ Client.clients.get(Client.numOfClients - 1).getSurname() + "!\n�� �������� ��� ������������ ��� ��� "
				+ day + "/" + month + "/2019\n" + "�������: " + t1[2] + " (���������: " + t1[1] + ")\n" + "������: "
				+ doctor + "\n������� ������������: " + stringvalidcode);

	}
	
	public static int[] suggestion(int month, int day, String eidikotita, int duration, Table[][] calendar) { // ANS = 1

		int[] md = new int[3];
		int ex1 = 0;
		int d = 0;
		while (ex1 == 0) { // ������� ��� 1 ���� ���� ����� ����
			if (day == 30) {
				ex1 = calendar[month + 1][1 + d].checkingFreehours(calendar[month + 1][1 + d].getTable(), eidikotita,
						duration);
				if (ex1 != 0) {
					md[1] = 1 + d;
					md[0] = month + 1;
					System.out.println("����� ����� �� ���������� ���� ��� " + md[1] + "/" + md[0]);
				}
			} else {
				ex1 = calendar[month][day + d + 1].checkingFreehours(calendar[month][day + d + 1].getTable(),
						eidikotita, duration);
				if (ex1 != 0) {
					md[1] = day + d + 1;
					md[0] = month;
					System.out.println("����� ����� �� ���������� ���� ��� " + md[1] + "/" + md[0]);
				}
			}
			d++;
		}
		md[2] = ex1;
		return md; // ����� �� ��� ����� ��� ���� ��� ��� ���� ��� ����������� ��� ��� ����������
					// ��� ���� ���� ������ ������ ��� �� �������������� ���������� ����� �� �������
					// ��������� ��� ���� ��� ���� ���.
	}
	
	public static int[] suggestion(int month, int day, String eidikotita, int duration, String doctor,
			Table[][] calendar) { // ANS = 2

		int[] md = new int[3];
		int ex1 = 0;
		int d = 0;

		while (ex1 == 0) { // ������� ��� 1 ���� ���� ����� ����
			if (day == 30) {
				ex1 = calendar[month][day].checkingFreehours(calendar[month][day].getTable(), eidikotita, duration,
						doctor);
				if (ex1 != 0) {
					md[1] = 1 + d;
					md[0] = month + 1;
				}
			} else {
				ex1 = calendar[month][day].checkingFreehours(calendar[month][day].getTable(), eidikotita, duration,
						doctor);
				if (ex1 != 0) {
					md[1] = day + d + 1;
					md[0] = month;
				}
			}
			d++;
		}
		md[2] = ex1;

		return md;

	}
	
	
	

}