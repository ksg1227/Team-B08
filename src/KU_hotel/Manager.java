package KU_hotel;

import java.util.ArrayList;
import java.util.Scanner;

import static KU_hotel.Main.date;

public class Manager {

    ArrayList<RoomInfo> rooms = new ArrayList<RoomInfo>();
    RoomInfo manager = new RoomInfo();
    Scanner sc = new Scanner(System.in);
    //////
    User m_user = new User();
    ArrayList<User> users = new ArrayList<User>();
    /////

    public Manager() {
        manager.fromCsv();
        for (int i = 0; i < manager.rooms.size(); i++) {
            rooms.add(manager.rooms.get(i));
        }
        for (int i = 0; i < m_user.users.size(); i++) {
            users.add(m_user.users.get(i));
        }


        while (true) {
            System.out.println("<관리자 메뉴>");
            System.out.println("---------------");
            System.out.println("1) 예약 신청 확인");
            System.out.println("2) 예약 현황 확인");
            System.out.println("3) 로그아웃");
            System.out.println("---------------");
            try {
                System.out.println("메뉴 번호를 입력하세요.");
                System.out.print(">>");
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1:
                        manage_Reservation();
                        break;
                    case 2:
                        check_manage_Reservation();
                        break;
                    case 3:
                        System.out.println("메뉴로 돌아갑니다.");
                        return;
                    default:
                        System.out.println("1~3 사이 숫자를 입력하세요");
                }
            } catch (NumberFormatException E) {
                System.out.println("올바른 형식으로 입력하세요!");
            }
        }
    }

    public void manage_Reservation() {
        ArrayList<RoomInfo> temprooms = new ArrayList<RoomInfo>(); //승인 처리 안된 방
        System.out.println("<예약 신청 확인>");
        System.out.println("---------------");
        System.out.println("예약 승인 대기 중인 손님 리스트");
        int count = 1;
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getisAccept() == false) {
                if (!rooms.get(i).getuserName().equals("X")) {

                    System.out.println(count + ") " + rooms.get(i).getuserName() +
                            "/" + rooms.get(i).getcheckIn() + " ~ " + rooms.get(i).getcheckOut()
                            + "/" + rooms.get(i).getroomNum() + "호실" + "/" + rooms.get(i).getpersonNum() + "인");
                    temprooms.add(rooms.get(i));
                    count++;
                }
            }
        }
        System.out.println("---------------");
        if (temprooms.size() == 0) {
            System.out.println("현재 예약 내역이 존재하지 않습니다");
            return;
        }
        while (true) {
            try {
                System.out.println("처리하고자 하는 예약 내역을 입력하세요.");
                System.out.print(">> ");
                int i = Integer.parseInt(sc.nextLine().trim());

                edit_roomInfo(temprooms.get(i - 1).getroomNum());
                return;
            } catch (NumberFormatException e) {
                System.out.println("올바른 형식으로 입력하세요.");
            }

        }
    }

    public void edit_roomInfo(int roomNum) {
        System.out.println("---------------");
        System.out.println("1) 승인");
        System.out.println("2) 취소");
        System.out.println("3) 돌아가기");
        System.out.println("---------------");
        while (true) {
            try {
                System.out.println("메뉴 번호를 입력하세요.");
                System.out.print(">>");
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1:
                        approve_Reservation(roomNum - 1);
                        return;
                    case 2:
                        cancel_Reservation(roomNum - 1);
                        return;
                    case 3:
                        System.out.println("메뉴로 돌아갑니다.");
                        return;
                    default:
                        System.out.println("1~3 사이 숫자를 입력하세요");
                }
            } catch (NumberFormatException E) {
                System.out.println("올바른 형식으로 입력하세요!");
            }
        }
    }

    private void approve_Reservation(int roomidx) {
        while (true) {
            System.out.println("예약을 승인하겠습니까? (Y/N)");
            System.out.print(">> ");
            String c = sc.nextLine().trim();
            if (c.equals("Y")) {
                manager.rooms.get(roomidx).setisAccept(true);
                manager.toCsv();
                System.out.println("예약이 승인되었습니다.");
                return;
            } else if (c.equals("X")) {
                System.out.println("예약이 보류되었습니다.");
                return;
            } else {
                System.out.println("올바른 형식으로 입력해주세요.");
            }

        }

    }
