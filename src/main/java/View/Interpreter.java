package View;

import Controller.Controller;
import Model.PrgState;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.stmt.Files.CloseReadFileStatement;
import Model.stmt.Files.OpenRFileStmt;
import Model.stmt.Files.ReadFileStmt;
import Model.types.*;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Interpreter {

    public static void main(String[] args) throws Exception {

        // ex 1:  int v; v = 2; Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValExpr(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        Repo repo1 = new Repo("log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStack<IStmt> s1 = new MyStack<IStmt>();
        IList<IValue> l1 = new MyList<IValue>();
        IDict<String, IValue> d1 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f1 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h1 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b1 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment = new MyDict<String, IType>();
        ex1.getTypeEnvironment(typeEnvironment);
        PrgState prg1 = new PrgState(s1, d1, l1, f1, h1, b1, ex1);
        ctr1.addProgram(prg1);


        // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp(new ValExpr(new IntValue(2)), new ArithExp(new ValExpr(new IntValue(3)), new ValExpr(new IntValue(5)), "*"), "+")),  new CompStmt(
                        new AssignStmt("b",new ArithExp(new VarExp("a"), new ValExpr(new IntValue(1)), "+")),
                        new PrintStmt(new VarExp("b"))))));

        Repo repo2 = new Repo("log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStack<IStmt> s2 = new MyStack<IStmt>();
        IList<IValue> l2 = new MyList<IValue>();
        IDict<String, IValue> d2 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f2 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h2 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b2 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment2 = new MyDict<String, IType>();
        ex2.getTypeEnvironment(typeEnvironment2);

        PrgState prg2 = new PrgState(s2, d2, l2, f2, h2, b2, ex2);
        ctr2.addProgram(prg2);



        // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
                new IntType()),new CompStmt(new AssignStmt("a", new ValExpr(new BoolValue(true))),
                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValExpr(new IntValue(2))),
                        new AssignStmt("v", new ValExpr(new IntValue(3)))), new PrintStmt(new
                        VarExp("v"))))));
        Repo repo3 = new Repo("log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStack<IStmt> s3 = new MyStack<IStmt>();
        IList<IValue> l3 = new MyList<IValue>();
        IDict<String, IValue> d3 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f3 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h3 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b3 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment3 = new MyDict<String, IType>();
        ex3.getTypeEnvironment(typeEnvironment3);
        PrgState prg3 = new PrgState(s3, d3, l3, f3, h3, b3, ex3);
        ctr3.addProgram(prg3);



        //string varf; varf="test.in"; openFile(varf); int varc; readFile(varf,varc); print(varc);readFile(varf,varc); print(varc); closeFile(varf);
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf",new StringType()),
                new CompStmt(new AssignStmt("varf", new ValExpr(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc",new IntType()),
                                new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                        new CompStmt(new PrintStmt(new VarExp("varc")), new CompStmt(new ReadFileStmt(new VarExp("varf"),"varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFileStatement(new VarExp("varf"))))))
                        ))));
        Repo repo4 = new Repo("log4.txt");
        Controller ctr4 = new Controller(repo4);

        IStack<IStmt> s4 = new MyStack<IStmt>();
        IList<IValue> l4 = new MyList<IValue>();
        IDict<String, IValue> d4 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f4 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h4 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b4 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment4 = new MyDict<String, IType>();
        ex4.getTypeEnvironment(typeEnvironment4);
        PrgState prg4 = new PrgState(s4, d4, l4, f4, h4, b4, ex4);
        ctr4.addProgram(prg4);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStatement("a", new VarExp("v")),
                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

        Repo repo5 = new Repo("log5.txt");
        Controller ctr5 = new Controller(repo5);

        IStack<IStmt> s5 = new MyStack<IStmt>();
        IList<IValue> l5 = new MyList<IValue>();
        IDict<String, IValue> d5 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f5 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h5 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b5 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment5 = new MyDict<String, IType>();
        ex5.getTypeEnvironment(typeEnvironment5);
        PrgState prg5 = new PrgState(s5, d5, l5, f5, h5, b5, ex5);
        ctr5.addProgram(prg5);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex6 =  new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStatement("a", new VarExp("v")),
                new CompStmt(new PrintStmt(new HeapReadingExpression(new VarExp("v"))), new PrintStmt(new ArithExp(
                        new HeapReadingExpression(new HeapReadingExpression(new VarExp("a"))),
                        new ValExpr(new IntValue(5)), "+")))))));

        Repo repo6 = new Repo("log6.txt");
        Controller ctr6 = new Controller(repo6);

        IStack<IStmt> s6 = new MyStack<IStmt>();
        IList<IValue> l6 = new MyList<IValue>();
        IDict<String, IValue> d6 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f6 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h6 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b6 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment6 = new MyDict<String, IType>();
        ex6.getTypeEnvironment(typeEnvironment6);
        PrgState prg6 = new PrgState(s6, d6, l6, f6, h6, b6, ex6);
        ctr6.addProgram(prg6);

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))),new CompStmt(
                        new PrintStmt(new HeapReadingExpression(new VarExp("v"))), new CompStmt(
                                new HeapWritingStatement("v", new ValExpr(new IntValue(30))),
                new PrintStmt(new ArithExp(
                        new HeapReadingExpression(new VarExp("v")),
                        new ValExpr(new IntValue(5)), "+"))))));

        Repo repo7 = new Repo("log7.txt");
        Controller ctr7 = new Controller(repo7);

        IStack<IStmt> s7 = new MyStack<IStmt>();
        IList<IValue> l7 = new MyList<IValue>();
        IDict<String, IValue> d7 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f7 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h7 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b7 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment7 = new MyDict<String, IType>();
        ex7.getTypeEnvironment(typeEnvironment7);
        PrgState prg7 = new PrgState(s7, d7, l7, f7, h7, b7, ex7);
        ctr7.addProgram(prg7);

        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ValExpr(new IntValue(4))),new CompStmt(
                        new WhileStmt(new RelationalExpr(new VarExp("v"),
                                new ValExpr(new IntValue(0)), ">"), new CompStmt(
                                new PrintStmt(new VarExp("v")),
                                new IncrementStmt("v", "-"))), new PrintStmt(new VarExp("v")))));

        Repo repo8 = new Repo("log8.txt");
        Controller ctr8 = new Controller(repo8);

        IStack<IStmt> s8 = new MyStack<IStmt>();
        IList<IValue> l8 = new MyList<IValue>();
        IDict<String, IValue> d8 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f8 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h8 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b8 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment8 = new MyDict<String, IType>();
        ex8.getTypeEnvironment(typeEnvironment8);
        PrgState prg8 = new PrgState(s8, d8, l8, f8, h8, b8, ex8);
        ctr8.addProgram(prg8);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex9 =  new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStatement("a", new VarExp("v")),
                new CompStmt(new HeapAllocationStatement("v",new ValExpr(new IntValue(30))),
                        new PrintStmt(new HeapReadingExpression(new HeapReadingExpression(new VarExp("a")))))))));

        Repo repo9 = new Repo("log9.txt");
        Controller ctr9 = new Controller(repo9);

        IStack<IStmt> s9 = new MyStack<IStmt>();
        IList<IValue> l9 = new MyList<IValue>();
        IDict<String, IValue> d9 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f9 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h9 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b9 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment9 = new MyDict<String, IType>();
        ex9.getTypeEnvironment(typeEnvironment9);
        PrgState prg9 = new PrgState(s9, d9, l9, f9, h9, b9, ex9);
        ctr9.addProgram(prg9);


        // int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a)));
        //   print(v);print(rH(a))

        IStmt ex10 =  new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValExpr(new IntValue(10))), new CompStmt(
                        new HeapAllocationStatement("a", new ValExpr(new IntValue(22))), new CompStmt(
                new ForkStmt(new CompStmt(new HeapWritingStatement("a", new ValExpr(new IntValue(30))),
                        new CompStmt(new AssignStmt("v", new ValExpr(new IntValue(32))), new CompStmt(
                                new PrintStmt(new VarExp("v")), new PrintStmt(
                new HeapReadingExpression(new VarExp("a"))))))
                ), new CompStmt(new PrintStmt(new VarExp("v")),
                        new PrintStmt(new HeapReadingExpression(new VarExp("a")))))))));

        Repo repo10 = new Repo("log10.txt");
        Controller ctr10 = new Controller(repo10);

        IStack<IStmt> s10 = new MyStack<IStmt>();
        IList<IValue> l10 = new MyList<IValue>();
        IDict<String, IValue> d10 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f10 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h10 = new MyHeap<Integer, IValue>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b10 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        IDict<String, IType> typeEnvironment10 = new MyDict<String, IType>();
        ex10.getTypeEnvironment(typeEnvironment10);
        PrgState prg10 = new PrgState(s10, d10, l10, f10, h10, b10, ex10);
        ctr10.addProgram(prg10);



        //

       /* IStmt ex11 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v2",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())), new CompStmt(new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(new HeapAllocationStatement("v1", new ValExpr(new IntValue(2))), new CompStmt(
                                new HeapAllocationStatement("v2", new ValExpr(new IntValue(3))), new CompStmt(
                                new HeapAllocationStatement("v3", new ValExpr(new IntValue(4))), new CompStmt(
                                new CreateBarrierStmt("cnt", new HeapReadingExpression(new VarExp("v2"))),
                                new CompStmt(new ForkStmt(new CompStmt(new AwaitBarrierStmt("cnt"), new CompStmt(
                                        new HeapWritingStatement("v1", new ArithExp(new HeapReadingExpression(new VarExp("v1")), new ValExpr(new IntValue(10)), "*")),
                                        new PrintStmt(new HeapReadingExpression(new VarExp("v1")))
                                ))), new CompStmt(new ForkStmt(new CompStmt(new AwaitBarrierStmt("cnt"), new CompStmt(
                                        new HeapWritingStatement("v2", new ArithExp(new HeapReadingExpression(new VarExp("v2")), new ValExpr(new IntValue(10)), "*")),
                                                new CompStmt(new AwaitBarrierStmt("cnt"), new CompStmt(
                                                        new HeapWritingStatement("v2", new ArithExp(new HeapReadingExpression(new VarExp("v2")), new ValExpr(new IntValue(10)), "*")),
                                                                new PrintStmt(new HeapReadingExpression(new VarExp("v2")))))))), new CompStmt(
                                                                        new AwaitBarrierStmt("cnt"), new PrintStmt(new HeapReadingExpression(new VarExp("v3"))))
                                                )))
                        )
                        )
                        ))))); */

        //new(v1,2);new(v2,3);new(v3,4);
        // newBarrier(cnt,rH(v2));
        // fork(await(cnt);wh(v1,rh(v1)*10);print(rh(v1)));
        // fork(await(cnt);wh(v2,rh(v2)*10);wh(v2,rh(v2)*10);print(rh(v2)));
        // await(cnt);
        // print(rH(v3))

        IStmt ex11 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v2",new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())), new CompStmt(new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(new HeapAllocationStatement("v1", new ValExpr(new IntValue(2))), new CompStmt(
                                new HeapAllocationStatement("v2", new ValExpr(new IntValue(3))), new CompStmt(
                                new HeapAllocationStatement("v3", new ValExpr(new IntValue(4))), new CompStmt(
                                new CreateBarrierStmt("cnt", new HeapReadingExpression(new VarExp("v2"))),
                                new CompStmt(new ForkStmt(new CompStmt(new AwaitBarrierStmt("cnt"), new CompStmt(
                                        new HeapWritingStatement("v1", new ArithExp(new HeapReadingExpression(new VarExp("v1")), new ValExpr(new IntValue(10)), "*")),
                                        new PrintStmt(new HeapReadingExpression(new VarExp("v1")))
                                ))), new CompStmt(new ForkStmt(new CompStmt(new AwaitBarrierStmt("cnt"), new CompStmt(
                                        new HeapWritingStatement("v2", new ArithExp(new HeapReadingExpression(new VarExp("v2")), new ValExpr(new IntValue(10)), "*")),
                                        new CompStmt(new AwaitBarrierStmt("cnt"), new CompStmt(
                                                new HeapWritingStatement("v2", new ArithExp(new HeapReadingExpression(new VarExp("v2")), new ValExpr(new IntValue(10)), "*")),
                                                new PrintStmt(new HeapReadingExpression(new VarExp("v2")))))))), new CompStmt(
                                        new AwaitBarrierStmt("cnt"), new PrintStmt(new HeapReadingExpression(new VarExp("v3"))))
                                )))
                        )
                        )
                        )))));

        Repo repo11 = new Repo("log11.txt");
        Controller ctr11 = new Controller(repo11);

        IStack<IStmt> s11 = new MyStack<IStmt>();
        IList<IValue> l11 = new MyList<IValue>();
        IDict<String, IValue> d11 = new MyDict<String, IValue>();
        IDict<String, BufferedReader> f11 = new MyDict<String, BufferedReader>();
        IHeap<Integer, IValue> h11 = new MyHeap<Integer, IValue>();
        IDict<String, IType> typeEnvironment11 = new MyDict<String, IType>();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> b11 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();
        ex11.getTypeEnvironment(typeEnvironment11);
        PrgState prg11 = new PrgState(s11, d11, l11, f11, h11, b11, ex11);
        ctr11.addProgram(prg11);

        repo1.clearFile();
        repo2.clearFile();
        repo3.clearFile();
        repo4.clearFile();
        repo5.clearFile();
        repo6.clearFile();
        repo7.clearFile();
        repo8.clearFile();
        repo9.clearFile();
        repo10.clearFile();
        repo11.clearFile();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit\n"));
        menu.addCommand(new RunExampleCommand("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExampleCommand("2",ex2.toString(),ctr2));
        menu.addCommand(new RunExampleCommand("3",ex3.toString(),ctr3));
        menu.addCommand(new RunExampleCommand("4",ex4.toString(),ctr4));
        menu.addCommand(new RunExampleCommand("5",ex5.toString(),ctr5));
        menu.addCommand(new RunExampleCommand("6",ex6.toString(),ctr6));
        menu.addCommand(new RunExampleCommand("7",ex7.toString(),ctr7));
        menu.addCommand(new RunExampleCommand("8",ex8.toString(),ctr8));
        menu.addCommand(new RunExampleCommand("9",ex9.toString(),ctr9));
        menu.addCommand(new RunExampleCommand("10",ex10.toString(),ctr10));
        menu.addCommand(new RunExampleCommand("11",ex11.toString(),ctr11));
        menu.show();
    }

}
