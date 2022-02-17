package com.example.exam_cyclic_barrier;

import Controller.Controller;
import Model.PrgState;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.stmt.Files.CloseReadFileStatement;
import Model.stmt.Files.OpenRFileStmt;
import Model.stmt.Files.ReadFileStmt;
import Model.stmt.IStmt;
import Model.types.*;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;
import Repo.Repo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProgramsWindowController implements Initializable {

    @FXML
    private Button execButton;

    @FXML
    private ListView<IStmt> selectItemsListView;

    private MainWindowController mainWindowController;

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @FXML
    private IStmt selectExample(ActionEvent actionEvent) {
        return selectItemsListView.getItems().get(selectItemsListView.getSelectionModel().getSelectedIndex());
    }

    private List<IStmt> initExamples(){
        List<IStmt> exList = new ArrayList<>();

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

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStatement("a", new VarExp("v")),
                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));


        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex6 =  new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStatement("a", new VarExp("v")),
                new CompStmt(new PrintStmt(new HeapReadingExpression(new VarExp("v"))), new PrintStmt(new ArithExp(
                        new HeapReadingExpression(new HeapReadingExpression(new VarExp("a"))),
                        new ValExpr(new IntValue(5)), "+")))))));


        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))),new CompStmt(
                new PrintStmt(new HeapReadingExpression(new VarExp("v"))), new CompStmt(
                new HeapWritingStatement("v", new ValExpr(new IntValue(30))),
                new PrintStmt(new ArithExp(
                        new HeapReadingExpression(new VarExp("v")),
                        new ValExpr(new IntValue(5)), "+"))))));


        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ValExpr(new IntValue(4))),new CompStmt(
                new WhileStmt(new RelationalExpr(new VarExp("v"),
                        new ValExpr(new IntValue(0)), ">"), new CompStmt(
                        new PrintStmt(new VarExp("v")),
                        new IncrementStmt("v", "-"))), new PrintStmt(new VarExp("v")))));


        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex9 =  new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new HeapAllocationStatement("v",new ValExpr(new IntValue(20))), new CompStmt(new VarDeclStmt("a",
                new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocationStatement("a", new VarExp("v")),
                new CompStmt(new HeapAllocationStatement("v",new ValExpr(new IntValue(30))),
                        new PrintStmt(new HeapReadingExpression(new HeapReadingExpression(new VarExp("a")))))))));


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

        exList.add(ex1);
        exList.add(ex2);
        exList.add(ex3);
        exList.add(ex4);
        exList.add(ex5);
        exList.add(ex6);
        exList.add(ex7);
        exList.add(ex8);
        exList.add(ex9);
        exList.add(ex10);
        exList.add(ex11);

        return exList;
    }
    private void displayExamples(){
        List<IStmt> examples = initExamples();
        selectItemsListView.setItems(FXCollections.observableArrayList(examples));
        // use an observable list to listen for changes
        selectItemsListView.getSelectionModel().select(0);
        execButton.setOnAction(actionEvent -> {
            int index = selectItemsListView.getSelectionModel().getSelectedIndex();
            IStmt selectedProgram = selectItemsListView.getItems().get(index);
            index++;

            IStack<IStmt> s1 = new MyStack<IStmt>();
            IList<IValue> l1 = new MyList<IValue>();
            IDict<String, IValue> d1 = new MyDict<String, IValue>();
            IDict<String, BufferedReader> f1 = new MyDict<String, BufferedReader>();
            IHeap<Integer, IValue> h1 = new MyHeap<Integer, IValue>();
            IDict<Integer, Pair<Integer, ArrayList<Integer>>> b1 = new MyLockTable<Integer, Pair<Integer,ArrayList<Integer>>>();

            PrgState programState = new PrgState(s1,d1,l1,f1,h1,b1,selectedProgram);
            Repo repository = new Repo("log" + index + ".txt");
            Controller controller = new Controller(repository);
            controller.addProgram(programState);
            try {
                selectedProgram.getTypeEnvironment(new MyDict<String, IType>());
                mainWindowController.setController(controller);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage());
                alert.show();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayExamples();
    }
}
