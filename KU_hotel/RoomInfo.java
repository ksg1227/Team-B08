package KU_hotel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static KU_hotel.Main.date;

public class RoomInfo {
    private String checkIn, userName, checkOut, PhoneNum;
    int personNum, price, roomNum;
    private boolean isAccept;

    User user = new User();

    public RoomInfo() {
    }

    public RoomInfo(User user) {
        this.user = user;
    }

    public RoomInfo(int roomNum, String userName, String phoneNum, String checkIn, String checkOut, int personNum, int price, boolean isAccept) {
        this.checkIn = checkIn;
        this.userName = userName;
        this.checkOut = checkOut;
        this.PhoneNum = phoneNum;
        this.personNum = personNum;
        this.price = price;
        this.roomNum = roomNum;
        this.isAccept = isAccept;
    }

    ArrayList<RoomInfo> rooms = new ArrayList<RoomInfo>();
    final String filename = "src/KU_hotel/RoomInfo.csv";

    public String getcheckIn() {
        return checkIn;
    }

    public void setcheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getcheckOut() {
        return checkOut;
    }

    public void setcheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getpersonNum() {
        return personNum;
    }

    public void setpersonNum(int personNum) {
        this.personNum = personNum;
    }

    public int getprice() {
        return price;
    }

    public void setprice(int price) {
        this.price = price;
    }

    public int getroomNum() {
        return roomNum;
    }

