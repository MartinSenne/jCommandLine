package de.martin_senne.jcommandline;

import de.martin_senne.jcommandline.option.IOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Class TreeIterator.
 *
 * @author  Martin Senne
 */
public class TreeIterator implements Iterator<IOption> {

    List<IOption> currentOfLevels;
    List<Integer> childPosForLevels;
    int currentLevel;
    IOption current;
    boolean onlyValids;

    public TreeIterator(IOption start) {
        this(start, false);
    }

    public TreeIterator(IOption start, boolean onlyValids) {
        currentOfLevels = new ArrayList<IOption>();
        childPosForLevels = new ArrayList<Integer>();

        currentLevel = 0;
        current = start;

        this.onlyValids = onlyValids;
    }

    public boolean hasNext() {
        return (currentLevel != -1);
    }

    public IOption next() {

        if (hasNext()) {

            // save next
            IOption next = current;
            advance(); // modifies current

            return next;
        } else {
            throw new NoSuchElementException("Requesting non existent element.");
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void advance() {

        if (current.getChildren().isEmpty()) {
            boolean foundNext = false;

            while (!foundNext) {

                currentLevel--;

                // check for global finish
                if (currentLevel == -1) {
                    return;
                }

                int idx = childPosForLevels.get(currentLevel);
                current = currentOfLevels.get(currentLevel);

                int maxChildren;

                if (onlyValids) {
                    maxChildren = current.getValidChildren().size();
                } else {
                    maxChildren = current.getChildren().size();
                }

                if (idx < (maxChildren - 1)) { // either to next sibling of current
                    idx++;
                    childPosForLevels.set(currentLevel, idx);
                    currentLevel++;

                    if (onlyValids) {
                        current = current.getValidChildren().get(idx);
                    } else {
                        current = current.getChildren().get(idx);
                    }

                    foundNext = true; // and out ====>
                } else { // or upward with next iteration

                    // delete entries of this level beforehand
                    childPosForLevels.remove(currentLevel);
                    currentOfLevels.remove(currentLevel);
                }
            } // end while

        } else {

            // or downward
            int childIdx = 0;
            childPosForLevels.add(currentLevel, childIdx); // first child in next level
            currentOfLevels.add(currentLevel, current);
            currentLevel++;

            if (onlyValids) {
                current = current.getValidChildren().get(childIdx);
            } else {
                current = current.getChildren().get(childIdx);
            }
        } // end if-else
    }
}
