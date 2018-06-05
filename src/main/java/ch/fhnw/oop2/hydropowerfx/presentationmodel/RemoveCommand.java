package ch.fhnw.oop2.hydropowerfx.presentationmodel;

public class RemoveCommand implements Command {
    private final RootPM  rootPM;
    private final PowerStation removed;
    private final int       position;

    public RemoveCommand(RootPM rootPM, PowerStation removed, int position) {
        this.rootPM = rootPM;
        this.removed = removed;
        this.position = position;
    }

    @Override
    public void undo() {
        rootPM.getPowerStationList().add(position, removed);
        rootPM.setActualPowerStation(removed);
    }

    @Override
    public void redo() {
        rootPM.removeFromList(removed);
    }
}
