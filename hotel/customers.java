package hotel;
import java.io.BufferedReader; // Các thư viện cần có để làm việc với file
import java.io.FileReader; // Thư viện này để đọc
import java.io.FileWriter;
import java.io.IOException; // In ra các thông báo lỗi
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

/*
- Class Khách hàng
+ họ tên (Kế thừa từ class nhân viên)
+ mã khách hàng (Kế thừa từ class nhân viên)
+ Ngày tháng năm sinh
+ Số cccd
+ Số đt
+ Ngày đặt phòng
+ Ngày đến
+ Ngày đi
+ Đánh giá chất lượng phục vụ
 */
class custumVariables{
    protected ArrayList<String> NameCustomers = new ArrayList<>();
    protected ArrayList<String> NumberRoomCustomers = new ArrayList<>();
    protected ArrayList<String> IdCustomers = new ArrayList<>();
    protected ArrayList<String> DateOfBirthCustomers = new ArrayList<>();
    protected ArrayList<String> CccdCustomers = new ArrayList<>();
    protected ArrayList<String> PhoneCustomers = new ArrayList<>();
    protected ArrayList<String> DateBookingCustomers = new ArrayList<>();
    protected ArrayList<String> DateArrivalCustomers = new ArrayList<>();
    protected ArrayList<String> DateDepartureCustomers = new ArrayList<>();
    protected ArrayList<String> CheckSttCustomers = new ArrayList<>();
}
class customersCheckFile extends custumVariables{
    String file = "/Users/levuong2005/Documents/GitHub/BTL_JAVA/Customers.csv";
    String line = "";
    String limiter = ",";
    public boolean Check_FileCus(){
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            while((line = reader.readLine()) != null){
                String[] data = line.split(limiter);
                IdCustomers.add(data[0]);
                NumberRoomCustomers.add(data[1]);
                NameCustomers.add(data[2]);
                DateOfBirthCustomers.add(data[3]);
                CccdCustomers.add(data[4]);
                PhoneCustomers.add(data[5]);
                DateBookingCustomers.add(data[6]);
                DateArrivalCustomers.add(data[7]);
                DateDepartureCustomers.add(data[8]);
                CheckSttCustomers.add(data[9]);
            }
            // System.out.println("Đọc file thành công");
            // for(int i = 0;i<IdCustomers.size();i++){
            //     System.out.println(IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
class CheckRoomsNumber extends CheckFile {
    public ArrayList<String> NumberRooms = new ArrayList<>();
    public void checkAndAddRooms() {
        if (Check_File()) {
            for (int i = 0; i < NumberRoom.size(); i++) {
                NumberRooms.add(NumberRoom.get(i));
            }
        }else{
            System.out.println("Không tìm thấy file");
        }
    }
}
class AddCustomers extends customersCheckFile{
    Scanner scanner = new Scanner(System.in);
    public boolean add(){
        if(Check_FileCus()){
            System.out.print("Nhập số lượng khách hàng muốn thêm: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            for(int i = 0;i<num;i++){
                System.out.print("Nhập mã khách hàng: ");
                String id = scanner.nextLine();
                for(int j = 0;j<IdCustomers.size();j++){
                    if(IdCustomers.get(j).equals(id)){
                        System.out.println("Mã khách hàng đã tồn tại, vui lòng nhập lại");
                        return false;
                    }
                }
                IdCustomers.add(id);
                System.out.println("Nhập số phòng: ");
                int numberRoom = scanner.nextInt();
                int check = 0;
                CheckRoomsNumber checkRoomsNumber = new CheckRoomsNumber();
                for(int j =0;j<checkRoomsNumber.NumberRooms.size();j++){
                    if(checkRoomsNumber.NumberRooms.get(j).equals(String.valueOf(numberRoom))){
                        for(int k = 0;k<NumberRoomCustomers.size();k++){
                            if(NumberRoomCustomers.get(k).equals(String.valueOf(numberRoom))){
                                System.out.println("Số phòng đã tồn tại, vui lòng nhập lại");
                                return false;
                            }
                        }
                        break;
                    }else{
                        check++;
                    }
                }
                if(check == checkRoomsNumber.NumberRooms.size()){
                    System.out.println("Số phòng không tồn tại, vui lòng nhập lại");
                    return false;
                }else{
                    NumberRoomCustomers.add(String.valueOf(numberRoom));
                }
                scanner.nextLine();
                System.out.println("Nhập họ tên khách hàng: ");
                NameCustomers.add(scanner.nextLine());
                System.out.println("Nhập ngày tháng năm sinh (dd/mm/yyyy): ");
                String dateInput = scanner.nextLine();
                try{
                    int age = LocalDate.now().getYear() - LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy")).getYear();
                    if(age < 18){
                        System.out.println("Khách hàng phải trên 18 tuổi");
                        return false;
                    }else{
                        DateOfBirthCustomers.add(dateInput);
                    }
                }catch(DateTimeParseException e){
                    System.out.println("Ngày tháng năm sinh không hợp lệ, vui lòng nhập lại");
                    return false;
                }
                System.out.println("Nhập số cccd: ");
                CccdCustomers.add(scanner.nextLine());
                System.out.println("Nhập số điện thoại: ");
                String phoneInput = scanner.nextLine().trim();
                // Remove country code if present
                if (phoneInput.startsWith("+84")) {
                    phoneInput = "0" + phoneInput.substring(3);
                } else if (phoneInput.startsWith("84")) {
                    phoneInput = "0" + phoneInput.substring(2);
                }

                // Check if the phone number is valid
                if (phoneInput.matches("0[0-9]{9}")) {
                    // Format the phone number
                    String formattedPhone = phoneInput.replaceFirst("(\\d{4})(\\d{3})(\\d{3})", "$1 $2 $3");
                    PhoneCustomers.add(formattedPhone);
                } else {
                    System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số, bắt đầu bằng số 0.");
                    return false;
                }
                System.out.println("Nhập ngày đặt phòng (dd/mm/yyyy): ");
                String dateBooking = scanner.nextLine();
                try {
                    // LocalDate.parse(dateBooking, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    // Kiểm tra xem ngày đặt phòng có trước ngày hiện tại không
                    if(LocalDate.parse(dateBooking, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now())){
                        DateBookingCustomers.add(dateBooking);
                    }else{
                        System.out.println("Ngày đặt phòng không hợp lệ, vui lòng nhập lại");
                        return false;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Ngày đặt phòng không hợp lệ, vui lòng nhập lại");
                    return false;
                }
                System.out.println("Nhập ngày đến (dd/mm/yyyy): ");
                String dateArrival = scanner.nextLine();
                System.out.println("Nhập ngày đi (dd/mm/yyyy): ");
                String dateDeparture = scanner.nextLine();
                if(LocalDate.parse(dateArrival, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(
                    LocalDate.parse(dateBooking, DateTimeFormatter.ofPattern("dd/MM/yyyy")))){
                        // Kiểm tra xem ngày đến có trước ngày đặt phòng không
                    System.out.println("Ngày đến không hợp lệ, vui lòng nhập lại");
                    return false;
                }else if(LocalDate.parse(dateDeparture, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(
                    LocalDate.parse(dateArrival, DateTimeFormatter.ofPattern("dd/MM/yyyy")))){
                        // Kiểm tra xem ngày đi có trước ngày đến không
                        System.out.println("Ngày đi không hợp lệ, vui lòng nhập lại");
                        return false;
                }else{
                    // Nếu ngày đến và ngày đi hợp lệ, thêm vào danh sách
                    DateArrivalCustomers.add(dateArrival);
                    DateDepartureCustomers.add(dateDeparture);
                    if(LocalDate.parse(dateArrival,DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(
                        LocalDate.now()) && LocalDate.parse(dateDeparture,DateTimeFormatter.ofPattern("dd/MM/yyyy")).isAfter(
                            LocalDate.now())){
                        CheckSttCustomers.add("Check-in");
                    }else if(LocalDate.parse(dateDeparture,DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(
                        LocalDate.now())){
                            CheckSttCustomers.add("Check-out");
                    }else{
                        CheckSttCustomers.add("Đang ở");
                    }
                }
                System.out.println("Thêm khách hàng thành công");
            }
            try (Writer writer = new FileWriter(file,true)) {
                for(int i = 0;i<IdCustomers.size();i++){
                    writer.write(IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Không tìm thấy file");
            return false;
        }
        return true;
    }
}
class DisplayCustomers extends KhachSan{
    Scanner scanner = new Scanner(System.in);
    customersCheckFile CheckFile = new customersCheckFile();
    @Override
    public void display(){
        if(CheckFile.Check_FileCus()){
            if(CheckFile.IdCustomers.size() == 1){
                System.out.println("Không có khách hàng nào, bạn muốn thêm khách hàng không? (Y/N)");
                String choice = scanner.nextLine();
                if(choice.equals("Y") || choice.equals("y")){
                    AddCustomers addCustomers = new AddCustomers();
                    addCustomers.add();
                }else{
                    System.out.println("Không có thông tin của khách hàng nào hiển thị cả!");
                }
            }else{
                for(int i = 1;i<CheckFile.IdCustomers.size();i++){
                    System.out.println(
                        "\nMã khách hàng: "+CheckFile.IdCustomers.get(i)+
                        "\n Số phòng: "+CheckFile.NumberRoomCustomers.get(i)+
                        "\n Họ tên: "+CheckFile.NameCustomers.get(i)+
                        "\n Ngày tháng năm sinh: "+CheckFile.DateOfBirthCustomers.get(i)+
                        "\n Số cccd: "+CheckFile.CccdCustomers.get(i)+
                        "\n Số điện thoại: "+CheckFile.PhoneCustomers.get(i)+
                        "\n Ngày đặt phòng: "+CheckFile.DateBookingCustomers.get(i)+
                        "\n Ngày đến: "+CheckFile.DateArrivalCustomers.get(i)+
                        "\n Ngày đi: "+CheckFile.DateDepartureCustomers.get(i)+
                        "\n Trạng thái: "+CheckFile.CheckSttCustomers.get(i)
                    );
                }
            }
        }else{
            System.out.println("Không tìm thấy file");
        }
    }
}

class CheckFileRooms extends CheckFile{
    public void updateRooms(String number, String stt){
        if(Check_File()){
            for(int i = 0;i<NumberRoom.size();i++){
                if(NumberRoom.get(i).equals(number)){
                    Stt.set(i,stt);
                    break;
                }
            }
            String file = "Room.csv";
            try (Writer writer = new FileWriter(file,false)) {
                writer.write(NumberRoom.get(0)+","+TypeRoom.get(0)+","+CostRoom.get(0)+","+Stt.get(0));
                for(int i = 1;i<NumberRoom.size();i++){
                    writer.write("\n"+NumberRoom.get(i)+","+TypeRoom.get(i)+","+CostRoom.get(i)+","+Stt.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void CostMustToPay(String dateArrival, String dateDeparture, String number){
        if(Check_File()){
            for(int i=0;i< NumberRoom.size();i++){
                if(Stt.get(i).equals("Check-out") && NumberRoom.get(i).equals(number)){
                    LocalDate arrivalDate = LocalDate.parse(dateArrival, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate departureDate = LocalDate.parse(dateDeparture, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    long daysBetween = ChronoUnit.DAYS.between(arrivalDate, departureDate);
                    String costSplit = CostRoom.get(i).split("\\$")[0];
                    // Lấy số tiền trước dấu $ và bỏ dấu kí tự $ đi, tránh lỗi khi chuyển đổi
                    // Do $ là kí tự đặc biệt nên phải dùng \\$ để loại bỏ $ ra làm hai phần tử
                    // Nếu không dùng \\$ thì kết quả của costSplit sẽ là 1000.0$
                    // System.out.println(costSplit);
                    String cost = String.valueOf(daysBetween * Double.parseDouble(costSplit));
                    System.out.println("\nSố tiền phải trả: "+cost+"$\n");
                    break;
                }
            }
        }
    }
}
class CheckInCheckOut extends customersCheckFile{
    CheckFile checkFile = new CheckFile();
    public void checkInCheckOut(){
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        if(Check_FileCus()){
            System.out.println("Nhập số phòng hoặc mã khách hàng bạn muốn kiểm tra!");
            String choice = scanner.nextLine();
            int found = 0;
            for(int i = 0;i<NumberRoomCustomers.size();i++){
                if(NumberRoomCustomers.get(i).equals(choice) || IdCustomers.get(i).equals(choice)){
                    System.out.println("Bạn muốn check-in hay check out? (I/O)");
                    String choice2 = scanner.nextLine();
                    if(choice2.equals("I") || choice2.equals("i")){
                        if(CheckSttCustomers.get(i).equals("Đang ở")){
                            CheckFileRooms checkFileRooms = new CheckFileRooms();
                            checkFileRooms.updateRooms(NumberRoomCustomers.get(i),"Đang ở");
                            System.out.println("Phòng hiện đang được sử dụng");
                        }else{
                            CheckSttCustomers.set(i,"Check-in");
                            DateArrivalCustomers.set(i,LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            CheckFileRooms checkFileRooms = new CheckFileRooms();
                            checkFileRooms.updateRooms(NumberRoomCustomers.get(i),"Check-in");
                            System.out.println("Check-in thành công");
                        }
                    }else if(choice2.equals("O") || choice2.equals("o")){
                        if(CheckSttCustomers.get(i).equals("Đang ở") || CheckSttCustomers.get(i).equals("Check-in")){
                            CheckSttCustomers.set(i,"Check-out");
                            DateDepartureCustomers.set(i,LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            CheckFileRooms checkFileRooms = new CheckFileRooms();
                            checkFileRooms.updateRooms(NumberRoomCustomers.get(i),"Check-out");
                            checkFileRooms.CostMustToPay(DateArrivalCustomers.get(i),DateDepartureCustomers.get(i),NumberRoomCustomers.get(i));
                            System.out.println("Check-out thành công");
                        }else{
                            System.out.println("Phòng hiện không có khách hàng");
                        }
                    }
                    break;
                }else{
                    found++;
                }
            }
            if(found == NumberRoomCustomers.size()){
                System.out.println("Không tìm thấy khách hàng");
            }else{
                try (Writer writer = new FileWriter(file,false)) {
                    writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                    for(int i = 1;i<IdCustomers.size();i++){
                        writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class SearchChoiceCus extends customersCheckFile{
    Scanner scanner = new Scanner(System.in);
    int err = 0;
    public void searchChoiceCus(String idCus){
        if(Check_FileCus()){
            for(int i = 0;i<IdCustomers.size();i++){
                if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                    System.out.println(
                        "\nMã khách hàng: "+IdCustomers.get(i)+
                        "\n Số phòng: "+NumberRoomCustomers.get(i)+
                        "\n Họ tên: "+NameCustomers.get(i)+
                        "\n Ngày tháng năm sinh: "+DateOfBirthCustomers.get(i)+
                        "\n Số cccd: "+CccdCustomers.get(i)+
                        "\n Số điện thoại: "+PhoneCustomers.get(i)+
                        "\n Ngày đặt phòng: "+DateBookingCustomers.get(i)+
                        "\n Ngày đến: "+DateArrivalCustomers.get(i)+
                        "\n Ngày đi: "+DateDepartureCustomers.get(i)+
                        "\n Trạng thái: "+CheckSttCustomers.get(i)
                    );
                    err++;
                    break;
                }
            }
        }
        if(err == IdCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }
    }
    public void searchNameCus(String nameCus){
        if(Check_FileCus()){
            String[] nameUpper = nameCus.toUpperCase().split(" ");
            for(int i =0;i<nameUpper.length;i++){
                nameUpper[i] = nameUpper[i].substring(0,1).toUpperCase() + nameUpper[i].substring(1).toLowerCase();
            }
            nameCus = String.join(" ", nameUpper);
            // System.out.println(nameCus);
            for(int i = 1;i<NameCustomers.size();i++){
                if(NameCustomers.get(i).contains(nameCus)){
                    System.out.println(
                        "\nMã khách hàng: "+IdCustomers.get(i)+
                        "\n Số phòng: "+NumberRoomCustomers.get(i)+
                        "\n Họ tên: "+NameCustomers.get(i)+
                        "\n Ngày tháng năm sinh: "+DateOfBirthCustomers.get(i)+
                        "\n Số cccd: "+CccdCustomers.get(i)+
                        "\n Số điện thoại: "+PhoneCustomers.get(i)+
                        "\n Ngày đặt phòng: "+DateBookingCustomers.get(i)+
                        "\n Ngày đến: "+DateArrivalCustomers.get(i)+
                        "\n Ngày đi: "+DateDepartureCustomers.get(i)+
                        "\n Trạng thái: "+CheckSttCustomers.get(i)
                    );
                }else{
                    err++;
                }
            }
            if(err == NameCustomers.size()){
                System.out.println("Không tìm thấy khách hàng");
                err = 0;
            }
        }else{
            System.out.println("Không tìm thấy file");
        }
    }
    public void searchChoiceCus(int cccdCus){
        if(Check_FileCus()){
            for(int i = 0;i<CccdCustomers.size();i++){
                if(CccdCustomers.get(i).equals(String.valueOf(cccdCus))){
                    System.out.println(
                        "\nMã khách hàng: "+IdCustomers.get(i)+
                        "\n Số phòng: "+NumberRoomCustomers.get(i)+
                        "\n Họ tên: "+NameCustomers.get(i)+
                        "\n Ngày tháng năm sinh: "+DateOfBirthCustomers.get(i)+
                        "\n Số cccd: "+CccdCustomers.get(i)+
                        "\n Số điện thoại: "+PhoneCustomers.get(i)+
                        "\n Ngày đặt phòng: "+DateBookingCustomers.get(i)+
                        "\n Ngày đến: "+DateArrivalCustomers.get(i)+
                        "\n Ngày đi: "+DateDepartureCustomers.get(i)+
                        "\n Trạng thái: "+CheckSttCustomers.get(i)
                    );
                }else{
                    err++;
                }
            }
        }
        if(err == CccdCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }
    }
    public void searchNumberRoomCus(int numberRoomCus){
        if(Check_FileCus()){
            for(int i = 0;i<NumberRoomCustomers.size();i++){
                if(NumberRoomCustomers.get(i).equals(String.valueOf(numberRoomCus))){
                    System.out.println(
                        "\nMã khách hàng: "+IdCustomers.get(i)+
                        "\n Số phòng: "+NumberRoomCustomers.get(i)+
                        "\n Họ tên: "+NameCustomers.get(i)+
                        "\n Ngày tháng năm sinh: "+DateOfBirthCustomers.get(i)+
                        "\n Số cccd: "+CccdCustomers.get(i)+
                        "\n Số điện thoại: "+PhoneCustomers.get(i)+
                        "\n Ngày đặt phòng: "+DateBookingCustomers.get(i)+
                        "\n Ngày đến: "+DateArrivalCustomers.get(i)+
                        "\n Ngày đi: "+DateDepartureCustomers.get(i)+
                        "\n Trạng thái: "+CheckSttCustomers.get(i)
                    );
                }else{
                    err++;
                }
            }
        }
        if(err == NumberRoomCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }
    }
}
class SearchCustomers extends customersCheckFile{
    public void search(){
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nBạn muốn tìm theo tiêu chí nào?");
            System.out.println("0. Thoát");
            System.out.println("1. Tìm theo mã khách hàng");
            System.out.println("2. Tìm theo số phòng");
            System.out.println("3. Tìm theo họ tên");
            System.out.println("4. Tìm theo số cccd");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    System.out.println("Thoát chương trình");
                    break;
                case 1:
                    System.out.println("Nhập mã khách hàng: ");
                    String idCus = scanner.nextLine();
                    SearchChoiceCus searchChoiceCus = new SearchChoiceCus();
                    searchChoiceCus.searchChoiceCus(idCus);
                    break;
                case 2:
                    SearchChoiceCus searchChoice = new SearchChoiceCus();
                    System.out.println("Nhập số phòng: ");
                    int numberRoom = scanner.nextInt();
                    searchChoice.searchNumberRoomCus(numberRoom);
                    break;
                case 3:
                    SearchChoiceCus searchName = new SearchChoiceCus();
                    System.out.println("Nhập họ tên: ");
                    String nameCus = scanner.nextLine();
                    searchName.searchNameCus(nameCus);
                    break;
                case 4:
                    SearchChoiceCus searchCccd = new SearchChoiceCus();
                    System.out.println("Nhập số cccd: ");
                    int cccdCus = scanner.nextInt();
                    searchCccd.searchChoiceCus(cccdCus);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
                    break;
            }
        } while (choice!=0);
    }
}
class EditChoiceCus extends customersCheckFile{
    Scanner scanner = new Scanner(System.in);
    int idCus;
    int err = 0;
    public int idCusCheck(){
        if(Check_FileCus()){
            System.out.println("Nhập mã khách hàng bạn muốn sửa: ");
            idCus = scanner.nextInt();
            for(int i = 0;i<IdCustomers.size();i++){
                if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                    return i;
                }
            }
        }
        return -1;
    }
    public void editChoiceCusName(String nameString){
        for(int i = 0;i<IdCustomers.size();i++){
            if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                NameCustomers.set(i,nameString);
                break;
            }else{
                err++;
            }
        }
        if(err == IdCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }else{
            System.out.println("Sửa thông tin thành công");
            try (Writer writer = new FileWriter(file,false)) {
                writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                for(int i = 1;i<IdCustomers.size();i++){
                    writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void editChoiceCusDateOfBirth(String dateOfBirthString){
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(dateOfBirth.isAfter(LocalDate.now())){
            System.out.println("Ngày tháng năm sinh không hợp lệ");
            return;
        }else if((LocalDate.now().getYear() - dateOfBirth.getYear())<18){
            System.out.println("\nKhách hàng phải trên 18 tuổi\n");
            return;
        }else{
            for(int i = 0;i<IdCustomers.size();i++){
                if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                    DateOfBirthCustomers.set(i,dateOfBirthString);
                    break;
                }else{
                    err++;
                }
            }
            if(err == IdCustomers.size()){
                System.out.println("\nKhông tìm thấy khách hàng\n");
                err = 0;
            }else{
                System.out.println("\nSửa thông tin thành công\n");
                try (Writer writer = new FileWriter(file,false)){
                    writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                    for(int i = 1;i<IdCustomers.size();i++){
                        writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void editCccdCus(String cccdString){
        for(int i = 0;i<CccdCustomers.size();i++){
            if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                CccdCustomers.set(i,cccdString);
                break;
            }else{
                err++;
            }
        }
        if(err == CccdCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }else{
            System.out.println("Sửa thông tin thành công");
            try (Writer writer = new FileWriter(file,false)){
                writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                for(int i = 1;i<IdCustomers.size();i++){
                    writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void editPhoneCus(String phoneString){
        if (phoneString.startsWith("+84")) { // regex phone number Viet Nam
            phoneString = "0" + phoneString.substring(3);
        } else if (phoneString.startsWith("84")) {
            phoneString = "0" + phoneString.substring(2);
        }
        if (phoneString.matches("0[0-9]{9}")) { // regex phone number Viet Nam
            String formattedPhone = phoneString.replaceFirst("(\\d{4})(\\d{3})(\\d{3})", "$1 $2 $3");
            PhoneCustomers.add(formattedPhone);
        } else {
            System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập 10 chữ số, bắt đầu bằng số 0.");
            return;
        }
        for(int i = 0;i<PhoneCustomers.size();i++){
            if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                PhoneCustomers.set(i,phoneString);
                break;
            }else{
                err++;
            }
        }
        if(err == PhoneCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }else{
            System.out.println("Sửa thông tin thành công");
            try (Writer writer = new FileWriter(file,false)){
                writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                for(int i = 1;i<IdCustomers.size();i++){
                    writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void editDateBookingCus(String dateBookingString){
        LocalDate dateBooking = LocalDate.parse(dateBookingString,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if(dateBooking.isAfter(LocalDate.now())){
            System.out.println("Ngày đặt phòng không hợp lệ");
            return;
        }else{
            for(int i = 0;i<DateBookingCustomers.size();i++){
                if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                    DateBookingCustomers.set(i,dateBookingString);
                    break;
                }else{
                    err++;
                }
            }
            if(err == DateBookingCustomers.size()){
                System.out.println("Không tìm thấy khách hàng");
                err = 0;
            }else{
                System.out.println("Sửa thông tin thành công");
                try (Writer writer = new FileWriter(file,false)){
                    writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                    for(int i = 1;i<IdCustomers.size();i++){
                        writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void editDateArrivalCus(String dateArrivalString){
        LocalDate dateArrival = LocalDate.parse(dateArrivalString,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dateBooking;
        for(int i = 0;i<DateBookingCustomers.size();i++){
            if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                dateBooking = LocalDate.parse(DateBookingCustomers.get(i),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(dateArrival.isBefore(dateBooking)){
                    System.out.println("\nNgày đến không hợp lệ\n");
                    return;
                }else{
                    if(dateArrival.isEqual(LocalDate.now())){
                        CheckSttCustomers.set(i,"Check-in");
                    }else if(dateArrival.isAfter(dateBooking) && dateArrival.isBefore(LocalDate.now())){
                        CheckSttCustomers.set(i,"Đang ở");
                    }
                    DateArrivalCustomers.set(i,dateArrivalString);
                }
            }else{
                err++;
            }
        }
        if(err == DateArrivalCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }else{
            System.out.println("Sửa thông tin thành công");
            try (Writer writer = new FileWriter(file,false)){
                writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                for(int i = 1;i<IdCustomers.size();i++){
                    writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void editDateDepartureCus(String dateDepartureString){
        LocalDate dateDeparture = LocalDate.parse(dateDepartureString,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dateArrival;
        LocalDate dateBooking;
        for(int i = 0;i<DateArrivalCustomers.size();i++){
            if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                dateArrival = LocalDate.parse(DateArrivalCustomers.get(i),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                dateBooking = LocalDate.parse(DateBookingCustomers.get(i),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if(dateDeparture.isBefore(dateArrival)){
                    System.out.println("\nNgày đi không hợp lệ\n");
                    return;
                }else if(dateDeparture.isBefore(dateBooking)){
                    System.out.println("\nNgày đi không hợp lệ\n");
                    return;
                }else{
                    DateDepartureCustomers.set(i,dateDepartureString);
                }
            }else{
                err++;
            }
        }
        if(err == DateDepartureCustomers.size()){
            System.out.println("Không tìm thấy khách hàng");
            err = 0;
        }else{
            System.out.println("Sửa thông tin thành công");
            try (Writer writer = new FileWriter(file,false)){
                writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                for(int i = 1;i<IdCustomers.size();i++){
                    writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class EditCustomers extends customersCheckFile{
    Scanner scanner = new Scanner(System.in);
    public void edit() {
        if(Check_FileCus()){
            EditChoiceCus editChoiceCus = new EditChoiceCus();
            int idCus = editChoiceCus.idCusCheck();
            if(idCus!=-1){
                int choice;
                do {
                    System.out.println("\nBạn muốn sửa thông tin gì?");
                    System.out.println("0. Thoát");
                    System.out.println("1. Họ tên");
                    System.out.println("2. Ngày sinh");
                    System.out.println("3. Số cccd");
                    System.out.println("4. Số điện thoại");
                    System.out.println("5. Ngày đặt phòng");
                    System.out.println("6. Ngày đến");
                    System.out.println("7. Ngày đi");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 0:
                            System.out.println("Thoát chương trình");
                            break;
                        case 1:
                            System.out.println("Nhập họ tên mới: ");
                            String newNameCus = scanner.nextLine();
                            editChoiceCus.editChoiceCusName(newNameCus);
                            break;
                        case 2:
                            System.out.println("Nhập ngày sinh mới: ");
                            String newDateOfBirthCus = scanner.nextLine();
                            editChoiceCus.editChoiceCusDateOfBirth(newDateOfBirthCus);
                            break;
                        case 3:
                            System.out.println("Nhập số cccd mới: ");
                            String newCccdCus = scanner.nextLine();
                            editChoiceCus.editCccdCus(newCccdCus);
                            break;
                        case 4:
                            System.out.println("Nhập số điện thoại mới: ");
                            int newPhoneCus = scanner.nextInt();
                            scanner.nextLine();
                            editChoiceCus.editPhoneCus(String.valueOf(newPhoneCus));
                            break;
                        case 5:
                            System.out.println("Nhập ngày đặt phòng mới: ");
                            String newDateBookingCus = scanner.nextLine();
                            editChoiceCus.editDateBookingCus(newDateBookingCus);
                            break;
                        case 6:
                            System.out.println("Nhập ngày đến mới: ");
                            String newDateArrivalCus = scanner.nextLine();
                            editChoiceCus.editDateArrivalCus(newDateArrivalCus);
                            break;
                        case 7:
                            System.out.println("Nhập ngày đi mới: ");
                            String newDateDepartureCus = scanner.nextLine();
                            editChoiceCus.editDateDepartureCus(newDateDepartureCus);
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
                            break;
                    }
                } while (choice!=0);
            }else{
                System.out.println("Không tìm thấy khách hàng");
            }
        }
    }

}
class DeleteCustomers extends customersCheckFile{
    Scanner scanner = new Scanner(System.in);
    boolean err = false;
    public void deleteCus(){
        if(Check_FileCus()){
            System.out.println("Nhập mã khách hàng bạn muốn xóa:");
            int idCus = scanner.nextInt();
            scanner.nextLine();
            // System.out.println(idCus);
            for(int i = 0;i<IdCustomers.size();i++){
                if(IdCustomers.get(i).equals(String.valueOf(idCus))){
                    IdCustomers.remove(i);
                    NumberRoomCustomers.remove(i);
                    NameCustomers.remove(i);
                    DateOfBirthCustomers.remove(i);
                    CccdCustomers.remove(i);
                    PhoneCustomers.remove(i);
                    DateBookingCustomers.remove(i);
                    DateArrivalCustomers.remove(i);
                    DateDepartureCustomers.remove(i);
                    CheckSttCustomers.remove(i);
                    err = true;
                    break;
                }else{
                    err = false;
                }
            }
            // System.out.println(err);
            if(err == false){
                System.out.println("Không tìm thấy khách hàng");
                err = false;
            }else{
                System.out.println("Xóa thông tin thành công");
                try (Writer writer = new FileWriter(file,false)){
                    writer.write(IdCustomers.get(0)+","+NumberRoomCustomers.get(0)+","+NameCustomers.get(0)+","+DateOfBirthCustomers.get(0)+","+CccdCustomers.get(0)+","+PhoneCustomers.get(0)+","+DateBookingCustomers.get(0)+","+DateArrivalCustomers.get(0)+","+DateDepartureCustomers.get(0)+","+CheckSttCustomers.get(0));
                    for(int i = 1;i<IdCustomers.size();i++){
                        writer.write("\n"+IdCustomers.get(i)+","+NumberRoomCustomers.get(i)+","+NameCustomers.get(i)+","+DateOfBirthCustomers.get(i)+","+CccdCustomers.get(i)+","+PhoneCustomers.get(i)+","+DateBookingCustomers.get(i)+","+DateArrivalCustomers.get(i)+","+DateDepartureCustomers.get(i)+","+CheckSttCustomers.get(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class RateService extends EmployeesCheckFile{
    Scanner scanner = new Scanner(System.in);
    public void rate(){
        System.out.println("Nhập mã nhân viên bạn muốn đánh giá: ");
        int idEmp = scanner.nextInt();
        int rate=0;
        int err = 0;
        scanner.nextLine();
        if(Check_File()){
            for(int i = 0;i<Idemployees.size();i++){
                if(Idemployees.get(i).equals(String.valueOf(idEmp))){
                    System.out.println("Trên thang điểm 1 đến 10 bạn đánh giá nhân viên "+ NameEmployees.get(i)+" bao nhiêu?");
                    rate = scanner.nextInt();
                    scanner.nextLine();
                    EvaluationEmployees.set(i,String.valueOf(rate));
                    break;
                }else{
                    err++;
                }
            }
            if(err == Idemployees.size()){
                System.out.println("Không tìm thấy nhân viên");
                err = 0;
            }else{
                System.out.println("Đánh giá thành công");
                try (Writer writer = new FileWriter(file,false)){
                    writer.write(Idemployees.get(0)+","+NameEmployees.get(0)+","+PositionEmployees.get(0)+","+SalaryEmployees.get(0)+","+DateJoinEmployees.get(0)+","+DateLeaveEmployees.get(0)+","+EvaluationEmployees.get(0));
                    for(int i = 1;i<Idemployees.size();i++){
                        writer.write("\n"+Idemployees.get(i)+","+NameEmployees.get(i)+","+PositionEmployees.get(i)+","+SalaryEmployees.get(i)+","+DateJoinEmployees.get(i)+","+DateLeaveEmployees.get(i)+","+EvaluationEmployees.get(i));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
public class customers {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("----------------------------------");
            System.out.println("Chương trình quản lý khách hàng");
            System.out.println("Vui lòng chọn chương trình");
            System.out.println("0. Thoát");
            System.out.println("1. Thêm thông tin khách hàng mới");
            System.out.println("2. Hiển thị thông tin khách hàng");
            System.out.println("3. Xác nhận Check-in/Check-out");
            System.out.println("4. Tìm kiếm thông tin khách hàng");
            System.out.println("5. Sửa thông tin khách hàng");
            System.out.println("6. Xóa thông tin khách hàng");
            System.out.println("7. Đánh giá chất lượng phục vụ");
            System.out.println("----------------------------------");
            choice = scanner.nextInt();
            switch (choice) {
            case 0:
                System.out.println("Thoát chương trình");
                break;
            case 1:
                AddCustomers addCustomers = new AddCustomers();
                addCustomers.add();
                break;
            case 2:
                DisplayCustomers displayCustomers = new DisplayCustomers();
                displayCustomers.display();
                break;
            case 3:
                CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
                checkInCheckOut.checkInCheckOut();
                break;
            case 4:
                SearchCustomers searchCustomers = new SearchCustomers();
                searchCustomers.search();
                break;
            case 5:
                EditCustomers editCustomers = new EditCustomers();
                editCustomers.edit();
                break;
            case 6:
                DeleteCustomers deleteCustomers = new DeleteCustomers();
                deleteCustomers.deleteCus();
                break;
            case 7:
                RateService rateService = new RateService();
                rateService.rate();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại");
                break;
        }
        } while (choice!=0);
    }

}