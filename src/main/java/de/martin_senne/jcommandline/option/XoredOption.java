package de.martin_senne.jcommandline.option;

import java.util.ArrayList;
import java.util.List;


/**
 * Class XoredOption.
 *
 * @author  Martin Senne
 */
public class XoredOption extends DefaultOption {

    protected List<IOption> validBranches;

    public XoredOption() {
        super("", false);
        validBranches = new ArrayList<IOption>();
        existentInCli = true;
    }

    @Override
    protected boolean checkChildren() {

        // no children, then everything is fine
        if (children.isEmpty()) {
            return true;
        }

        validBranches.clear();
        childrenErrorMessage = "No valid alternative.\n";

        boolean status = false;
        int alternative = 0;

        for (IOption childOption : children) {

            if (childOption.isValid()) {
                validBranches.add(childOption);
                status = true;
            } else {
                childrenErrorMessage += "  If using alternative " + (alternative + 1) + ": " + childOption.getErrorMessage() +
                    "\n";
            }

            alternative++;
        }

        if (validBranches.size() > 1) {
            childrenErrorMessage = "More then two alternatives applicable.";

            return false;
        }

        if (status) {
            childrenErrorMessage = "";
        }

        return status;
    }

    @Override
    public List<IOption> getValidChildren() {
        return validBranches;
    }
}
