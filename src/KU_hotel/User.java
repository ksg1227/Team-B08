package KU_hotel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class User
{
    private String name, id, PhoneNum, Password, ban_Date;
    final String admin_id = "kuhotel_1234";
    private boolean no_Show = false;
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<User> non_users = new ArrayList<User>();
    final String filename = "src/KU_hotel/UserInfo.csv";
    public User(String name, String id, String PhoneNum, String Password, boolean no_Show, String ban_Date) {
        this.name = name;
        this.id = id;
        this.PhoneNum = PhoneNum;
        this.Password = Password;
        this.no_Show = no_Show;
        this.ban_Date = ban_Date;
    }

    public String getBan_Date() {
        return ban_Date;
    }

    public void setBan_Date(String ban_Date) {
        this.ban_Date = ban_Date;
    }

    public boolean getNo_Show() {
        return no_Show;
    }
    public void setNo_Show(boolean no_Show) {
        this.no_Show = no_Show;
    }
    public String getName() {return name;}
    public String getId() {return id;}
    public String getPhoneNum() {return PhoneNum;}
    public String getPassword() {return Password;}

    public void setName(String name){this.name = name;}
    public void setId(String id){this.id = id;}
    public void setPhoneNum(String PhoneNum){this.PhoneNum = PhoneNum;}
    public void setPassword(String Password){this.Password = Password;}

    public User() {
        fromCsv();
    }
    public User register() {        //회원가입
        String name, id, PhoneNum, Password;
        Scanner sc = new Scanner(System.in);
        System.out.println("<회원 가입>");
        System.out.println("메인 메뉴로 돌아가려면 'q'를 누르세요.\n");
        while(true){                   //**********이름 등록**********//
            System.out.print("이 름 : ");
            name = sc.nextLine();
            name = name.trim();
            if(name.contentEquals("q")){
                return null;
            }
            if(name.length()<1 || name.length()>15){
                System.out.println("이름의 길이는 1이상 15이하여야 합니다!");
                continue;
            }
            if (!name.matches("^[가-힣]+$")) {
                if (name.matches(".*[a-zA-Z]+.*")) {
                    System.out.println("영어를 포함한 이름은 입력할 수 없습니다.");
                    continue;
                }
                else if(name.contains(" ")||name.contains("\t")) {
                    System.out.println("공백은 입력할 수 없습니다.");
                    continue;
                }
                else if(name.matches(".*\\d+.*")){
                    System.out.println("숫자는 입력할 수 없습니다.");
                    continue;
                }
                else{
                    System.out.println("올바른 형식을 입력하세요!");
                    continue;
                }
            }
            else{
                break;
            }

        }

        while(true) {                   //**********아이디 등록**********//
            System.out.print("아이디 : ");
            id = sc.nextLine();
            id = id.trim();
            if(id.contentEquals("q")){
                return null;
            }
            if (id.length() < 4 || id.length() > 10){
                System.out.println("4~10자 영문, 숫자를 사용하세요.");
                continue;
            }
            if (id.contains(" ")) {
                System.out.println("공백은 포함될 수 없습니다.");
                continue;
            }
            if(!id.matches("[a-zA-Z0-9]*")) {
                System.out.println("영어와 숫자만 사용해주세요.");
                continue;
            }
            if(id.equals(admin_id)){
                System.out.println("관리자 아이디는 사용할 수 없습니다.");
                continue;
            }

            break;
        }

        while(true) {                   //**********전화번호 등록**********//
            System.out.print("전화번호 : ");
            PhoneNum = sc.nextLine();
            PhoneNum = PhoneNum.trim();
            if(PhoneNum.contentEquals("q")){
                return null;
            }
            if(PhoneNum.length() < 1 || PhoneNum.length() > 15) {
                System.out.println("1~15자의 숫자만 사용하세요.");
                continue;
            }
            if(PhoneNum.contains(" ")){
                System.out.println("공백은 포함될 수 없습니다.");
                continue;
            }
            if(!PhoneNum.matches("[0-9]*")) {
                System.out.println("숫자만 사용해주세요.");
                continue;
            }

            break;
        }
        while(true) {                   //**********비밀번호 등록**********//
            System.out.print("비밀번호 : ");
            Password = sc.nextLine();
            Password = Password.trim();
            if(Password.contentEquals("q")){
                return null;
            }
            if(Password.length() < 8 || PhoneNum.length() > 16){
                System.out.println("8~16자의 영어, 숫자, 특수문자를 사용하세요.");
                continue;
            }
            if(Password.contains(" ")){
                System.out.println("공백은 포함될 수 없습니다.");
                continue;
            }
            if (!Password.matches("[a-zA-Z0-9!@#$%^&*()-_+=]*")) {
                System.out.println("영어와 숫자만 사용해주세요.");
                continue;
            }
            break;
        }
        if(!isUniqueID(id) && !isUniquePhoneNum(PhoneNum)){
            System.out.println("이미 등록된 전화번호, 아이디입니다.");
            System.out.println("아무 키를 누르면 메인 메뉴로 돌아갑니다.");
            sc.nextLine();
            return null;
        }
        if(!isUniqueID(id)) {
            System.out.println("이미 등록된 아이디입니다.");
            System.out.println("아무 키를 누르면 메인 메뉴로 돌아갑니다.");
            sc.nextLine();
            return null;
        }
        if(!isUniquePhoneNum(PhoneNum)) {
            System.out.println("이미 등록된 전화번호입니다.");
            System.out.println("아무 키를 누르면 메인 메뉴로 돌아갑니다.");
            sc.nextLine();
            return null;
        }
        User newuser = new User(name, id, PhoneNum, Password, false, "00000000");
        System.out.println("회원가입에 성공하였습니다.\n");
        Main.FileLog(Main.date, name, 1);
        users.add(newuser);
        toCsv();
        System.out.println("아무 키를 누르면 메인 메뉴로 이동합니다.");
        sc.nextLine();
        return newuser;
    }

    private boolean isUniquePhoneNum(String PhoneNum) {
        for(User user : users) {
            if(user.getPhoneNum().equals(PhoneNum)){
                return false;
            }
        }
        for(User nuser : non_users) {
            if(nuser.getPhoneNum().equals(PhoneNum)){
                return false;
            }
        }
        return true;
    }

    private boolean isUniqueID(String id) {
        for(User user : users) {
            if(user.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public boolean user_Login() { //main으로 돌아가야되면 true, 사용자 로그인 메뉴면 false return
        Scanner sc = new Scanner(System.in);
        String uid;
        String upwd;
        System.out.println("사용자 로그인 메뉴로 돌아가려면 'q'를 누르세요.\n");
        while(true) {
            System.out.print("아이디 : ");
            uid = sc.nextLine();
            uid = uid.trim();
            if(uid.equals("q")){
                return false;
            }
            System.out.print("비밀번호 : ");
            upwd = sc.nextLine();
            upwd = upwd.trim();
            if(upwd.equals("q")){
                return false;
            }
            for(User u : users) {       //회원 정보 일치 체크!
                if(u.getId().equals(uid))
                    if(u.getPassword().equals(upwd)){
                        System.out.println("로그인 성공!");
                        System.out.println("아무 키를 누르면 예약 메뉴로 돌아갑니다.");
                        Main.FileLog(Main.date, u.getName(), 2);
                        sc.nextLine();
                        RoomInfo r = new RoomInfo(u);
                        r.reservation_Menu();
                        return true;
                    }
            }
            System.out.println("아이디 또는 비밀번호가 일치하지 않습니다."); //로그인 실패!
        }
    }

    public boolean non_user_Login() {
        Scanner sc = new Scanner(System.in);
        String uname;
        String uphonenum;
        boolean login_success = false;
        while(!login_success) {
            System.out.println("사용자 로그인 메뉴로 돌아가려면 'q'를 누르세요.\n");
            while (true) {                   //**********이름 등록**********//
                System.out.print("이 름 : ");
                uname = sc.nextLine();
                uname = uname.trim();
                if (uname.contentEquals("q")) {
                    return false;
                }
                if (uname.length() < 1 || uname.length() > 15) {
                    System.out.println("이름의 길이는 1이상 15이하여야 합니다!");
                    continue;
                }
                if (!uname.matches("^[가-힣]+$")) {
                    if (uname.matches(".*[a-zA-Z]+.*")) {
                        System.out.println("영어를 포함한 이름은 입력할 수 없습니다.");
                        continue;
                    } else if (uname.contains(" ") || uname.contains("\t")) {
                        System.out.println("공백은 입력할 수 없습니다.");
                        continue;
                    } else if (uname.matches(".*\\d+.*")) {
                        System.out.println("숫자는 입력할 수 없습니다.");
                        continue;
                    } else {
                        System.out.println("올바른 형식을 입력하세요!");
                        continue;
                    }
                } else {
                    break;
                }

            }
            while (true) {                   //**********전화번호 등록**********//
                System.out.print("전화번호 : ");
                uphonenum = sc.nextLine();
                uphonenum = uphonenum.trim();
                if (uphonenum.contentEquals("q")) {
                    return false;
                }
                if (uphonenum.length() < 1 || uphonenum.length() > 15) {
                    System.out.println("1~15자의 숫자만 사용하세요.");
                    continue;
                }
                if (uphonenum.contains(" ")) {
                    System.out.println("공백은 포함될 수 없습니다.");
                    continue;
                }
                if (!uphonenum.matches("[0-9]*")) {
                    System.out.println("숫자만 사용해주세요.");
                    continue;
                }

                break;
            }

            if (!isUniquePhoneNum(uphonenum)) {
                System.out.println("이미 등록된 전화번호입니다.");
                System.out.println("아무 키를 누르면 메인 메뉴로 돌아갑니다.");
                sc.nextLine();
                return true;
            }
            while(true) {
                System.out.print("입력한 정보가 정확합니까? (Y/N)\n>> ");
                String c = sc.nextLine().trim();
                if (c.equals("Y")) {
                    User newnuser = new User(uname, "X", uphonenum, "X", false, "00000000");
                    System.out.println("비회원 로그인 성공!.\n");
                    Main.FileLog(Main.date, uname, 5);
                    non_users.add(newnuser);
                    toCsv();
                    System.out.println("아무 키를 누르면 예약 메뉴로 이동합니다.");
                    sc.nextLine();
                    RoomInfo r = new RoomInfo(newnuser);
                    r.reservation_Menu();
                    login_success = true;
                    return login_success;
                }
                else if (c.equals("N")) {
                    login_success = false;
                    break;
                }
                else{
                    System.out.println("올바른 형식을 입력해주세요");
                }
            }
        }

        return true;
    }

    public void toCsv() {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for(User u : users) {
                writer.write(u.getName()+","+u.getId()+","+u.getPhoneNum()+","+u.getPassword()+","+u.getNo_Show()+","+u.getBan_Date()+"\n");
            }
            for(User u : non_users) {
                writer.write(u.getName()+","+u.getId()+","+u.getPhoneNum()+","+u.getPassword()+","+u.getNo_Show()+","+u.getBan_Date()+"\n");
            }
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void fromCsv() {
        BufferedReader br;

        try(FileReader fileReader = new FileReader(filename)){
            br = Files.newBufferedReader(Paths.get(filename));
            String line = "";

            while((line = br.readLine()) != null){
                if(line.isEmpty()){
                    break;
                }
                String[] array = line.split(",");
                User user = new User(array[0], array[1], array[2], array[3], Boolean.parseBoolean(array[4]), array[5]);
                if(!array[1].equals("X")) {
                    users.add(user);
                }else{
                    non_users.add(user);
                }
            }
            fileReader.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("회원 정보 파일이 없습니다.\n프로그램을 종료합니다.");
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("회원 정보 파일이 없습니다.\n프로그램을 종료합니다.");
            System.exit(0);
        }
    }
}


