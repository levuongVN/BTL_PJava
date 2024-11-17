import hotel.Rooms;
import hotel.customers;
import hotel.eployees;
import java.util.Scanner;

public class BTL_LeVanVuong {
    public static void main(String[] args) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("----------------------------------");
            System.out.println("Chương trình quản lý khách sạn");
            System.out.println("Vui lòng chọn chương trình");
            System.out.println("1. Quản lý phòng");
            System.out.println("2. Quản lý nhân sự");
            System.out.println("3. Quản lý khách hàng");
            System.out.println("4. Thoát");
            System.out.print("Chọn chương trình: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 4:
                    System.out.println("Thoát chương trình!");
                    break;
                case 1:
                    Rooms.main(args);
                    break;
                case 2:
                    eployees.main(args);
                    break;
                case 3:
                    customers.main(args);
                    break;
                default:
                    System.out.println("Chức năng không tồn tại");
                    break;
            }
        } while (choice != 4);
        
        scanner.close();
    }
}
