import java.util.*;

class TextEditor {
    private StringBuilder text; // To store the text
    private Stack<String> undoStack; // Stack for undo operations
    private Stack<String> redoStack; // Stack for redo operations
    private Queue<String> clipboardQueue; // Queue for clipboard management

    // Constructor
    public TextEditor() {
        text = new StringBuilder();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        clipboardQueue = new LinkedList<>();
    }

    // Method to insert text at a specified position
    public void insertText(int position, String newText) {
        if (position < 0 || position > text.length()) {
            System.out.println("Invalid position.");
            return;
        }
        saveStateForUndo();
        text.insert(position, newText);
        redoStack.clear();
    }

    // Method to delete text from a specified position
    public void deleteText(int position, int length) {
        if (position < 0 || position + length > text.length()) {
            System.out.println("Invalid position or length.");
            return;
        }
        saveStateForUndo();
        text.delete(position, position + length);
        redoStack.clear();
    }

    // Method to undo the last operation
    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        redoStack.push(text.toString());
        text = new StringBuilder(undoStack.pop());
    }

    // Method to redo the last undone operation
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }
        undoStack.push(text.toString());
        text = new StringBuilder(redoStack.pop());
    }

    // Method to copy text to the clipboard
    public void copy(int position, int length) {
        if (position < 0 || position + length > text.length()) {
            System.out.println("Invalid position or length.");
            return;
        }
        String copiedText = text.substring(position, position + length);
        clipboardQueue.offer(copiedText);
    }

    // Method to paste the last copied text at a specified position
    public void paste(int position) {
        if (clipboardQueue.isEmpty()) {
            System.out.println("Clipboard is empty.");
            return;
        }
        String pasteText = clipboardQueue.peek();
        insertText(position, pasteText);
    }

    // Save the current state for undo functionality
    private void saveStateForUndo() {
        undoStack.push(text.toString());
    }

    // Display the current state of the text editor
    public void display() {
        System.out.println("Text: " + text);
    }

    // Main method for testing
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();

        // Test cases
        editor.insertText(0, "Hello");
        editor.display(); // Expected: Hello

        editor.deleteText(0, 2);
        editor.display(); // Expected: llo

        editor.undo();
        editor.display(); // Expected: Hello

        editor.redo();
        editor.display(); // Expected: llo

        editor.copy(0, 2);
        editor.paste(3);
        editor.display(); // Expected: lloHe
    }
}
