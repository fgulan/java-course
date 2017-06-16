package hr.fer.zemris.java.custom.collections;

import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * ObjectMultistack is type of collection where stacks are stored in a map. Each
 * stack has its own unique name which is also a key for the stack in a map.
 * Also stack can hold any type of an object.
 * 
 * @author Filip Gulan
 * @version 1.0
 *
 */
public class ObjectMultistack {

    /** Map with stacks. */
    private Map<String, MultistackEntry> stack = new HashMap<>();

    /**
     * Adds a new value to the given stack. If stack with given name does not
     * exists, method creates one with given name.
     * 
     * @param name
     *            Stack name.
     * @param value
     *            Value to add.
     */
    public void push(String name, ValueWrapper value) {
        MultistackEntry entry;
        if (stack.containsKey(name)) {
            entry = new MultistackEntry(value, stack.get(name));
        } else {
            entry = new MultistackEntry(value, null);
        }

        stack.put(name, entry);
    }

    /**
     * Pops the value from the given stack.
     * 
     * @param name
     *            Stack name.
     * @return A value from the given stack.
     * @throws IllegalArgumentException
     *             If stack with given name does not exists.
     */
    public ValueWrapper pop(String name) {
        if (stack.containsKey(name)) {
            MultistackEntry entry = stack.get(name);
            ValueWrapper value = entry.getValue();

            if (entry.getNextEntry() != null) {
                stack.put(name, entry.getNextEntry());
            } else {
                stack.remove(name);
            }
            return value;
        } else {
            throw new IllegalArgumentException(
                    "There is no stack with given name!");
        }
    }

    /**
     * Returns the value from the given stack without deleting it.
     * 
     * @param name
     *            Stack name.
     * @return A value from the given stack.
     * @throws IllegalArgumentException
     *             If stack with given name does not exists.
     */
    public ValueWrapper peek(String name) {
        if (stack.containsKey(name)) {
            return stack.get(name).getValue();
        } else {
            throw new IllegalArgumentException(
                    "There is no stack with given name!");
        }
    }

    /**
     * Checks if stack with given name is empty.
     * 
     * @param name
     *            Stack name.
     * @return <code>true</code> if stack is empty, <code>false</code>
     *         othrewise.
     */
    public boolean isEmpty(String name) {
        return !stack.containsKey(name);
    }

    /**
     * MultistackEntry class represents stack object. It holds value and
     * reference to the next entry in stack.
     * 
     * @author Filip Gulan
     * @version 1.0
     *
     */
    static class MultistackEntry {
        /** Next element in stack. */
        private MultistackEntry nextEntry;
        /** Value. */
        private ValueWrapper value;

        /**
         * Constructor for MultistackEntry class. Creates entry with given value
         * and reference to the next entry in stack.
         * 
         * @param value
         *            New value.
         * @param nextEntry
         *            Reference to the next entry.
         */
        public MultistackEntry(ValueWrapper value, MultistackEntry nextEntry) {
            this.nextEntry = nextEntry;
            this.value = value;
        }

        /**
         * Returns next entry from stack.
         * 
         * @return Next entry.
         */
        public MultistackEntry getNextEntry() {
            return nextEntry;
        }

        /**
         * Returns value of current entry.
         * 
         * @return Value of current entry.
         */
        public ValueWrapper getValue() {
            return value;
        }

    }
}
