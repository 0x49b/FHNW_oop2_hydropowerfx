package ch.fhnw.oop2.hydropowerfx.presentationmodel;

public class AddCommand implements Command {

    private final RootPM  rootPM;
    private final PowerStation added;
    private final int       position;

    public AddCommand(RootPM rootPM, PowerStation added, int position) {
        this.rootPM = rootPM;
        this.added = added;
        this.position = position;
    }

    @Override
    public void undo() {
        rootPM.removeFromList(added);
    }

    @Override
    public void redo() {
        rootPM.addToList(position, added);
    }
}
