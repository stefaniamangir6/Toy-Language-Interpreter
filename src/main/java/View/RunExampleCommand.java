package View;

import Controller.Controller;

public class RunExampleCommand extends Command{
    private Controller ctr;

    public RunExampleCommand(String key, String desc,Controller ctr){
        super(key, desc);
        this.ctr=ctr;
    }
    @Override
    public void execute() throws Exception {
        ctr.allStep();
    }
}
