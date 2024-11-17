package hotel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
/*
 *
 * - class Nhân Viên:
+ Họ tên
+ Mã nhân viên
+ Chức vụ nhân viên
+ Lương
+ Ngày vào làm
+ Ngày nghỉ làm
+ Đánh giá từ khách hàng
 */
class Employees {  // Đổi tên class 'employees' thành 'Employees'
   protected ArrayList<String> Idemployees = new ArrayList<>();
   public ArrayList<String> NameEmployees = new ArrayList<>();
   public ArrayList<String> PositionEmployees = new ArrayList<>();
   protected ArrayList<String> SalaryEmployees = new ArrayList<>();
   protected ArrayList<String> DateJoinEmployees = new ArrayList<>();
   protected ArrayList<String> DateLeaveEmployees = new ArrayList<>();
   public ArrayList<String> EvaluationEmployees = new ArrayList<>();
}

class EmployeesCheckFile extends Employees {
    String file = "Employees.csv";
    String line = "";
    String delimiter = ",";
    public boolean Check_File() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // BufferedReader dùng để đọc từng dòng trong file CSV
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                Idemployees.add(values[0]);
                NameEmployees.add(values[1]);
                PositionEmployees.add(values[2]);
                SalaryEmployees.add(values[3]);
                DateJoinEmployees.add(values[4]);
                DateLeaveEmployees.add(values[5]);
                EvaluationEmployees.add(values[6]);
            }
            return true;
        } catch (IOException e) {
            System.out.println("Lỗi tìm kiếm file! : " + e.getMessage());
            return false;
        }
    }
}
class AddEmployees extends EmployeesCheckFile{
    Scanner scn = new Scanner(System.in);
    boolean checkErr= false;
    public void add(){
        System.out.println("Nhập số lượng nhân viên muốn thêm");
        int numberepls = scn.nextInt();
        if(Check_File()){
            for(int i=0;i<numberepls;i++){
                System.out.println("Nhập thông tin của nhân viên thứ: "+(i+1));
                System.out.println("Nhập mã nhân viên (số nguyên): ");
                int id = scn.nextInt();
                scn.nextLine();
                for(int j=0;j<Idemployees.size();j++){
                    if(Idemployees.get(j).equals(String.valueOf(id))){
                        System.out.println("Mã nhân viên đã bị trùng lặp với nhân viên khác!");
                        checkErr = false;
                        return;
                    }
                }
                Idemployees.add(String.valueOf(id));
                System.out.println("Nhập họ tên nhân viên: ");
                NameEmployees.add(scn.nextLine());
                System.out.println("Nhập chức vụ nhân viên: ");
                PositionEmployees.add(scn.nextLine());
                System.out.println("Nhập lương nhân viên: ");
                SalaryEmployees.add(String.valueOf(scn.nextDouble()) + "$");
                scn.nextLine();
                System.out.println("Nhập ngày vào làm nhân viên (dd/MM/yyyy): ");
                String dateJoin = scn.nextLine();
                DateJoinEmployees.add(dateJoin);
                System.out.println("Nhập ngày nghỉ làm nhân viên: ");
                String dateLeave = scn.nextLine();
                System.out.println(dateLeave);
                if(LocalDate.parse(dateJoin,DateTimeFormatter.ofPattern("dd/MM/yyyy")).isAfter(LocalDate.now())){
                    System.out.println("Ngày vào làm không hợp lệ");
                    checkErr = false;
                    return;
                }
                if (!dateLeave.isEmpty()) {
                    if(
                        LocalDate.parse(dateLeave,DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.parse(dateJoin,DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    ){
                        System.out.println("Ngày nghỉ làm không hợp lệ");
                        checkErr = false;
                        return;
                    }else if(LocalDate.parse(dateLeave,DateTimeFormatter.ofPattern("dd/MM/yyyy")).isAfter(LocalDate.now())){
                        System.out.println("Ngày nghỉ làm không hợp lệ");
                        checkErr = false;
                        return;
                    }else{
                        DateLeaveEmployees.add(dateLeave);
                    }
                }
                if(dateLeave == ""){
                    DateLeaveEmployees.add("Chưa nghỉ");
                }
                EvaluationEmployees.add("Chưa đánh giá!");
            }
            checkErr = true;
            // for(int i=0;i<Idemployees.size();i++){
            //     System.out.println(
            //         Idemployees.get(i)+","+NameEmployees.get(i)+","+PositionEmployees.get(i)+","+SalaryEmployees.get(i)+","+DateJoinEmployees.get(i)+","+DateLeaveEmployees.get(i)
            //     );
            // }

        }
    }
    public void writer(){
        if(checkErr){
            try(Writer wt = new FileWriter(file, false)){
                wt.write(Idemployees.get(0)+","+NameEmployees.get(0)+","+PositionEmployees.get(0)+","+SalaryEmployees.get(0)+","+DateJoinEmployees.get(0)+","+DateLeaveEmployees.get(0)+","+EvaluationEmployees.get(0));
                for(int i=1;i<Idemployees.size();i++){
                    wt.write("\n"+Idemployees.get(i)+","+NameEmployees.get(i)+","+PositionEmployees.get(i)+","+SalaryEmployees.get(i)+","+DateJoinEmployees.get(i)+","+DateLeaveEmployees.get(i)+","+EvaluationEmployees.get(i));
                }
            }catch(IOException e){
                System.out.println("Lỗi ghi file: "+e.getMessage());
            }
        }else{
            System.out.println("Không thể ghi dữ liệu");
        }
    }
}

class EmployeesDisplay extends KhachSan {
    EmployeesCheckFile epls = new EmployeesCheckFile();
    @SuppressWarnings("resource")
    public void display() {
        Scanner scanner = new Scanner(System.in);
        AddEmployees addEmployees = new AddEmployees();
        if (epls.Check_File()) {
            if (epls.Idemployees.size()==1) {
                System.out.println("Hiện dữ liệu chưa có, bạn muốn thêm dữ liệu không? (Y/N)");
                String create = scanner.next();
                create = create.toLowerCase();
                if(create.equalsIgnoreCase("y")){
                    addEmployees.add();
                    // addEmployees.writer();
                    // System.out.println(addEmployees.Idemployees.size());
                }else{
                    System.out.println("Không có dữ liệu để đọc");
                    return;
                }
            }else{
                System.out.println("Danh sách nhân viên:");
                for(int i=1;i<epls.Idemployees.size();i++){
                    System.out.println("Mã nhân viên: "+epls.Idemployees.get(i));
                    System.out.println("Họ tên nhân viên: "+epls.NameEmployees.get(i));
                    System.out.println("Chức vụ nhân viên: "+epls.PositionEmployees.get(i));
                    System.out.println("Lương nhân viên: "+epls.SalaryEmployees.get(i));
                    System.out.println("Ngày vào làm: "+epls.DateJoinEmployees.get(i));
                    System.out.println("Ngày nghỉ làm: "+epls.DateLeaveEmployees.get(i));
                    System.out.println("Đánh giá: "+epls.EvaluationEmployees.get(i));
                    System.out.println("\n");
                }
            }
        }else{
            System.out.println("Không tìm thấy file");
        }
    }
}

class EditEmployees extends EmployeesCheckFile {
    public void edit(){
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập mã nhân viên cần sửa: ");
        String id = scanner.nextLine();
        boolean found = false;
        if(Check_File()){
            for(int i=0;i<Idemployees.size();i++){
                // System.out.println(Idemployees.get(i));
                if(Idemployees.get(i).equals(id)){
                    System.out.println("Nhập họ tên mới: ");
                    NameEmployees.set(i, scanner.nextLine());
                    System.out.println("Nhập chức vụ mới: ");
                    PositionEmployees.set(i, scanner.nextLine());
                    System.out.println("Nhập lương mới: ");
                    SalaryEmployees.set(i, String.valueOf(scanner.nextDouble()));
                    scanner.nextLine();
                    System.out.println("Nhập ngày vào làm mới (dd/MM/yyyy): ");
                    DateJoinEmployees.set(i, scanner.nextLine());
                    System.out.println("Nhập ngày nghỉ làm mới (Nếu có) (dd/MM/yyyy): ");
                    String dateInput = scanner.nextLine();
                    if(dateInput.isEmpty()){
                        DateLeaveEmployees.set(i, "Chưa nghỉ");
                    }else{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Định dạng ngày tháng năm
                        LocalDate date = LocalDate.parse(dateInput, formatter);
                        DateLeaveEmployees.set(i, date.toString());
                    }
                    found = true;
                    break;
                }else{
                    found = false;
                }
            }
            if(!found){
                System.out.println("Không tìm thấy nhân viên cần sửa");
            }else{
                System.out.println("Đã sửa thành công");
                try(Writer wt = new FileWriter(file, false)) {
                    wt.write(Idemployees.get(0) + "," + NameEmployees.get(0) + "," + PositionEmployees.get(0) + "," + SalaryEmployees.get(0) + "," + DateJoinEmployees.get(0) + "," + DateLeaveEmployees.get(0)+","+EvaluationEmployees.get(0));
                    for(int i =1;i< Idemployees.size();i++){
                        wt.write("\n"+Idemployees.get(i) + "," + NameEmployees.get(i) + "," + PositionEmployees.get(i) + "," + SalaryEmployees.get(i) + "," + DateJoinEmployees.get(i) + "," + DateLeaveEmployees.get(i)+","+EvaluationEmployees.get(i));
                    }
                } catch (Exception e) {
                    System.out.println("Lỗi ghi file: " + e.getMessage());
                }
            }
        }else{
            System.out.println("Không tìm thấy file");
        }
    }
}

class DeleteEmployees extends EmployeesCheckFile {
    @SuppressWarnings("resource")
    public void delete(){
        Scanner scanner = new Scanner(System.in);
        boolean found = false;
        if(Check_File()){
            if(Idemployees.size() == 1){
                System.out.println("Chưa có dữ liệu, bạn muốn thêm dữ liệu vào không? (Y/N)");
                String createEmployees = scanner.next();
                createEmployees = createEmployees.toLowerCase();
                if(createEmployees.equals("y")){
                    AddEmployees add = new AddEmployees();
                    add.add();
                }else{
                    System.out.println("\nKhông có dữ liệu!\n");
                    return;
                }
            }
            System.out.println("Nhập mã nhân viên cần xoá: ");
            String id = scanner.nextLine();
            for(int i=0;i<Idemployees.size();i++){
                if(Idemployees.get(i).equals(id)){
                    Idemployees.remove(i);
                    NameEmployees.remove(i);
                    PositionEmployees.remove(i);
                    SalaryEmployees.remove(i);
                    DateJoinEmployees.remove(i);
                    DateLeaveEmployees.remove(i);
                    found = true;
                    System.out.println("Đã xoá thành công");
                    break;
                }else{
                    found = false;
                }
            }
            if(!found){
                System.out.println("Không tìm thấy nhân viên");
            }else{
                try(Writer wt = new FileWriter(file, false)) {
                    wt.write(Idemployees.get(0)+","+NameEmployees.get(0)+","+PositionEmployees.get(0)+","+SalaryEmployees.get(0)+","+DateJoinEmployees.get(0)+","+DateLeaveEmployees.get(0)+","+EvaluationEmployees.get(0));
                    for(int i=1;i<Idemployees.size();i++){
                        wt.write("\n"+Idemployees.get(i)+","+NameEmployees.get(i)+","+PositionEmployees.get(i)+","+SalaryEmployees.get(i)+","+DateJoinEmployees.get(i)+","+DateLeaveEmployees.get(i)+","+EvaluationEmployees.get(i));
                    }
                } catch (Exception e) {
                    System.out.println("Lỗi"+ e.getMessage());
                }
            }
        }
    }
}

class SearchEmployees extends EmployeesCheckFile{
    Scanner scanner = new Scanner(System.in);
    public void SearchID(){
        String Id;
        int err=1;
        if(Check_File()){
            System.out.print("Nhập mã nhân viên bạn muốn tìm: ");
            Id = scanner.next();
            for(int i=1;i<Idemployees.size();i++){
                if(Idemployees.get(i).equals(Id)){
                    // System.out.println(true);
                    System.out.println(
                        "\nMã nhân viên: "+Idemployees.get(i)+ "\n"+
                        "Họ tên nhân viên: "+NameEmployees.get(i)+"\n"+
                        "Vị trí nhân viên: "+PositionEmployees.get(i)+"\n"+
                        "Lương nhân viên: "+SalaryEmployees.get(i)+"\n"+
                        "Ngày vào làm: "+DateJoinEmployees.get(i)+"\n"+
                        "Ngày nghỉ làm: "+DateLeaveEmployees.get(i)+"\n"
                    );
                    break;
                }else{
                    // System.out.println(false);
                    err++;
                }
            }
            if(err == Idemployees.size()){
                System.out.println("\nKhông có nhân viên nào khớp với ID\n");
                err = 0;
            }
        }else{
            System.out.println("Không tìm thấy file dữ liệu");
        }
    }

    public void SearchNames(){
        int err=1;
        if(Check_File()){
            System.out.println("Nhập tên nhân viên cần tìm: ");
            String NameKey = scanner.nextLine();  // Đọc toàn bộ chuỗi nhập vào, bao gồm cả dấu cách
            String[] ToUpper = NameKey.split(" ");  // Tách chuỗi theo dấu cách
            for(int i=0;i<ToUpper.length;i++){
                ToUpper[i] = ToUpper[i].substring(0, 1).toUpperCase() + ToUpper[i].substring(1).toLowerCase(); 
                // Chuyển đổi chữ cái đầu thành chữ hoa và các chữ cái còn lại thành chữ thường
            }
            NameKey = String.join(" ", ToUpper);  // Nối các phần tử trong mảng thành 1 chuỗi và cách nhau bằng delimiter
            // System.out.println(NameKey);
            for(int i=1;i<NameEmployees.size();i++){
                if(NameEmployees.get(i).contains(NameKey)){
                    System.out.println(
                        "\nMã nhân viên: "+Idemployees.get(i)+ "\n"+
                        "Họ tên nhân viên: "+NameEmployees.get(i)+"\n"+
                        "Vị trí nhân viên: "+PositionEmployees.get(i)+"\n"+
                        "Lương nhân viên: "+SalaryEmployees.get(i)+"\n"+
                        "Ngày vào làm: "+DateJoinEmployees.get(i)+"\n"+
                        "Ngày nghỉ làm: "+DateLeaveEmployees.get(i)+"\n"
                    );
                }else{
                    err++;
                }
            }
            if(err == NameEmployees.size()){
                System.out.println("\nKhông có nhân viên nào khớp với tên hoặc kí tự\n");
                err = 1;
            }
        }else{
            System.out.println("Không tìm thấy file dữ liệu");
        }
    }
    public void SearchPosition(){
        if(Check_File()){
            int err=1;
            System.out.println("Nhập vị trí nhân viên cần tìm: ");
            String PositionKey = scanner.next();
            PositionKey = PositionKey.toLowerCase();
            scanner.nextLine();
            for(int i=1;i<PositionEmployees.size();i++){
                if(PositionEmployees.get(i).contains(PositionKey)){
                    System.out.println(
                        "\nMã nhân viên: "+Idemployees.get(i)+ "\n"+
                        "Họ tên nhân viên: "+NameEmployees.get(i)+"\n"+
                        "Vị trí nhân viên: "+PositionEmployees.get(i)+"\n"+
                        "Lương nhân viên: "+SalaryEmployees.get(i)+"\n"+
                        "Ngày vào làm: "+DateJoinEmployees.get(i)+"\n"+
                        "Ngày nghỉ làm: "+DateLeaveEmployees.get(i)+"\n"
                    );
                }else{
                    err++;
                }
            }
            if(err == PositionEmployees.size()){
                System.out.println("\nKhông có nhân viên nào khớp với vị trí\n");
                err = 1;
            }
        }else{
            System.out.println("Không tìm thấy file dữ liệu");
        }
    }
    public void Search() {
        int n;
        do {
            System.out.println("Bạn muốn tìm kiếm theo tiêu chí nào?");
            System.out.println("0. Thoát chương trình");
            System.out.println("1. Mã nhân viên");
            System.out.println("2. Tên nhân viên");
            System.out.println("3. Vị trí làm việc");
            System.out.println("Vui lòng chọn đúng chương trình");
            n = scanner.nextInt();
            scanner.nextLine();
            switch(n){
                case 0:
                System.out.println("Thoát chương trình");
                break;
                case 1:
                SearchID();
                break;
                case 2:
                SearchNames();
                break;
                case 3:
                SearchPosition();
                break;
                default:
                System.out.println("Không có chức năng này");
                break;
            }
        } while (n!=0);

    }
}

class SortEmployees extends EmployeesCheckFile {
    public void sort(){
        if(Check_File()){
            for(int i=1;i<EvaluationEmployees.size();i++){
                if(EvaluationEmployees.get(i).equals("Chưa đánh giá!")){
                    EvaluationEmployees.set(i, "0");
                }
            }
            for(int i=1;i<EvaluationEmployees.size();i++){
                for(int j=i+1;j<EvaluationEmployees.size();j++){
                    if(Integer.parseInt(EvaluationEmployees.get(i))<Integer.parseInt(EvaluationEmployees.get(j))){
                        String temp = EvaluationEmployees.get(i);
                        EvaluationEmployees.set(i, EvaluationEmployees.get(j));
                        EvaluationEmployees.set(j, temp);
                    }
                }
            }
            System.out.println("Chất lượng phục vụ từ cao đến thấp: ");
            for(int i=1;i<EvaluationEmployees.size();i++){
                if(EvaluationEmployees.get(i).equals("0")){
                    EvaluationEmployees.set(i, "Chưa đánh giá!");
                }
                System.out.println("Chất lượng phục vụ của nhân viên " +NameEmployees.get(i)+" là: "+String.valueOf(EvaluationEmployees.get(i)));
            }
        }
    }
}
public class eployees extends Rooms {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int n;
        do {
                System.out.println("<---------------------------->");
                System.out.println("Quản lý nhân viên!");
                System.out.println("Vui lòng chọn chức năng");
                System.out.println("0. Thoát chương trình");
                System.out.println("1. Thêm nhân viên");
                System.out.println("2. Chỉnh sửa thông tin của nhân viên");
                System.out.println("3. Xoá nhân viên");
                System.out.println("4. Hiển thị danh sách nhân viên");
                System.out.println("5. Tìm kiếm nhân viên");
                System.out.println("6. Hiển thị nhân viên có chất lượng phục vụ từ cao đến thấp");
                n = scanner.nextInt();
                switch(n){
                    case 0:
                    System.out.println("Thoát chương trình!");
                    break;
                    case 1:
                    AddEmployees addEmployees = new AddEmployees();
                    addEmployees.add();
                    addEmployees.writer();
                    break;
                    case 2:
                    EditEmployees editEmployees = new EditEmployees();
                    editEmployees.edit();
                    break;
                    case 3:
                    DeleteEmployees deleteEmployees = new DeleteEmployees();
                    deleteEmployees.delete();
                    break;
                    case 4:
                    EmployeesDisplay display = new EmployeesDisplay();
                    display.display();
                    break;
                    case 5:
                    SearchEmployees search = new SearchEmployees();
                    search.Search();
                    break;
                    case 6:
                    SortEmployees sort = new SortEmployees();
                    sort.sort();
                    break;
                }
        } while (n!=0);
    }
}