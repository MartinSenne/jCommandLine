package de.martin_senne.jcommandline;

/**
 * Class AbstractCommand.
 *
 * @author  Martin Senne
 */
public abstract class AbstractCmd {
    private CommandLineParser clp;

    protected AbstractCmd(String[] args) {
        clp = new CommandLineParser(args);
    }

    protected abstract void setupParser();

    public abstract String getUsageString();

    protected CommandLineParser getCommandLineParser() {
        return clp;
    }

    protected void setCommandLineParser(CommandLineParser clp) {
        this.clp = clp;
    }

    protected void check() {
        boolean ok = clp.check();

        if (!ok) {
            parsingFailed();
        } else {
            run();
        }
    }

    protected abstract void run();

    protected void parsingFailed() {
        System.out.println(clp.getErrorMessage() + "\n\n" + getUsageString());
    }

    public static void go(AbstractCmd cmd) {
        cmd.setupParser();
        cmd.check();
    }
}