////
    private void cancel_Reservation(int roomidx) {
        while (true) {
            System.out.println("예약을 취소하겠습니까? (Y/N)");
            System.out.print(">> ");
            String c = sc.nextLine().trim();
            if (c.equals("Y")) {
                int count =0;
                for(int i=0; i<rooms.size(); i++)
                    if(rooms.get(roomidx).getroomNum() == rooms.get(i).getroomNum()) //해당 호실 예약내역이 몇개 있는지 세어줌
                        count++;
                if(count >= 2){ // 취소하려는 호실이 csv파일에 또다른 예약이 존재할때
                    manager.rooms.remove(roomidx);

                }else{
                    manager.rooms.get(roomidx).setcheckIn("X");
                    manager.rooms.get(roomidx).setcheckOut("X");
                    manager.rooms.get(roomidx).setpersonNum(0);
                    manager.rooms.get(roomidx).setPhoneNum("X");
                    manager.rooms.get(roomidx).setuserName("X");
                    manager.rooms.get(roomidx).setisAccept(false);

                }
                manager.toCsv();
                return;
            } else if (c.equals("N")) {
                System.out.println("예약이 보류되었습니다.");
                return;
            } else {
                System.out.println("올바른 형식으로 입력해주세요.");
            }

        }
    }


    public void check_manage_Reservation() {

        System.out.println("<예약 현황 리스트>");
        System.out.println("----------------");
        int count = 1;
        for (int i = 0; i < rooms.size(); i++) {
            if (!rooms.get(i).getuserName().equals("X")) {
                if (!rooms.get(i).getuserName().equals("X")) {
                    String temp = rooms.get(i).getuserName() + "/" + rooms.get(i).getPhoneNum() + "/" + rooms.get(i).getcheckIn() + " ~ "
                            + rooms.get(i).getcheckOut() + "/" + rooms.get(i).getroomNum() + "호/" + rooms.get(i).getpersonNum() + "인/";
                    if (rooms.get(i).getisAccept()) {
                        temp += "승인 완료";
                    } else {
                        temp += "승인 대기";
                    }
                    System.out.println(count + ") " + temp);
                    count++;
                }
            }
        }
        System.out.println("----------------");
        System.out.println("노쇼 처리를 하고자 하는 회원이 존재합니까? (Y/N)");
        System.out.print(">> ");

        String input = sc.nextLine().trim();
        int count2 = 1;
        ArrayList<User> tempuser = new ArrayList<User>();

        if (input.equals("Y")) {
            System.out.println("<노쇼 처리가 가능한 예약 리스트>");
            for (int i = 0; i < rooms.size(); i++) {
                int num1 = Integer.parseInt(date);
                if (rooms.get(i).getcheckOut().equals("X")) {
                    continue;
                }
                int num2 = Integer.parseInt(rooms.get(i).getcheckOut());
                String temp2 = "";
                if (num1 > num2 && rooms.get(i).getisAccept()) {
                    boolean flag = false;
                    for(int m=0;m<users.size();m++){
                        if(rooms.get(i).getPhoneNum().equals(users.get(m).getPhoneNum())){
                            if(users.get(m).getNo_Show() == true){
                                flag = true;
                                break;
                            }
                        }
                    }
                    if(flag){
                        continue;
                    }
                    temp2 += rooms.get(i).getuserName() + "/" + rooms.get(i).getPhoneNum() + "/" + rooms.get(i).getcheckIn() + " ~ "
                            + rooms.get(i).getcheckOut() + "/" + rooms.get(i).getroomNum() + "호/" + rooms.get(i).getpersonNum() + "인/승인 완료";

                    for (int k = 0; k < users.size(); k++) {
                        if (users.get(k).getPhoneNum().equals(rooms.get(i).getPhoneNum())) {
                            tempuser.add(users.get(k));
                        }
                    }

                    System.out.println(count2 + ") " + temp2);
                    count2++;
                }


            }

            if(tempuser.size() == 0){
                System.out.println("\n노쇼 처리할 회원이 존재하지 않습니다.\n");
                System.out.println("아무 키를 누르면 호텔 로그인 메뉴로 돌아갑니다.");
                return;
            }
            System.out.println("———————");

            System.out.println("노쇼 처리할 회원의 번호를 입력해주세요.");
            System.out.print(">> ");
            String input2 = sc.nextLine().trim();
            int index = Integer.parseInt(input2) - 1;
            System.out.print("노쇼 처리하시겠습니까?(Y/N)\n>>");
            String input3 = sc.nextLine().trim();
            if (input3.equals("Y")) {
                tempuser.get(index).setNo_Show(true);
                String ban_date = "";
                for (int m = 0; m < rooms.size(); m++) {
                    if (tempuser.get(index).getPhoneNum().equals(rooms.get(m).getPhoneNum())) {
                        ban_date = String.valueOf(Integer.parseInt(rooms.get(m).getcheckOut()) + 7);
                    }
                }
                tempuser.get(index).setBan_Date(ban_date);
                tempuser.remove(tempuser.get(index));
                System.out.println("노쇼 처리가 완료되었습니다.");
                System.out.println();
                System.out.println("아무 키를 누르면 호텔 로그인 메뉴로 돌아갑니다.");
                m_user.toCsv();
                sc.nextLine();
            } else if (input3.equals("N")) {
                System.out.println("\n노쇼 처리를 취소합니다.\n");
                System.out.println("아무 키를 누르면 호텔 로그인 메뉴로 돌아갑니다.");
                sc.nextLine();
            }

        } else if (input.equals("N")) {
            System.out.println();
            System.out.println("노쇼 처리할 회원이 존재하지 않습니다.");
            System.out.println();
            System.out.println("아무 키를 누르면 호텔 로그인 메뉴로 돌아갑니다.\n");
            sc.nextLine();
        } else {
            System.out.println("올바른 형식으로 입력해주세요.");
        }
    }
}