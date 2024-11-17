package hotel;
import java.io.BufferedReader; // Các thư viện cần có để làm việc với file
import java.io.FileReader; // Thư viện này để đọc
import java.io.FileWriter;
import java.io.IOException; // In ra các thông báo lỗi
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
abstract class KhachSan {
 abstract void display();
}

class CheckFile{
    public ArrayList<String> NumberRoom = new ArrayList<>();
    public ArrayList<String> TypeRoom = new ArrayList<>(); // loại phòng: VIP, Deluxe, Standard
    public ArrayList<String> CostRoom = new ArrayList<>();
    public ArrayList<String> Stt = new ArrayList<>(); // trạng thái phòng

    public boolean Check_File(){
        String file = "Room.csv"; // Biến lưu trữ đường dẫn đến file
        String line ="";
        String delimiter =",";
        try(BufferedReader br = new BufferedReader( new FileReader(file))){
            // BufferedReader Dùng cái này để đọc từng dòng
            while((line = br.readLine()) !=null){
                NumberRoom.add(line.split(delimiter)[0]);
                TypeRoom.add(line.split(delimiter)[1]);
                CostRoom.add(line.split(delimiter)[2]);
                Stt.add(line.split(delimiter)[3]);
            }
            return true;
        }
        catch(IOException e){
            System.out.println("Lỗi tìm kiếm file! : " +e.getMessage());
            return false;
            // e.printStackTrace();
        }
    };
}

