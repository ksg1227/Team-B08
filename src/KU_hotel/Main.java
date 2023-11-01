package KU_hotel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    static String date="";


    public static void main(String[] args) {
        final String admin_id = "kuhotel_1234";
        final String admin_pwd = "kuhotel_5678";




        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("오늘의 날짜를 YYYYMMDD 형식으로 입력해주세요.(예: 20231031)");
            System.out.print(">>");
            date = scanner.nextLine();
            date = date.trim();
            if (date.length() != 8) {
                System.out.println("날짜는 YYYYMMDD 형식으로 입력해야합니다.");
                continue;
            }
            else if (!date.matches("\\d+")) { // date가 숫자가 아닐 시
                System.out.println("날짜는 YYYYMMDD 형식의 숫자로 입력해야합니다.");
                continue;
            }
            else {
                break;
            }
        }

        while (true) {
            User user = new User();
            System.out.println("<메인 메뉴>");
            System.out.println("---------------");
            System.out.println("1) 회원가입");
            System.out.println("2) 사용자 로그인");
            System.out.println("3) 호텔 로그인");
            System.out.println("4) 종료");
            System.out.println("---------------");
            System.out.println("메뉴 번호를 입력하세요.");
            System.out.print(">> ");

            try {
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        System.out.println("회원가입 창으로 이동합니다.");
                        user.register();
                        break;
                    case "2":
                        while(true) {
                            System.out.println("<사용자 로그인 메뉴>");
                            System.out.println("---------------");
                            System.out.println("1) 회원 로그인");
                            System.out.println("2) 비회원 로그인");
                            System.out.println("3) 메인 메뉴");
                            System.out.println("---------------");
                            System.out.println("메뉴 번호를 입력하세요.");
                            System.out.print(">> ");
                            try {
                                int c = Integer.parseInt(scanner.nextLine().trim());
                                boolean go_to_main = false;
                                switch (c) {
                                    case 1:
                                        System.out.println("<회원 로그인>");
                                        go_to_main = user.user_Login();

                                        break;
                                    case 2:
                                        System.out.println("<비회원 로그인>");
                                        go_to_main = user.non_user_Login();
                                        break;
                                    case 3:
                                        System.out.println("메인 메뉴로 돌아갑니다.");
                                        go_to_main = true;
                                        break;
                                    default:
                                        System.out.println("1~3 사이 숫자를 입력해주세요.");
                                }
                                if(go_to_main){
                                    break;
                                }
                            }catch(NumberFormatException e){
                                System.out.println("올바른 형식을 입력해주세요.");
                            }
                        }
                        break;
                    case "3":
                        int signal = 0;
                        while (true) {
                            System.out.println("관리자 아이디를 입력하세요.\n메인메뉴로 돌아가려면 'q'를 입력하세요.");
                            System.out.print(">> ");
                            String input_id = scanner.nextLine();
                            if (input_id.equals("q")) {
                                System.out.println("메인 메뉴로 이동합니다.");
                                break;
                            }
                            System.out.println("관리자 비밀번호를 입력하세요.\n메인메뉴로 돌아가려면 'q'를 입력하세요.");
                            System.out.print(">> ");
                            String input_pwd = scanner.nextLine();
                            if (input_pwd.equals("q")) {
                                System.out.println("메인 메뉴로 이동합니다.");
                                break;
                            }
                            if (input_id.equals(admin_id) && input_pwd.equals(admin_pwd)) {
                                signal = 1;
                                break;
                            } else {
                                System.out.println("관리자 아이디 또는 비밀번호가 일치하지 않습니다.");
                            }
                        }
                        if (signal == 0) {
                            break;
                        } else {
                            System.out.println("관리자 메뉴로 이동합니다.");
                            FileLog(date, "Admin",2);
//                            RoomInfo room = new RoomInfo();
                            break;
                        }
                    case "4":
                        String temp;
                        while (true) {
                            System.out.println("종료하시겠습니까? (Y/N)\n>> ");
                            String choose = scanner.nextLine();
                            temp = choose;
                            if (choose.equals("Y") || choose.equals("N")) {
                                break;
                            } else {
                                System.out.println("다시 입력해주세요. ");
                                continue;
                            }
                        }
                        if (temp.equals("Y")) {
                            System.out.println("프로그램을 종료합니다. ");
                            return;
                        } else {
                            System.out.println("메인메뉴로 돌아갑니다");
                            break;
                        }
                    default:
                        System.out.println("1 ~ 4 사이 숫자를 입력하세요. ");
                }
            } catch (NumberFormatException E) {
                System.out.println("올바른 형식으로 입력하세요 !");
            }
        }
    }
    public static void FileLog(String Date, String User, int motion){
        String motion1 = "";
        try {
            String logfilename ="src/KU_Hotel/KuhotelLog.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(logfilename, true));
            switch(motion){
                case 1:
                    motion1 = "예약 신청 승인";
                    writer.write(Date+","+User+","+motion1+"\n");
                    break;
                case 2:
                    motion1 = "사용자로그인";
                    writer.write(Date+","+User+","+motion1+"\n");
                    break;
                case 3:
                    motion1 = "호텔로그인";
                    writer.write(Date+","+User+","+motion1+"\n");
                    break;
                case 4:
                    motion1 = "회원로그인";
                    break;
                case 5:
                    motion1 = "비회원로그인";
                    break;
                case 6:
                    motion1 = "예약신청";
                    break;
                case 7:
                    motion1 = "예약확인";
                    break;
                case 8:
                    motion1 = "로그아웃";
                    break;
                case 9:
                    motion1 = "예약신청확인";
                    break;
                case 10:
                    motion1 = "예약현황확인";
                    break;
                case 11:
                    motion1 = "관리자 메뉴";
                    break;
                case 12:
                    motion1 = "예약 취소";
                    break;
                default:
                    motion1 = "NULL";
                    break;
            }

            writer.flush();
            writer.close();
        }catch (IOException e){
            System.out.println("Log 파일 쓰기 실패\n");
            System.exit(0);
        }
    }
}