package View;

import Controller.Controller;
import Model.exp.ArithExp;
import Model.exp.IExp;
import Model.exp.ValExpr;
import Model.exp.VarExp;
import Model.stmt.*;
import Model.stmt.Files.CloseReadFileStatement;
import Model.stmt.Files.OpenRFileStmt;
import Model.stmt.Files.ReadFileStmt;
import Model.types.BoolType;
import Model.types.IntType;
import Model.types.StringType;
import Model.value.BoolValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;

import java.util.Scanner;


public class Main {

    static Repo myRepository = new Repo("/Users/mangirstefania/IdeaProjects/LabAssignmentInterpretor1/programs.txt");
    static Controller myController = new Controller(myRepository);

    public static void main(String[] args) throws Exception {

        // ex 1:  int v; v = 2; Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValExpr(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp(new ValExpr(new IntValue(2)), new ArithExp(new ValExpr(new IntValue(3)), new ValExpr(new IntValue(5)), "*"), "+")),  new CompStmt(
                        new AssignStmt("b",new ArithExp(new VarExp("a"), new ValExpr(new IntValue(1)), "+")),
                        new PrintStmt(new VarExp("b"))))));

        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValExpr(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValExpr(new IntValue(2))),
                        new AssignStmt("v", new ValExpr(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));
        //string varf; varf="test.in"; openFile(varf); int varc; readFile(varf,varc); print(varc);readFile(varf,varc); print(varc); closeFile(varf);
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf",new StringType()),
                new CompStmt(new AssignStmt("varf", new ValExpr(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc",new IntType()),
                                new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                        new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"),"varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFileStatement(new VarExp("varf"))))))
                        ))));
        //IStack<IStmt> s = new MyStack<IStmt>();
        //IList<IValue> l = new MyList<IValue>();
        //IDict<String, IValue> d = new MyDict<String, IValue>();
        //PrgState prg = new PrgState(s, d, l, ex3);
        //myController.addProgram(prg);
        //System.out.println(prg.toString());
        //myController.allStep();
        //System.out.println(fin2.toString());

        int choice;
        String choiceString;
        Scanner myInput = new Scanner(System.in);

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Execute ex1");
            System.out.println("2. Execute ex2");
            System.out.println("3. Execute ex3");

            choiceString = myInput.next();
            try {
                choice = Integer.parseInt(choiceString);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (choice == 0) {
                System.out.println("Done");
                break;
            }

            if (choice == 1) {

            }

            else if (choice == 2) {

            }
            else if(choice == 3 ){


                IExp varf;
                varf=new ValExpr(new StringValue("test.in"));
                IStmt st = new OpenRFileStmt(varf);
                String varc = null;
                IStmt st1 = new ReadFileStmt(varf, varc);
                System.out.println(varc);
                IStmt st2 = new ReadFileStmt(varf, varc);
                System.out.println(varc);
                IStmt st3 = new CloseReadFileStatement(varf);


            }
            else {
                System.out.println("Invalid choice");
            }
        }

        myInput.close();


    }
}
