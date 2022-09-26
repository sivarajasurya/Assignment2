
import java.util.Scanner;

public class Assignment2 {
    public int menu ;
    static Scanner get = new Scanner(System.in);


    // 1. Enter Person Data
    // 2. Read Person Data
    // 3. Exit
    public void detail() throws Exception {


        System.out.println("Enter  1 for  Enter Person Data \n Enter 2 for  Read Person Data  \n Enter 3 for quit ");
        menu = get.nextInt();

        switch (menu) {

            //Enter Person Data
            case 1: {
                System.out.println("Enter name , Age , Sex");
                Person obj = new Person(get.next(), get.nextInt(), get.next().charAt(0));

                //save file name as "<name><age>.xml"
                System.out.println(obj.write(obj.getName() + obj.getAge() + ".xml"));
                detail();
                break;
            }


            //Read Person Data
            case 2: {

                System.out.println("Enter the xml file path");
                Person obj = new Person();

                System.out.println("Enter Name");
                String name = get.next();

                System.out.println("Enter Age");
                int age = get.nextInt();

                //Get file name as "<name><age>.xml"
                obj.personRead(name + age + ".xml");
                detail();

            }
            break;


            //Exit
            case 3: {
                get.close();
                System.exit(0);
            }


        }
    }


    public static void main(String[] args) throws Exception {


        Assignment2 main = new Assignment2();
        main.detail();

    }
}

