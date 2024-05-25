import java.util.*;
import java.io.*;
import java.time.LocalDate;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Book implements Serializable{
    private String title;
    private String author;
    private boolean isAvailable;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author;
    }
}

class StudyBook extends Book implements Serializable {
    private String subject;

    public StudyBook(String title, String author, String subject) {
        super(title, author);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return super.toString() + ", Subject: " + subject;
    }
}

class Novel extends Book implements Serializable {
    private String genre;

    public Novel(String title, String author, String genre) {
        super(title, author);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return super.toString() + ", Genre: " + genre;
    }
}

class Person implements Serializable{
     protected String name;

     Person(){}

     Person(String n){
        name=n;
     }

     public String getName(){
        return name;
     }

     public void setName(String n){
        name=n;
     }
}

class Borrower extends Person implements Serializable {
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public Borrower(){}

    public Borrower(String name) {
        super(name);
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void borrowBook(Book book) {
        book.setAvailable(false);
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        book.setAvailable(true);
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "Borrower Name: " + name;
    }
}

class Manager extends Person  implements Serializable{

    private String password;
    private ArrayList<StudyBook> studyBooksRecord=new ArrayList<>();
    private ArrayList<Novel> novelsRecord=new ArrayList<>();
    private ArrayList<Borrower> borrowersRecord=new ArrayList<>();

    Manager(){}

    Manager(String n, String password){
        super(n);
        this.password=password;
        studyBooksRecord=new ArrayList<>();
        novelsRecord=new ArrayList<>();
        borrowersRecord=new ArrayList<>();
        
    }

    public void setManagerAttributes(String n, String p){
        name=n;
        password=p;
    }

    public void addStudyBook(StudyBook book) {
        studyBooksRecord.add(book);
    }

    public void removeStudyBook(StudyBook book) {
        studyBooksRecord.remove(book);
    }
    
    public void addNovel(Novel novel) {
        novelsRecord.add(novel);
    }
    
    public void removeNovel(Novel novel) {
        novelsRecord.remove(novel);
     }
    
    public void addBorrower(Borrower borrower) {
        borrowersRecord.add(borrower);
    }
    
    public void removeBorrower(Borrower borrower) {
        borrowersRecord.remove(borrower);
    }    
    
    public void borrowBook(Borrower borrower, Book book, int days) {
        borrower.borrowBook(book);
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = borrowDate.plusDays(days); 
        borrower.setBorrowDate(borrowDate);
        borrower.setReturnDate(returnDate);
    }

    public void returnBook(Borrower borrower, Book book) {
        borrower.returnBook(book);
    }
    
    public StudyBook findStudyBooks(String title, String author) {
        for (StudyBook studyBooks : studyBooksRecord) {
            if (studyBooks.getTitle().equalsIgnoreCase(title) && studyBooks.getAuthor().equalsIgnoreCase(author) && studyBooks.isAvailable()) {
                return studyBooks;
            }
        }
        return null;
    }
    
    public Novel findNovels(String title, String author) {
        for (Novel novels : novelsRecord) {
            if (novels.getTitle().equalsIgnoreCase(title) && novels.getAuthor().equalsIgnoreCase(author) && novels.isAvailable()) {
                return novels;
            }
        }
        return null;
    }

    public Book findBorrowedBook(Borrower borrower, String title, String author) {
        for (Book book : borrower.getBorrowedBooks()) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                return book;
            }
        }
        return null;
    }

    public ArrayList<StudyBook> getStudyBooks() {
        return studyBooksRecord;
    }
    
    public ArrayList<Novel> getNovels() {
        return novelsRecord;
    }

    public  void setStudyBooks(ArrayList<StudyBook> studybook) {
        studyBooksRecord=studybook;
    }

    public  void setNovel(ArrayList<Novel> novelbook) {
        novelsRecord=novelbook;
    }
    
    public Borrower getBorrower(String borrowerName) {
        for (Borrower borrower : borrowersRecord) {
            if (borrower.getName().equalsIgnoreCase(borrowerName)) {
                return borrower;
            }
        }
        return null;
    }
    
    public ArrayList<Borrower> getBorrowersList() {
        return borrowersRecord;
    }
}