class Room extends CheckFile {
    Scanner scanner = new Scanner(System.in);
    String linkRoom = "Room.csv";
    public void addRooms(){
        if(Check_File()== true){
            System.out.print("Nhap so luong phong ban muon them: ");
            int num = scanner.nextInt();
            for(int i=0;i<num;i++){
                System.out.print("Nhập số phòng: ");

                int numberNewRooms = scanner.nextInt();
                for(int j=0;j<NumberRoom.size();j++){
                    if(NumberRoom.get(j).equals(String.valueOf(numberNewRooms))){
                        System.out.println("Số phòng đã tồn tại!");
                        break;
                    }
                }
                NumberRoom.set(i,String.valueOf (numberNewRooms));

                System.out.print("Nhập loại phòng (VIP/Deluxe/Standard): ");
                String scn = scanner.next();
                scn = scn.toLowerCase();
                if(scn.equals("vip") || scn.equals("deluxe") || scn.equals("standart")){
                    TypeRoom.add(scn);
                }else{
                    System.out.println("Vui lòng nhập đúng dạng VIP/Deluxe/Standard");
                    break;
                }
                System.out.print("Nhập giá phòng: ");
                CostRoom.add(String.valueOf(scanner.nextDouble()));
                System.out.print("Nhập trạng thái phòng mới (Trống/check-in/check-out): ");
                String ScnSTT = scanner.next();
                ScnSTT = ScnSTT.toLowerCase();
                if(ScnSTT.equals("trống") || ScnSTT.equals("check-in") || ScnSTT.equals("check-out")){
                    TypeRoom.add(ScnSTT);
                }else{
                    System.out.println("Vui lòng nhập đúng đúng dạng Trống/check-in/check-out");
                    break;
                }
            }
            try (Writer writer = new FileWriter(linkRoom, true)) {
                for (int i = NumberRoom.size() - num; i < NumberRoom.size(); i++) {
                    writer.write("\n"+NumberRoom.get(i) + "," + TypeRoom.get(i) + "," + CostRoom.get(i) + "$," + Stt.get(i) + "\n");
                }
            }catch(IOException e){
                System.out.println("Ghi file thất bại! : " + e.getMessage());
            }
        }else{
            System.out.println("Không tìm thấy file");
        }
    }
    public void display(){
        if(Check_File()==true){
            if(NumberRoom.size()==1){
                System.out.println("Hiện chưa có phòng nào trong tệp, Ban có muốn thêm phòng mới vào tệp không?(Y/N)");
                String create = scanner.next();
                create = create.toLowerCase();
                // Conditions to add new rooms
                if(create.equalsIgnoreCase("y")){
                    addRooms();
                }else if(create.equalsIgnoreCase("n")){
                    System.out.println("Không có dữ liệu phòng để đọc");
                    return;
                }else{
                    System.out.println("Vui lòng nhập đúng định dạng Y/N");
                    return;
                }
            }
                System.out.println("Danh sách phòng:");
                for(int i=1; i<NumberRoom.size(); i++){
                System.out.println("Số phòng: " + NumberRoom.get(i) + "\t Loại phòng: " + TypeRoom.get(i) + "\t Giá phòng: " + CostRoom.get(i) + "\t Trạng thái phòng: " + Stt.get(i));
            }
        }
    }
    public void updateRoom(){
        if(Check_File()== true){
            System.out.print("Nhập số phòng cần sửa thông tin: ");
            String roomNum = scanner.next();
            if(NumberRoom.size()>1){
                for(int i=1;i<NumberRoom.size();i++){
                    if(NumberRoom.get(i).equals(roomNum)){
                        System.out.print("Nhập số phòng mới: ");
                        NumberRoom.set(i,String.valueOf (scanner.nextInt()));
                        System.out.print("Nhập loại phòng mới (VIP/Deluxe/Standard): ");
                        String scn = scanner.next();
                        scn = scn.toLowerCase();
                        if(scn.equals("vip") || scn.equals("deluxe") || scn.equals("standart")){
                            TypeRoom.add(scn);
                        }else{
                            System.out.println("Vui lòng nhập đúng dạng VIP/Deluxe/Standard");
                            break;
                        }
                        System.out.print("Nhập giá phòng mới: ");
                        CostRoom.set(i,scanner.next());
                        System.out.print("Nhập trạng thái phòng mới (Trống/check-in/check-out): ");
                        String ScnSTT = scanner.next();
                        ScnSTT = ScnSTT.toLowerCase();
                        if(ScnSTT.equals("trống") || ScnSTT.equals("check-in") || ScnSTT.equals("check-out")){
                            TypeRoom.add(ScnSTT);
                        }else{
                            System.out.println("Vui lòng nhập đúng đúng dạng Trống/check-in/check-out");
                            break;
                        }
                            break;
                    }else{
                        System.out.println(false);
                    }
                }
                try(Writer writer = new FileWriter(linkRoom, false)) {
                    writer.write("\n"+NumberRoom.get(0) + "," + TypeRoom.get(0) + "," + CostRoom.get(0) + "$," + Stt.get(0) + "\n");
                    for (int i = 1; i < NumberRoom.size(); i++) {
                        writer.write(NumberRoom.get(i) + "," + TypeRoom.get(i) + "," + CostRoom.get(i) + "$," + Stt.get(i) + "\n");
                    }
                } catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                }
            }else{
                System.out.println("Không có phòng nào để sửa!");
            }
        }else{
            System.out.println("Không tìm thấy tệp dữ liệu phòng!");
        }
    }
    public void DeleteRoom(){
        if(Check_File()==true){
            System.out.println("Nhập số phòng bạn cần xoá!");
            int n = scanner.nextInt();
            if(NumberRoom.size()>1){
                int errRoom =0;
                for(int i=0;i<NumberRoom.size();i++){
                    if(NumberRoom.get(i).equals(String.valueOf(n))){
                        // System.out.println(true);
                        NumberRoom.remove(i);
                        TypeRoom.remove(i);
                        CostRoom.remove(i);
                        Stt.remove(i);
                        System.out.println("Đã xoá thành công");
                        break;
                    }else{
                        // System.out.println(false);
                        errRoom++;
                    }
                }
                if(errRoom < NumberRoom.size()){
                    try(Writer writer = new FileWriter(linkRoom,false)){
                        writer.write(NumberRoom.get(0) + "," + TypeRoom.get(0) + "," + CostRoom.get(0) + "$," + Stt.get(0) + "\n");
                        for (int i = 1; i < NumberRoom.size(); i++) {
                            writer.write(NumberRoom.get(i) + "," + TypeRoom.get(i) + "," + CostRoom.get(i) + "$," + Stt.get(i));
                        }
                    }catch(IOException e){
                        System.out.println("Error: " + e.getMessage());
                    }
                }else{
                    System.out.println("Không tìm thấy phòng nào để xoá!");
                }
            }else{
                System.out.println("Không có phòng nào để xoá!");
            }
        }else{
            System.out.println("Không tìm thấy tệp dữ liệu phòng!");
        }
    }
    public void searchTypeRoom(){
        int err = 0;
        System.out.println("Nhập loại phòng bạn muốn tìm kiếm: ");
        String Type = scanner.next();
                    Type = Type.toLowerCase();
                    for(int i=0;i<TypeRoom.size();i++){
                        if(TypeRoom.get(i).equals(Type)){
                            System.out.println("Số phòng: " + NumberRoom.get(i) + "\t Loại phòng: " + TypeRoom.get(i) + "\t Giá phòng: " + CostRoom.get(i) + "\t Trạng thái phòng: " + Stt.get(i));
                        }else{
                            err++;
                        }
                    }
                    if(err == TypeRoom.size()){
                        System.out.println("Không tìm thấy phòng nào theo loại phòng đã nhập!");
            err = 0;
        }
    }
    public void searchCostRoom(){
        int err =0;
        System.out.println("Nhập giá phòng bạn muốn tìm kiếm: ");
        double costRooms = scanner.nextDouble();
        String cost = String.valueOf(costRooms + "$");
        for(int i=0;i<CostRoom.size();i++){
            if(CostRoom.get(i).equals(cost)){
                System.out.println("Số phòng: " + NumberRoom.get(i) + "\t Loại phòng: " + TypeRoom.get(i) + "\t Giá phòng: " + CostRoom.get(i) + "\t Trạng thái phòng: " + Stt.get(i));
            }else{
                err++;
            }
        }
        if(err == CostRoom.size()){
            System.out.println("Không tìm thấy phòng nào theo giá phòng đã nhập!");
            err = 0;
        }
    }
    public void searchStatusRoom(){
        int err = 0;
        System.out.println("Nhập trạng thái phòng bạn muốn tìm kiếm: ");
        String stt = scanner.next();
        String[] sttUper = stt.split("-");
        sttUper[0] = sttUper[0].substring(0, 1).toUpperCase() + sttUper[0].substring(1).toLowerCase();
        stt = String.join("-", sttUper);
        for(int i=0;i<Stt.size();i++){
            if(Stt.get(i).equals(stt)){
                System.out.println("Số phòng: " + NumberRoom.get(i) + "\t Loại phòng: " + TypeRoom.get(i) + "\t Giá phòng: " + CostRoom.get(i) + "\t Trạng thái phòng: " + Stt.get(i));
            }else{
                err++;
            }
        }
        if(err == Stt.size()){
            System.out.println("Không tìm thấy phòng nào theo trạng thái phòng đã nhập!");
            err = 0;
        }
    }
    public void SearchRoom() {
        if (Check_File() == true) {
            int N;
            do {
                System.out.println("Bạn muốn tìm phòng theo tiêu chí nào?");
                System.out.println("0. Thoát");
                System.out.println("1. Tìm phòng theo loại phòng");
                System.out.println("2. Tìm phòng theo giá phòng");
                System.out.println("3. Tìm phòng theo trạng thái phòng");
                N = scanner.nextInt();
                switch(N){
                    case 0:
                    System.out.println("Thoát chương trình");
                    break;
                    case 1:
                    searchTypeRoom();
                    break;
                    case 2:
                    searchCostRoom();
                    break;
                    case 3:
                    searchStatusRoom();
                    break;
                }
            } while (N!=0);
        }else{
            System.out.println("Không tìm thấy file");
        }
    }
}


public class Rooms {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n;
        Room room = new Room();
        // room.updateRoom();
        do {
            System.out.println("<============================>");
            System.out.println("Chương trình quản lý phòng khách sạn");
            System.out.println("0. Thoát");
            System.out.println("1. Thêm phòng");
            System.out.println("2. Hiển thị các phòng");
            System.out.println("3. Sửa thông tin phòng");
            System.out.println("4. Xoá phòng");
            System.out.println("5. Tìm kiếm phòng theo tiêu chí");
            System.out.println("Vui lòng chọn chương trình");
            n = scanner.nextInt(); // Yêu cầu người dùng nhập lại tránh lặp vô tận 1 chức năng
            switch (n) {
                case 0:
                    System.out.println("Thoát chương trình");
                    break;
                case 1:
                    room.addRooms();
                    break;
                case 2:
                    room.display();
                    break;
                case 3:
                    room.updateRoom();
                    break;
                case 4:
                    room.DeleteRoom();
                    break;
                case 5:
                    room.SearchRoom();
                    break;
                default:
                    System.out.println("Vui lòng chọn đúng chức năng");
                    break;
            }
        } while (n!= 0);
    }
}
