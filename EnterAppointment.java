package ergasia2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class EnterAppointment {

	static Scanner m = new Scanner(System.in);

	public static String[] questionsToBegin() {

		String[] t1 = new String[3];
		boolean found = false;
		String search = null;

		String eidikotita = Menu.chooseService(); // �������� ����������
		t1[1] = eidikotita;
		System.out.println("���������");
		boolean inserted = false;

		while (inserted == false) {
			try {
				search = m.nextLine();// ������������ ���� ���������
				inserted = true;
			} catch (InputMismatchException e) {
				System.out.println("insert the required info.");
			}
		}

		do {
			if (Menu.filterAndPrintServices(search, eidikotita) == false) {
				System.out.println("����� ������� ��� ���������� ��� ��������: " + eidikotita + ";");
				String a = null;
				boolean correct = false;

				while (correct == false) {
					try {
						a = m.next();
						correct = true;
					} catch (InputMismatchException e) {
						System.out.println("�������� ��� � ���.");
					}
				}

				correct = false;

				while (correct == false) {
					try {
						if (a.contains("���")) {
							System.out.println("���������");
							search = m.nextLine();// ������������ ���� ���������
							correct = true;
						} else {
							eidikotita = Menu.chooseService(); // �������� ����������
							t1[1] = eidikotita;
							System.out.println("���������");
							search = m.nextLine();
							correct = true;
						}
					} catch (InputMismatchException e) {
						System.out.println("insert the required info.");
					}
				}

			} else {
				found = true;
			}
		} while (!found);

		// ������� ����� �� �� ������������
		// ��� ���������� ���
		//m.nextLine();
		String choice = null;
		boolean correct = false;

		while (correct == false) {
			try {
				choice = m.nextLine();// ���������� �� ������ ��� ������� ���������
				correct = true;

			} catch (InputMismatchException e) {
				System.out.println("insert the required info.");
			}
		}

		t1[2] = choice;

		String ans = Menu.chooseCriterion(); // ���������� �� ��������
		t1[0] = ans;
		return t1;
	}

	public static double [] choiceDuration(String[] t1) {


		double duration [] = new double[2];

		for (int j = 0; j < 22; j++) {
			
			if (t1[1].toLowerCase().contains(Services.eidikotites.get(j))) {
				
				for (int i = 0; i < Services.eidikotitesoles.get(j).size(); i++) {
					//System.out.println(Services.eidikotitesoles.get(j).get(i).getName());
					//System.out.println(t1[2]);
					//boolean b = Services.eidikotitesoles.get(j).get(i).getName().contains(t1[2]);
					//System.out.println(b);
					if (Services.eidikotitesoles.get(j).get(i).getName().contains(t1[2])) {
						//System.out.println("wsdcsaxc");
						duration[0] = Services.eidikotitesoles.get(j).get(i).getDuration();
						duration[1] = Services.eidikotitesoles.get(j).get(i).getCost();
						//System.out.println(duration[0] + "+"  + duration[1]);
						break;
					}
				}
			}
		}
		return duration;
	}

	public static void basedOnDate(String[] t1, double [] duration, Table[][] calendar) { // ���� �����������--> ans == "1"
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
				md = suggestion(month, day, eidikotita, duration[0], calendar);
				day = md[1];
				month = md[0];
				exists = md[2];
			} else {
				System.out.println("�������� �������� ��� ���������� ��� ����������");
				System.out.println("�����: ");
				boolean ok2 = false;

				while (ok2 == false) {// each variable checks if month and day are inserted correctly
					try {
						month = m.nextInt();
						System.out.println("�����: ");
						day = m.nextInt();
						ok2 = true;
					} catch (InputMismatchException e) {
						System.out.println("insert the required info.");
					}
				}

				try {
					exists = calendar[month][day].checkingFreehours(calendar[month][day].getTable(), eidikotita,
							duration[0]);
					System.out.println(exists);
					System.out.println(duration[0]);
					// �������� ���������� ��� ��� ������ �������= �������� ����� ������� ���� �����
					// ��� �������
					// ���� ������� �� ��������� = ������� ������ �� ����� ���� ��� �������.
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("error in array's bounds.");
				}
			}
			double totalcells;

			if (exists != 0) { // �������� ����� ����

				System.out.println(
						"�������� ��� ��� ��� ���������� (�������� ���� �� ��� ��� ������� ����� ��������� ���)");
				boolean correct = false;

				while (correct == false) {
					try {
						anstime = m.next();
						correct = true;
					} catch (InputMismatchException e) {
						System.out.println("insert the required info.");
					}
				}

				if (!(anstime.contains("����"))) { // ��� ������� ������ ��� ��� ���� ���

					for (int k = 1; k <= 16; k++) {

						if (duration[0] <= 30) {

							try {
								if (calendar[month][day].getTable()[k][0].contains(anstime)) {

									validcode = rand.nextInt(100000);
									stringvalidcode = String.valueOf(validcode);
									stringid = String.valueOf(Client.clients.get(Client.numOfClients - 1).getId());
									stringcode = stringvalidcode + stringid;
									calendar[month][day].getTable()[k][exists] = "  " + stringcode + "     ";
									break;
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("error in array's bounds.");
							}
						} else {
							totalcells = (duration[0] / 30);

							try {
								if (calendar[month][day].getTable()[k][0].contains(anstime)) {
									validcode = rand.nextInt(100000);
									int ip = 0;
									if ((duration[0] % 30) == 0) {
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
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("error in array's bounds.");
							}
						} // ���� �� �������� ������� ���� ��� ���� ��� ���������� �� �� �������� ���
							// ������ ��� �� ���������� ����� ��� ������

					}
				} else { // ��� ������ ���� ������ ��� ���� ��� ��� ������� ������ ��� --> ��� ����
							// ������
					System.out.println("�������������� ��� �� ���������� ���� ��� ������ �� ��� \n"
							+ "��� �������, �������� �������� ��������� �������� ��� �� ��� �������������� � ������ \n"
							+ "��� �� ��� ����������� ��������� ���� �� �������� �����������.\n ������� � �� ���� ����������.");

					correct = false;

					while (correct == false) {
						try {
							protasi = m.next();
							correct = true;
						} catch (InputMismatchException e) {
							System.out.println("insert the required info.");
						}
					}
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
						+ "��������� ���� �� �������� �����������\n ������� � ��� ����������");

				boolean correct = false;

				while (correct == false) {
					try {
						protasi = m.next();
						correct = true;
					} catch (InputMismatchException e) {
						System.out.println("insert the required info.");
					}
				}

			}
		}
		System.out.println(duration[0] + "   " + duration[1]);
		System.out.println("���� ��� " + Client.clients.get(Client.numOfClients - 1).getName() + " "
				+ Client.clients.get(Client.numOfClients - 1).getSurname() + "!\n�� �������� ��� ������������ ��� ��� "
				+ day + "/" + month + "/2019\n" + "���: " + anstime + "\n" + "�������: " + t1[2] + " (���������: "
				+ t1[1] + ")\n" + "������: " + duration[1] + "$" + "\n" + "������� ������������: " + stringvalidcode);
	}
	
	// kat= "katachorish", doc = null --> ��� ��� ������ ����������
	// � ���� ������� ��������������� ��� ���� ������ ��������, ��� ������� �� ���
	// ������ ��� ���������� �� ���� ������� ���������
	// � ������ ���������� ����������� ����������� ������� ��� �������

	public static void basedOnDateAndEmp(String[] t1, double [] duration, String kat, String doc, Table[][] calendar) {
		// ���� ��� �������--> ans == "2"

		Random rand = new Random();
		int validcode = 0;
		String stringid = null;
		String stringvalidcode = null;
		String stringcode = null;
		String anstime = " ���� ";
		int exists = 0;
		String protasi = "���";
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

			if (!(protasi.equals("�������"))) {
				System.out.println("�������� �������� ��� ���������� ��� ����������");

				boolean ok1 = true;

				while (ok1) {

					try {
						System.out.println("�����: ");
						month = m.nextInt();
						System.out.println("�����: ");
						day = m.nextInt();
						ok1 = false;
					} catch (InputMismatchException e) {
						System.out.println("insert the required info.");
					}
				}

				if (kat == "katachorish") {
					System.out.println("�������� ��� ������ ��� ����������:");
					for (int d = 0; d < doc_num; d++) {
						System.out.println(doc_names.get(d));
					}
					wannabeMain.in.nextLine();
					doctor = wannabeMain.in.nextLine();

				} else {
					doctor = doc;
				}

				
					exists = calendar[month][day].checkingFreehours(calendar[month][day].getTable(), eidikotita,
							duration[0], doctor); // � ���� ��� �������
				
			} else {
				if (kat == "katachorish") {
					System.out.println("�������� ��� ������ ��� ����������");
					for (int iii = 0; iii < Employees.employees.size(); iii++) {
						System.out.println(Employees.employees.get(iii));
					}
					doctor = wannabeMain.in.nextLine();

				} else {
					doctor = doc;
				}
				md = suggestion(month, day, eidikotita, duration[0], doctor, calendar);
				day = md[1];
				month = md[0];
				exists = md[2];
			}

			double totalcells = 0;

			if (exists != 0) { // ����� ����� ����
				System.out.println(
						"�������� ��� ��� ��� ���������� (�������� ���� �� ��� ��� ������� ����� ��������� ���)");
				boolean correct = false;
				while (correct == false) {
					try {
						anstime = m.nextLine();
						correct = true;
					} catch (InputMismatchException e) {
						System.out.println("insert the required info.");
					}
				}

				if (anstime != "����") { // ��� ������� ������ ��� ��� ���� ���

					for (int k = 1; k <= 16; k++) {

						if (duration[0] <= 30) {

							try {
								if (calendar[month][day].getTable()[k][0] == anstime) {
									validcode = rand.nextInt(100000);
									stringvalidcode = String.valueOf(validcode);
									stringid = String.valueOf(Client.clients.get(Client.numOfClients - 1).getId());
									stringcode = stringvalidcode + stringid;
									calendar[month][day].getTable()[k][exists] = "   " + stringcode;
									break;
								}
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("error in array's bounds.");
							}

						} else {// ���� �� �������� ������� ���� ��� ���� ��� ���������� �� �� �������� ���
								// ������ ��� �� ���������� ����� ��� ������
							totalcells = (duration[0] / 30);

							try {
								if (calendar[month][day].getTable()[k][0] == anstime) {
									int ip = 0;
									if ((duration[0] % 30) == 0) {
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
							} catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("error in array's bounds.");
							}
						}
					}
				} else { // ��� ��� ������� ������ ��� ��� ����� ��� ����� --> ��� ���� ������
					System.out.println("�������������� ��� �� ���������� ���� ��� ������ �� ��� "
							+ "��� �������, �������� �������� ��������� �������� ��� �� ��� �������������� � ������ "
							+ "��� �� ��� ����������� ��������� ���� �� �������� �����������!\n ������� � ��� ���������� ");

					correct = false;
					while (correct == false) {
						try {
							protasi = m.next();
							correct = true;
						} catch (InputMismatchException e) {
							System.out.println("insert the required info.");
						}
					}
				}
			} else { // ��� ����� ����� ���� --> ���� ������
				System.out.println(
						"��� ������� ��������� ���� ��� ���� ��� ������������ ���� �� ��� ������������ ������, "
								+ "�� ���������� ������������ �� �������� ��� ��� �������� ���������� � ������ ��� �� ��� ����������� "
								+ "��������� ���� �� �������� �����������!\n ������� � ��� ���������� ");

				boolean correct = false;
				while (correct == false) {
					try {
						protasi = m.next();
						correct = true;
					} catch (InputMismatchException e) {
						System.out.println("insert the required info.");
					}
				}

			}
		}
		System.out.println("���� ��� " + Client.clients.get(Client.numOfClients - 1).getName() + " "
				+ Client.clients.get(Client.numOfClients - 1).getSurname() + "!\n�� �������� ��� ������������ ��� ��� "
				+ day + "/" + month + "/2019\n" + "���: " + anstime + "\n" + "�������: " + t1[2] + " (���������: " + t1[1] + ")\n" + "������: "
				+ doctor + "������: " + duration[1] + "\n" + "\n������� ������������: " + stringvalidcode);

	}

	public static int[] suggestion(int month, int day, String eidikotita, double duration, Table[][] calendar) { // ANS = 1

		int[] md = new int[3];
		int ex1 = 0;
		int d = 0;
		while (ex1 == 0) { // ������� ��� 1 ���� ���� ����� ����

			//try {
				if (day == 30) {
					ex1 = calendar[month + 1][1].checkingFreehours(calendar[month + 1][1].getTable(),
							eidikotita, duration);
					if (ex1 != 0) {
						md[1] = 1;
						md[0] = month + 1;
						System.out.println("����� ����� �� ���������� ���� ��� " + md[1] + "/" + md[0]);
					}
					day = 1;
				
				} else {
					ex1 = calendar[month][day + d + 1].checkingFreehours(calendar[month][day + d + 1].getTable(),
							eidikotita, duration);
					
					day = day + d + 1;
					if (ex1 != 0) {
						md[1] = day;
						md[0] = month;
						System.out.println("����� ����� �� ���������� ���� ��� " + md[1] + "/" + md[0]);
					}
				}
			//} catch (ArrayIndexOutOfBoundsException e) {
				//System.out.println("error in array's bounds");
			//}

			d++;
		
		}

		md[2] = ex1;
		return md; // ����� �� ��� ����� ��� ���� ��� ��� ���� ��� ����������� ��� ��� ����������
					// ��� ���� ���� ������ ������ ��� �� �������������� ���������� ����� �� �������
					// ��������� ��� ���� ��� ���� ���.
	}

	public static int[] suggestion(int month, int day, String eidikotita, double duration, String doctor,
			Table[][] calendar) { // ANS = 2

		int[] md = new int[3];
		int ex1 = 0;
		int d = 0;

		while (ex1 == 0) { // ������� ��� 1 ���� ���� ����� ����

			try {
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
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("error in array's bounds.");
			}

			d++;
		}
		md[2] = ex1;

		return md;

	}

}