    public void setroomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String PhoneNum) {
        this.PhoneNum = PhoneNum;
    }

    public boolean getisAccept() {
        return isAccept;
    }

    public void setisAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }



    public void reservation_Menu() {
        Scanner sc = new Scanner(System.in);

        while(true) {
            System.out.println("<예약 메뉴>");
            System.out.println("---------------");
            System.out.println("1) 예약 신청");
            System.out.println("2) 예약 확인");
            System.out.println("3) 로그아웃");
            System.out.println("---------------");
            try {
                System.out.println("메뉴 번호를 입력하세요.");
                System.out.print(">>");
                String choice = sc.nextLine().trim();
                switch (choice) {
                    case "1":
                        apply_Reservation();
                        break;
                    case "2":
                        check_Reservation();
                        break;
                    case "3":
                        System.out.println("메뉴로 돌아갑니다.");
                        return;
                    default:
                        System.out.println("1~4 사이 숫자를 입력하세요");
                }
            } catch (NumberFormatException E) {
                System.out.println("올바른 형식으로 입력하세요!");
            }
        }
    }


    public void apply_Reservation() {
        String checkIn, checkOut, choose;
        int roomNum=0, personNum;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("체크인 날짜를 입력해주세요.(YYYYMMDD)");
            System.out.print(">>");
            checkIn = sc.nextLine();
            checkIn = checkIn.trim();
            if(checkIn.equals('q')){
                return;
            }
            else if (checkIn.length() != 8) {
                System.out.println("날짜는 YYYYMMDD 형식으로 입력해야합니다.");
                continue;
            }
            else if (!checkIn.matches("\\d+")) { // date가 숫자가 아닐 시
                System.out.println("날짜는 YYYYMMDD 형식의 숫자로 입력해야합니다.");
                continue;
            }

            int num1 = Integer.parseInt(Main.date);
            int num2 = Integer.parseInt(checkIn);

            if (num1>num2) {
                System.out.println("이전 날짜는 예약할 수 없습니다.");
                System.out.println("오늘은 "+Main.date+"입니다.");
                continue;
            }
            else {
                break;
            }
        }

        while (true) {

            System.out.println("체크아웃 날짜를 입력해주세요.(YYYYMMDD)");
            System.out.print(">>");
            checkOut = sc.nextLine();
            checkOut = checkOut.trim();
            if(checkOut.equals('q')){
                return;
            }
            else if (checkOut.length() != 8) {
                System.out.println("날짜는 YYYYMMDD 형식으로 입력해야합니다.");
                continue;
            }
            else if (!checkOut.matches("\\d+")) { // date가 숫자가 아닐 시
                System.out.println("날짜는 YYYYMMDD 형식의 숫자로 입력해야합니다.");
                continue;
            }

            int num1 = Integer.parseInt(checkIn);
            int num2 = Integer.parseInt(checkOut);

            if (num1>num2) {
                System.out.println("체크인보다 이전 날짜는 체크아웃 날짜로 지정할 수 없습니다.");
                System.out.println("체크인은 "+checkIn+"입니다.");
                continue;
            }
            else {
                break;
            }
        }

        fromCsv();
        System.out.println("예약 가능 객실 리스트");
        ArrayList<RoomInfo> temproom = new ArrayList<RoomInfo>();
        for (RoomInfo room1 : rooms) {
            if(room1.userName.equals("null")) {
                System.out.println(room1.roomNum+"호 "+room1.price);
                temproom.add(room1);
            }
        }

        boolean invalid_room = true;
        while (invalid_room) {
            System.out.println("머물고자하는 객실을 선택해주세요.");
            System.out.print(">>");
            String input = sc.nextLine().trim();
            if(input.equals('q')){
                return;
            }
            if(!input.matches("\\d+")){
                System.out.println("숫자만 입력해주세요.");
                continue;
            }
            roomNum = Integer.parseInt(input);
            if(roomNum <= 0 || roomNum > rooms.size()){
                System.out.println("해당 객실은 존재하지 않습니다.");
                continue;
            }
            for(int j = 0; j<temproom.size(); j++){
                if(roomNum == temproom.get(j).roomNum){
                    invalid_room = false;
                    break;
                }
            }
            System.out.println("해당 객실은 현재 예약 불가능 상태입니다");

        }
        while (true) {
            System.out.println("인원을 선택해주세요.");
            System.out.print(">>");
            String input=sc.nextLine();
            if(input.equals('q')){
                return;
            }
            if(!input.matches("\\d+")){
                System.out.println("숫자만 입력해주세요.");
                continue;
            }
            personNum = Integer.parseInt(input);
            if(personNum <= 0 || personNum > 9){
                System.out.println("한 객실의 한도인원은 1~9인 입니다.");
                continue;
            }
            break;
        }
        while(true) {
            System.out.print("결제하시겠습니까? (Y/N)\n>>");
            choose = sc.nextLine();
            if (choose.equals("Y")) {
                break;
            }
            else if (choose.equals("N")||choose.equals('q')) {
                return;
            }
            else {
                System.out.println("다시 올바르게 입력해주세요.");
                continue;
            }
        }
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).roomNum == roomNum){
                rooms.get(i).checkIn = checkIn;
                rooms.get(i).checkOut = checkOut;
                rooms.get(i).personNum = personNum;
                rooms.get(i).PhoneNum = user.getPhoneNum();
                rooms.get(i).userName = user.getName();
                Main.FileLog(Main.date, user.getName(), 6);
            }
        }
        toCsv();
    }


    public void check_Reservation() {
        boolean reserve = false;
        for(int i=0;i<rooms.size();i++){
            if(rooms.get(i).PhoneNum == user.getPhoneNum()){
                reserve = true;
            }
        }
        if(!reserve){
            System.out.println("사용자님의 예약이 존재하지 않습니다.");
        }else {
            while (true) {
                System.out.println("<예약확인>");
                System.out.println("---------------");
                System.out.println("예약 내역 출력");
                for (int i=0;i<rooms.size();i++) {
                    if (rooms.get(i).PhoneNum == user.getPhoneNum()) {
                        System.out.println(rooms.get(i).checkIn + " ~ " + rooms.get(i).checkOut);
                        System.out.println(rooms.get(i).roomNum + "호 " + rooms.get(i).userName);
                        System.out.println("인원 : " + rooms.get(i).personNum + "명");
                        System.out.println("금액 : " + rooms.get(i).price);
                        Main.FileLog(Main.date, rooms.get(i).userName, 7);
                        System.out.println("---------------");
                    }
                }
                Scanner sc = new Scanner(System.in);
                System.out.println("1) 예약 취소");
                System.out.println("2) 돌아가기");
                System.out.println("---------------");
                try {
                    System.out.print(">>");
                    String choice = sc.nextLine().trim();
                    switch (choice) {
                        case "1":

                            System.out.print("취소하시겠습니까? (Y/N)\n>> ");
                            String choose = sc.nextLine();
                            if (choose.equals("Y")) {
                                System.out.println("성공적으로 취소되었습니다.");
                                for (int i = 0; i < rooms.size(); i++) {
                                    if (rooms.get(i).PhoneNum == user.getPhoneNum()) {
                                        Main.FileLog(Main.date, rooms.get(i).userName, 12);
                                        rooms.get(i).checkIn = null;
                                        rooms.get(i).checkOut = null;
                                        rooms.get(i).personNum = 0;
                                        rooms.get(i).PhoneNum = null;
                                        rooms.get(i).userName = null;
                                    }
                                }
                                toCsv();
                                return;
                            } else if (choose.equals("N")) {
                                System.out.println("예약을 유지하겠습니다.");
                                break;
                            } else {
                                System.out.println("올바르게 입력해주세요");
                            }

                        case "2":
                            System.out.print("예약 메뉴로 돌아가시겠습니까? (Y/N)\n>>");
                            choose = sc.nextLine();
                            if (choose.equals("Y")) {
                                return;
                            }
                            else if (choose.equals("N")) {
                                break;
                            } else {
                                System.out.println("올바르게 입력해주세요");
                            }
                        default:
                            System.out.println("1,2 숫자를 입력해주세요.");
                    }
                } catch (NumberFormatException E) {
                    System.out.println("올바른 형식으로 입력하세요!");
                }
            }
        }
    }

    public void toCsv() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (RoomInfo r : rooms) {
                writer.write(r.getroomNum() + "," + r.getuserName() + "," + r.getPhoneNum() + "," + r.getcheckIn() + "," + r.getcheckOut() + "," + r.getpersonNum() + "," + r.getprice() + "," + r.getisAccept()+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fromCsv() {
        BufferedReader br;

        try (FileReader fileReader = new FileReader(filename)) {
            br = Files.newBufferedReader(Paths.get(filename));
            String line = "";

            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                String[] array = line.split(",");

                RoomInfo room = new RoomInfo(Integer.parseInt(array[0]), array[1], array[2], array[3], array[4], Integer.parseInt(array[5]), Integer.parseInt(array[6]), Boolean.parseBoolean(array[7]));
                rooms.add(room);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "checkIn='" + checkIn + '\'' +
                ", userName='" + userName + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", PhoneNum='" + PhoneNum + '\'' +
                ", personNum=" + personNum +
                ", price=" + price +
                ", roomNum=" + roomNum +
                ", isAccept=" + isAccept +
                ", rooms=" + rooms +
                ", filename='" + filename + '\'' +
                '}';
    }
}