public class AyeshaProject extends JFrame implements ActionListener  {
    private File file = new File(DATA_FILE);
   private Manager manager=new Manager();
   private Borrower borrower1 = new Borrower();
   private static final String DATA_FILE = "library_data.ser";
   private static final String MANAGER_PASSWORD = "admin123";
   private ArrayList<StudyBook> STUDYBOOKS= new ArrayList<>();
   private ArrayList<Novel> NOVELS = new ArrayList<>();
   private ArrayList<Borrower> BORROWERS = new ArrayList<>();
   private ListIterator<StudyBook> iterator1;
   private ListIterator<Novel> iterator2;
   private ListIterator<Borrower> BorrowerIterator;
   private JTextField titleField, authorField, typeField, borrowerNameField, nameField, daysField, passwordField;
   private JComboBox typeComboBox;

   Scanner input = new  Scanner (System.in);

    public AyeshaProject() {}
    
    private void saveLibraryData() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } 
        catch (IOException e) {
            System.out.println("Failed to create library data file: " + e.getMessage());
        }

       try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
           oos.writeObject(manager);
           oos.close();
           System.out.println("Library data saved successfully.");
       } 
       catch (IOException e) {
           System.out.println("Error saving library data.");
       }
   }

    private void writeToFile(Manager m){
        
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } 
        catch (IOException e) {
            System.out.println("Failed to create library data file: " + e.getMessage());
        }

        try {
            ObjectOutputStream objectOut = new ObjectOutputStream( new FileOutputStream(DATA_FILE));
            objectOut.writeObject(m);
            objectOut.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }  
    }

        ///////GUI part////////

    @Override
    public void actionPerformed(ActionEvent e){
        String a = e.getActionCommand();
        if (a.equals("Librarian")) {
            librarianLogin();
        } 
        else if (a.equals("Login")) {
            login();
        } 
        else if (a.equals("Add Book")) {
            AddBookForm();
        } 
        else if(a.equals("Add")){
            try {
                insertBook();
            } 
            catch (Exception b) {
                b.printStackTrace();
            }
        }
        else if (a.equals("Remove Book")) {
            RemoveBookForm();
        } 
        else if(a.equals("Remove")){
            try {
                deleteBook();
            } 
            catch (Exception b) {
                b.printStackTrace();
            }
        }
        else if (a.equals("View Books Record")) {
            ViewBooksRecord();
        } 
        else if (a.equals("View Borrowers Record")) {
            ViewBorrowersRecord();
        } 
        else if (a.equals("Borrower")) {
            BorrowerMenu();
        }
        else if (a.equals("Register as Borrower")) {
            RegisterBorrower();
        }  
        else if (a.equals("Register")) {
            register();
        } 
        else if (a.equals("View All Books")) {
            ViewBooksRecord();
        } 
        else if (a.equals("Borrow Book")) {
            BookBorrowForm();
        }  
        else if (a.equals("Borrow")) {
            borrow();
        }  
        else if (a.equals("Return Book")) {
            BookReturnForm();
        }  
        else if (a.equals(" Return")) {
            Return();
        }  
        else if (a.equals("Exit from System")) {
            saveLibraryData();
            System.exit(0);
        }
    }
    
    
    public void mainMethod() {

        JFrame mainFrame=new JFrame("Main Frame");
        mainFrame.setSize(400,200);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(Color.LIGHT_GRAY);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        JLabel label = new JLabel("Select one Option");
        Font labelFont= new Font("Arial", Font.ITALIC, 15);
        label.setFont(labelFont);

        mainFrame.add(label, BorderLayout.NORTH);
        JButton B1 = new JButton("Librarian");
        Font B1Font= new Font("Arial", Font.BOLD, 20);
        B1.setFont(B1Font);
        B1.setBackground(Color.LIGHT_GRAY);

        JButton B2 = new JButton("Borrower");
        Font B2Font= new Font("Arial", Font.BOLD, 20);
        B2.setFont(B2Font);
        B2.setBackground(Color.LIGHT_GRAY);

        JButton endButton = new JButton("Exit from System");
        Font endButtonFont= new Font("Arial", Font.ITALIC, 15);
        endButton.setFont(endButtonFont);
        endButton.setBackground(Color.LIGHT_GRAY);

        mainPanel.add(B1);
        mainPanel.add(B2);
        mainFrame.add(endButton, BorderLayout.SOUTH);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        B1.addActionListener(this);
        B2.addActionListener(this);
        endButton.addActionListener(this);

        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
    

    public void librarianLogin(){
        JFrame bookForm=new JFrame("Librarian Login");
        bookForm.setSize(400,200);
        bookForm.setLayout(new BorderLayout());
        bookForm.getContentPane().setBackground(Color.DARK_GRAY);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(new JLabel("Your Name :"));
        nameField = new JTextField();
        nameField.setBackground(Color.GRAY);
        mainPanel.add(nameField);
        mainPanel.setBackground(Color.LIGHT_GRAY);

        mainPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        passwordField.setBackground(Color.GRAY);
        mainPanel.add(passwordField);

        JButton submitButton = new JButton("Login");
        submitButton.addActionListener(this);

        bookForm.add(mainPanel, BorderLayout.CENTER);
        bookForm.add(submitButton, BorderLayout.SOUTH);
        bookForm.setVisible(true);
        bookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void login(){
      
        String password= passwordField.getText();
        String name = nameField.getText();

        if (!name.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Invalid name! Please enter alphabets only.", "Error", JOptionPane.ERROR_MESSAGE);
            nameField.setText("");
            return; 
        }

        if (!password.matches("[a-zA-Z0-9]+")) {
            JOptionPane.showMessageDialog(null, "Invalid password! Please enter alphanumeric characters only.", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            return; 
        }


        if (password.equals(MANAGER_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "---LOGIN SUCCESSFUL!---");
            LibrarianMenu();
        } 
        else {
            JOptionPane.showMessageDialog(this, "Incorrect Password  ! ---Redirecting to Main Menu---");
            mainMethod();
        }

         manager.setManagerAttributes(name,password);   
    }

    public void LibrarianMenu() {
        JFrame managerFrame=new JFrame("LIBRARIAN MENU");
        managerFrame.setBounds(3,4,400,200);
        managerFrame.setLayout(new BorderLayout());
        managerFrame.getContentPane().setBackground(Color.WHITE);

        JPanel managerPanel = new JPanel();
        managerPanel.setLayout(new GridLayout(4,1));
        JLabel label = new JLabel("What you want to do?");
        managerFrame.add(label, BorderLayout.NORTH);
        managerPanel.setBackground(Color.WHITE);

        JButton B1 = new JButton("Add Book");
        managerPanel.add(B1);
        B1.addActionListener(this);
        B1.setBackground(Color.LIGHT_GRAY);

        JButton B2 = new JButton("Remove Book");
        managerPanel.add(B2);
        B2.addActionListener(this);
        B2.setBackground(Color.LIGHT_GRAY);

        JButton B3 = new JButton("View Books Record");
        managerPanel.add(B3);
        B3.addActionListener(this);
        B3.setBackground(Color.LIGHT_GRAY);

        JButton B4 = new JButton("View Borrowers Record");
        managerPanel.add(B4);
        B4.addActionListener(this);
        B4.setBackground(Color.LIGHT_GRAY);

        JButton endButton = new JButton("Click cross icon to exit");
        managerPanel.add(endButton);
        endButton.addActionListener(this);
        endButton.setBackground(Color.LIGHT_GRAY);

        managerFrame.add(managerPanel, BorderLayout.CENTER);
        managerFrame.setVisible(true);
        managerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }   
    
    public void AddBookForm(){
        JFrame bookForm=new JFrame("Book Information Form");
        bookForm.setSize(400,400);
        bookForm.setLayout(new BorderLayout());
        bookForm.getContentPane().setBackground(Color.DARK_GRAY);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        titleField.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(titleField);
        mainPanel.setBackground(Color.GRAY);

        mainPanel.add(new JLabel("Book Author:"));
        authorField = new JTextField();
        authorField.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(authorField);

        mainPanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Study Book", "Novel"});
        typeComboBox.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(typeComboBox);

        JButton submitButton = new JButton("Add");
        submitButton.addActionListener(this);
        bookForm.add(submitButton, BorderLayout.SOUTH);

        bookForm.add(mainPanel, BorderLayout.CENTER);
        bookForm.setVisible(true);
        bookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
      
    public void RemoveBookForm(){
        JFrame bookForm=new JFrame("Book Information Form");
        bookForm.setSize(400,400);
        bookForm.setLayout(new BorderLayout());
        bookForm.getContentPane().setBackground(Color.BLUE);
        JPanel mainPanel = new JPanel(new GridLayout(3,1));
        mainPanel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        mainPanel.add(titleField);

        mainPanel.add(new JLabel("Book Author:"));
        authorField = new JTextField();
        mainPanel.add(authorField);

        mainPanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Study Book", "Novel"});
        mainPanel.add(typeComboBox);

        JButton submitButton = new JButton("Remove");
        submitButton.addActionListener(this);
        bookForm.add(submitButton, BorderLayout.SOUTH);

        bookForm.add(mainPanel, BorderLayout.CENTER);
        bookForm.setVisible(true);
        bookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void insertBook()throws Exception{
       

        String title = titleField.getText();
        String author = authorField.getText();
        String type = (String) typeComboBox.getSelectedItem();

            
        if (!author.matches("^[a-zA-Z ]+$")) {
            JOptionPane.showMessageDialog(this, "Invalid author! Please enter alphabets only.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }
  
        if (title.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Invalid title! Title should not consist solely of numerics.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        if (type.equalsIgnoreCase("Study Book")){
            StudyBook studyBook= new StudyBook(title, author,type);
            manager.addStudyBook(studyBook);
            writeToFile(manager);
        }
        else if (type.equalsIgnoreCase("Novel")){
            Novel novel = new Novel(title,author, type);
            manager.addNovel(novel);
            writeToFile(manager);
        }

        JOptionPane.showMessageDialog(this, "Book added successfully!");

        clearFields();
    }

    public void deleteBook() throws Exception{

        String title = titleField.getText();
        String author = authorField.getText();
        String type = (String) typeComboBox.getSelectedItem();


        if (!author.matches("^[a-zA-Z ]+$")) {
            JOptionPane.showMessageDialog(this, "Invalid author! Please enter alphabets only.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }
  
        if (title.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Invalid title! Title should not consist solely of numerics.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        StudyBook bookToRemove = null;
        Novel novelToRemove = null;
    
        
        if (type.equalsIgnoreCase("Study Book")){
            for (StudyBook book : manager.getStudyBooks()) {
                if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                    bookToRemove = book;
                    break;
                }
            }
            if (bookToRemove != null) {
                ArrayList<StudyBook> copy1=new ArrayList<>();
                copy1=manager.getStudyBooks();
                int i= copy1.indexOf(novelToRemove);
                StudyBook book=copy1.get(i+1);
                manager.removeStudyBook(book);
                writeToFile(manager);
                JOptionPane.showMessageDialog(this, "Study Book removed successfully!");

            } 
            else {
                JOptionPane.showMessageDialog(this, "Study Book not found !");
            }
        }
        else if (type.equalsIgnoreCase("Novel")){
            for (Novel novels : manager.getNovels()) {
                if (novels.getTitle().equalsIgnoreCase(title) && novels.getAuthor().equalsIgnoreCase(author)) {
                    novelToRemove = novels;
                    break;
                }
            }
            if (novelToRemove != null) {
                ArrayList<Novel> copy=new ArrayList<>();
                copy=manager.getNovels();
                int i= copy.indexOf(novelToRemove);
                Novel rrbook=copy.get(i+1);
                manager.removeNovel(rrbook);
                writeToFile(manager);
                JOptionPane.showMessageDialog(this, "Novel removed successfully!");
            }
            else {
                JOptionPane.showMessageDialog(this, "Novel not found !");
            }
        }

        clearFields();
    }

    public void ViewBooksRecord(){
        try {

            ReadStudyBooksRecord();
            ReadNovelsRecord();
   
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Books Data");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane);
    

        textArea.append("Title              Author              Genre/Subject \n");
        textArea.append("--------------------------------------------------------------\n");


        iterator1 = STUDYBOOKS.listIterator();
        while (iterator1.hasNext()) {
            StudyBook s1 = iterator1.next();
            textArea.append(s1.getTitle() + "    " + s1.getAuthor() + "    " + s1.getSubject() + "    " );
            textArea.append("\n"); // Add a blank line for separation
        }
        
        iterator2 = NOVELS.listIterator();
        while (iterator2.hasNext()) {
            Novel s1 = iterator2.next();
            textArea.append(s1.getTitle() + "    " + s1.getAuthor() + "    " + s1.getGenre() + "    " );
            textArea.append("\n"); 
        }

        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void ReadStudyBooksRecord() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            manager = (Manager) ois.readObject();
            ois.close();

            STUDYBOOKS.addAll(manager.getStudyBooks());
        
        } 
        catch (FileNotFoundException e) {
            System.out.println("Library data file not found. Starting with empty data.");
        } 
        catch (IOException e) {
            System.out.println("Error reading library data file. Starting with empty data.");
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Error loading library data. Starting with empty data.");
        }
    }

    private void ReadNovelsRecord() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            manager = (Manager) ois.readObject();
            ois.close();

            NOVELS.addAll(manager.getNovels());
        
        } 
        catch (FileNotFoundException e) {
            System.out.println("Library data file not found. Starting with empty data.");
        } 
        catch (IOException e) {
            System.out.println("Error reading library data file. Starting with empty data.");
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Error loading library data. Starting with empty data.");
        }
    }

    public void ViewBorrowersRecord(){
        try {

            ReadBorrowersRecord();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFrame frame = new JFrame("Borrowers Data");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
    
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane);
    
        BorrowerIterator = BORROWERS.listIterator();
        while (BorrowerIterator.hasNext()) {
            Borrower borrower =BorrowerIterator.next();
            textArea.append("NAME: " + borrower.getName() + "\n");
            textArea.append("BOOKS: \n");
            for (Book book : borrower.getBorrowedBooks()) {
                textArea.append("BOOK TITLE :"+ book.getTitle() + "\n");
                textArea.append("BOOK AUTHOR :"+ book.getAuthor() + "\n");
                textArea.append("BORROWED DATE: " + borrower.getBorrowDate() + "\n");
                textArea.append("RETURN DATE: " + borrower.getReturnDate() + "\n");
            }
            textArea.append("\n"); // Add a blank line for separation
        }      
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void ReadBorrowersRecord() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            manager = (Manager) ois.readObject();
            ois.close();

            BORROWERS.addAll(manager.getBorrowersList());
        
        } 
        catch (FileNotFoundException e) {
            System.out.println("Library data file not found. Starting with empty data.");
        } 
        catch (IOException e) {
            System.out.println("Error reading library data file. Starting with empty data.");
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Error loading library data. Starting with empty data.");
        }
    }

///////////BORROWER//////////////

    public void BorrowerMenu() {
        JFrame BorrowerFrame=new JFrame("Borrower Menu");
        BorrowerFrame.setSize(400,400);
        BorrowerFrame.setLayout(new BorderLayout());
        BorrowerFrame.getContentPane().setBackground(Color.WHITE);

        JPanel BorrowerPanel = new JPanel();
        BorrowerPanel.setLayout(new GridLayout(4,1));

        JLabel label = new JLabel("What you want to do?");
        BorrowerFrame.add(label, BorderLayout.NORTH);

        JButton B1 = new JButton("Register as Borrower");
        BorrowerPanel.add(B1);
        B1.addActionListener(this);

        JButton B2 = new JButton("Borrow Book");
        BorrowerPanel.add(B2);
        B2.addActionListener(this);

        JButton B3 = new JButton("Return Book");
        BorrowerPanel.add(B3);
        B3.addActionListener(this);

        JButton B4 = new JButton("View All Books");
        BorrowerPanel.add(B4);
        B4.addActionListener(this);

        JButton endButton = new JButton("Click cross icon to exit");
        BorrowerFrame.add(endButton, BorderLayout.SOUTH);
        endButton.addActionListener(this);

        BorrowerFrame.add(BorrowerPanel, BorderLayout.CENTER);
        BorrowerFrame.setVisible(true);

        BorrowerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void RegisterBorrower(){
        JFrame bookForm=new JFrame("Borrower Form");
        bookForm.setSize(400,400);
        bookForm.setLayout(new BorderLayout());
        bookForm.getContentPane().setBackground(Color.BLUE);

        JPanel mainPanel = new JPanel(new GridLayout(2,1));
        mainPanel.add(new JLabel("Your Name:"));
        borrowerNameField = new JTextField();
        mainPanel.add(borrowerNameField);

        JButton submitButton = new JButton("Register");
        submitButton.addActionListener(this);
        mainPanel.add(submitButton);

        bookForm.add(mainPanel, BorderLayout.CENTER);
        bookForm.setVisible(true);
        bookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    public void register(){
        String n = borrowerNameField.getText();
        if (!n.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Invalid name! Please enter alphabets only.", "Error", JOptionPane.ERROR_MESSAGE);
            nameField.setText("");
            return; 
        }

        borrower1.setName(n);
        manager.addBorrower(borrower1);
        writeToFile(manager);
        JOptionPane.showMessageDialog(this, "Registered successfully!");

    }

    public void BookBorrowForm(){
        JFrame bookForm=new JFrame("Book Borrow");
        bookForm.setSize(400,400);
        bookForm.setLayout(new BorderLayout());
        bookForm.getContentPane().setBackground(Color.BLUE);

        JPanel mainPanel = new JPanel(new GridLayout(5,1));
        mainPanel.add(new JLabel("Your Name:"));
        nameField = new JTextField();
        mainPanel.add(nameField);      

        mainPanel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        mainPanel.add(titleField);

        mainPanel.add(new JLabel("Book Author:"));
        authorField = new JTextField();
        mainPanel.add(authorField);

        mainPanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Study Book", "Novel"});
        mainPanel.add(typeComboBox);

        mainPanel.add(new JLabel("For how many days you want to Borrow the book "));
        daysField = new JTextField();
        mainPanel.add(daysField);

        JButton submitButton = new JButton("Borrow");
        submitButton.addActionListener(this);
        bookForm.add(submitButton, BorderLayout.SOUTH);

        bookForm.add(mainPanel, BorderLayout.CENTER);
        bookForm.setVisible(true);
        bookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void borrow(){
        String n = nameField.getText();

        if (!n.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Invalid name! Please enter alphabets only.", "Error", JOptionPane.ERROR_MESSAGE);
            nameField.setText("");
            return; 
        }
        borrower1 = manager.getBorrower(n);

        if (borrower1 != null) {  
            String title = titleField.getText();
            String author = authorField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            int days = Integer.parseInt(daysField.getText());

            if(type.equals("Study Book")){
                StudyBook studyBook = manager.findStudyBooks(title, author);
                if (studyBook != null) {
                    manager.borrowBook(borrower1, studyBook, days);
                    manager.addBorrower(borrower1);
                    writeToFile(manager);
                    JOptionPane.showMessageDialog(this, "Your BORROWED DATE "+ borrower1.getBorrowDate() +"Your RETURN DATE "+ borrower1.getReturnDate() );
                    JOptionPane.showMessageDialog(this, "------BOOK BORROWED SUCCESSFULLY----- ");
                
                } 
                else {
                    JOptionPane.showMessageDialog(this, "------BOOK NOT FOUND----- ");
                }
            } 
            else if(type.equals("Novel")){
                Novel novel = manager.findNovels(title, author);
                if (novel != null) {
                    manager.borrowBook(borrower1,novel, days);
                    manager.addBorrower(borrower1);
                    writeToFile(manager);
                    JOptionPane.showMessageDialog(this, "Your BORROWED DATE "+ borrower1.getBorrowDate() +"Your RETURN DATE "+ borrower1.getReturnDate() );
                    JOptionPane.showMessageDialog(this, "------BOOK BORROWED SUCCESSFULLY----- ");
                
                } 
                else {
                    JOptionPane.showMessageDialog(this, "------BOOK NOT FOUND----- ");
                }
          
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "------Borrower not found , PLease Register First---- ");
        }
    }

    public void BookReturnForm(){

        JFrame bookForm=new JFrame("Book Return");
        bookForm.setSize(400,400);
        bookForm.setLayout(new BorderLayout());
        bookForm.getContentPane().setBackground(Color.BLUE);
        JPanel mainPanel = new JPanel(new GridLayout(4,1));
        mainPanel.add(new JLabel("Your Name:"));
        nameField = new JTextField();
        mainPanel.add(nameField);      

        mainPanel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        mainPanel.add(titleField);

        mainPanel.add(new JLabel("Book Author:"));
        authorField = new JTextField();
        mainPanel.add(authorField);

        mainPanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Study Book", "Novel"});
        mainPanel.add(typeComboBox);

        JButton submitButton = new JButton(" Return");
        submitButton.addActionListener(this);
        bookForm.add(submitButton, BorderLayout.SOUTH);

        bookForm.add(mainPanel, BorderLayout.CENTER);
        bookForm.setVisible(true);
        bookForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    public void Return(){

        String n = nameField.getText();
        if (!n.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(null, "Invalid name! Please enter alphabets only.", "Error", JOptionPane.ERROR_MESSAGE);
            nameField.setText("");
            return; 
        }
        borrower1 = manager.getBorrower(n); 
    
        if (borrower1 != null) {
            String title = titleField.getText();
            String author = authorField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            Book book = manager.findBorrowedBook(borrower1, title, author);
            if (book != null) {
                manager.returnBook(borrower1, book);
                writeToFile(manager);
                JOptionPane.showMessageDialog(this, "------BOOK RETURNED SUCCESSFULLY----- ");
                
            } 
            else {
                JOptionPane.showMessageDialog(this, "------BOOK NOT FOUND or NOT BORROWED BY BORROWER----- ");
            
            }
        } 
        else{
            JOptionPane.showMessageDialog(this, "------Borrower not found , PLease Register First---- ");
        }
    }
  
    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        typeComboBox.setSelectedIndex(0);
    }
    
    

    public static void main(String[] args) {
        AyeshaProject libraryManagementSystem = new AyeshaProject();
        libraryManagementSystem.mainMethod();
    }

}
    
