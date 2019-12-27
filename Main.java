/**
 * 
 */
package ergasia2;

import java.util.Scanner;

/**
 * @author HP
 *
 */
public class Main {

	public static Scanner sc = new Scanner(System.in);

	public static void loadObjects(Table[][] calendar, String path) { // CREATE Services, Employees, Calendar(type: Table)
		
		Services.listEidikotites();
		Services.addAllServices();
		boolean err = true;

		int size = Services.eidikotites.size();
		for (int i = 0; i < size ; i++) {
			err = true;
			while (err) {
				try {
					CreateObjects.createServices(
							path + Services.eidikotites.get(i) + ".txt");
					err = false;
				} catch (NumberFormatException e) {
					System.err.println("����� ������������ �������� ��o �����o " + Services.eidikotites.get(i)
							+ " ��� ���������������!");
					System.err.println("��������� �� ������ ��� ������������ ��!");
					path = sc.nextLine();
				} 
				
			}
		}

		err = true;
		while (err) {
			
			try {
				CreateObjects.createEmployees("C:\\Users\\HP\\Desktop\\java2_\\exe2\\employees.txt");
				err = false;
			} catch (NumberFormatException e) {
				System.err.println("����� ������������ �������� ��o �����o employees ��� ���������������!");
			}
		}

		
		for (int i = 1; i <= 12; i++) {
			for (int j = 1; j <= 30; j++) {
				Table table = new Table();
				calendar[i][j] = table;
			}
		}
		
		/*
		// ��� �� ����� ��� ����� �������������� 
		for (int i = 0; i < 22; i++) {
		  System.out.println(Services.eidikotitesoles.get(i));
		}
		*/
		
		/*
		// ��� �� ����� ��� ����� �������������� 
		for (int i = 0; i < Employees.employees.size(); i++) {
			System.out.println(Employees.employees.get(i)); 
		}
		*/
		
		
	}
		
		
		
	public static void maaain() {
		
		
		
		
		
		
		
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	
	
}
