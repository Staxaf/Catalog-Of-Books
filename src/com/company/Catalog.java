package com.company;

import org.w3c.dom.ls.LSOutput;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalTime;
import java.util.Scanner;

public class Catalog {
    static public ArrayList<Book> books = new ArrayList<>();// список книг в каталоге
    static public ArrayList<BookHistory> story = new ArrayList<>();// список истории выдач книг получателям
    static public ArrayList<Book> booksInStory = new ArrayList<>();// список книг в истории получателей(нужен для проверки книги при её возврате)
    /*void setStory(BookHistory story)
    {
        this.story.add(story);
    }
    void setBooks(Book book)
    {
        this.books.add(book);
    }*/
    public class BookHistory // внутренний класс с помощью объектов которого можно хранить историю выдач книг читателям
    {
        private String receiver;// ФИО получателя книги
        private String dateOfReceipt;// дата и время получения книги
        private Book book;// книга, которую получает получатель

        BookHistory()
        {
            //story.add(this)
            this.dateOfReceipt = new Date().toString();
            System.out.print("Введите свои ФИО: ");
            for(;;)
            {
                try{
                    Scanner scan = new Scanner(System.in);
                    receiver = scan.nextLine();
                    if(receiver.length() == 0)
                    {
                        throw new Exception("Введена пустая строка. Повторите попытку.");
                    }
                    break;
                }catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
            System.out.print("Введите название книги, которую хотите найти: ");
            book = Catalog.findBook(books);

        }
        BookHistory(String receiver, String dateOfReceipt, Book book)
        {
            //story.add(this);
            this.receiver = receiver;
            this.dateOfReceipt = dateOfReceipt;
            this.book = book;
        }
        String getReceiver()
        {
            return this.receiver;
        }
        public String toString()
        {
            return String.format("\n***********\nИмя получателя книги: %s\nДата и время получения книги: %s\n%s",this.getReceiver(),this.dateOfReceipt,this.book.toString());
        }
        Book getBook()
        {
            return this.book;
        }
        String printInFileStory()// метод вывода информации об объекте в файл
        {
            return String.format("%s\n%s\n%s",this.getReceiver(),this.dateOfReceipt,this.getBook().printInFileBooks());
        /*for(BookHistory i : story)
        {
            fw.println(i.getReceiver());
            fw.println(i.dateOfReceipt);
            fw.println(i.getBook().printInFileBooks());
        }*/
        }
    }

    public class Book
    {
        private String title;// Название книги
        private String authors;// Авторы книги
        private int yearOfRelease;// Год выпуска книги(или её издания)
        private boolean flag;//есть ли книга в наличии true - есть, false - нет
        Book()
        {
            /*books.add(this);
            System.out.print("Введите название книги: ");
            setTitle();
            System.out.print("Введите автора(-ов) книги: ");
            setAuthors();
            System.out.print("Введите год выпуска книги: ");
            setYearOfRelease();*/
            this.flag = true;
        }
        Book(String title, String authors, int yearOfRelease)
        {

            this.title = title;
            this.authors = authors;
            this.yearOfRelease = yearOfRelease;
            this.flag = true;
        }
        Book(String title, String authors, int yearOfRelease,boolean flag)// еще один конструктор для того, чтобы флаг считывался с файла
        {

            this.title = title;
            this.authors = authors;
            this.yearOfRelease = yearOfRelease;
            this.flag = flag;
        }
        String getTitle()
        {
            return  this.title;
        }
        void setTitle(String title)
        {
            this.title = title;
        }
        String getAuthors()
        {
            return this.authors;
        }
        void setAuthors(String authors)
        {
            this.authors = authors;
        }
        int getYearOfRelease()
        {
            return this.yearOfRelease;
        }
        void setYearOfRelease(int yearOfRelease)
        {
            this.yearOfRelease = yearOfRelease;
        }
        boolean getFlag()
        {
            return this.flag;
        }
        void setFlag(boolean flag)
        {
            this.flag = flag;
        }
        public String toString()// возвращает текущий объект в виде строки
        {
            return String.format("Нзвание книги: %s \nАвтор(-ы) книги: %s \nГод выпуска книги: %d\nПрисутствует ли? %b\n************" ,this.getTitle(),this.getAuthors(),this.getYearOfRelease(),this.getFlag());
        }
        String printInFileBooks()// метод вывода информации об объекте в файл
        {
            return String.format("%s\n%s\n%d\n%s",this.getTitle(),this.getAuthors(),this.getYearOfRelease(),this.getFlag());
        }
    }
    static void printAllCatalog()// вывод всех книг в каталоге
    {
        for(Book i : books)
        {
            System.out.println(i.toString());
        }
    }
    static void printAllStory()// вывод всей истории выдач книг
    {
        for(BookHistory i : story)
        {
            System.out.println(i.toString());
        }
    }

    static Book findBook(ArrayList<Book> books)// метод нахождения книги
    {

        Scanner scan = new Scanner(System.in);
        String keyString;
        for(;;) {
            for (;;) {
                try {

                    keyString = scan.nextLine();
                    if (keyString.length() == 0) { // keyString == ""
                        throw new Exception("Ошибка.Введена пустая строка.");
                    }
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            int count = 0;// количетсво книг, которые прошли проверку
            int place = 0;// место последней книги, которая прошла проверку в коллекции

            for (Book i : books) {
                if (i.getTitle().toUpperCase().contains(keyString.toUpperCase())) {
                    System.out.println(i.toString());
                    count++;
                    place = books.indexOf(i);

                }
            }
            if (count == 1) {
                System.out.println("Книга успешна получена.");
                booksInStory.add(books.get(place));// добавляется книга в список книг в истории получателей
                return books.get(place);
            } else if (count > 1) {
                if (keyString.toUpperCase().equals(books.get(place).getTitle().toUpperCase())) {
                    System.out.println("Книга успешна получена. Пожалуйста, верните её когда-нибудь");
                    booksInStory.add(books.get(place));// добавляется книга в список книг в истории получателей
                    return books.get(place);
                }
                System.out.println("Введите, пожалуйста, название конкретнее");

            } else if (count == 0) {
                System.out.println("Такой книги нет, повторите попытку.");

            }
        }
    }
    static Book returnBook(ArrayList<Book> books)// метод возврата книги
    {
        System.out.print("Введите полные ФИО на которые вы записывали книгу: ");
        Scanner scan = new Scanner(System.in);
        String tempFio;
        for(;;)
        {
            tempFio = scan.nextLine();
            int countBooks = 0;
            int placeBook = 0;

            for(BookHistory i : story)
            {
                if(tempFio.equals(i.receiver))
                {
                   if(!i.getBook().getFlag())
                   {
                       System.out.println(i.toString());
                       countBooks++;
                       placeBook = story.indexOf(i);
                   }
                }
            }
            if(countBooks == 0)
            {
                System.out.println("Такого получателя нет. Повторите попытку(Внимание: ввести нужно полные ФИО!)");
            }
            else if(countBooks == 1)
            {
                System.out.println("Книга успешна получена.");
                return story.get(placeBook).book;
            }
            else if(countBooks > 1)
            {
                System.out.print("Введите какую книгу вы хотите вернуть: ");
                return Catalog.findBook(booksInStory);// опять же вызывается метод поиска книги, только теперь в списке истории книг
            }
        }
    }
    void readFileInBooks(Scanner scanFile)// считывает инфомормацию с файла о книгах
    {
        String line = null;
        ArrayList<String> allBooks= new ArrayList<>();
        while((line = scanFile.nextLine()) != null)
        {
            allBooks.add(line);
            if(!scanFile.hasNextLine()) break;

        }
        for(int i = 0; i < allBooks.size(); i++)
        {
            if(i % (Book.class.getDeclaredFields().length - 1) == 0)
            {
                Book book = new Book();
                book.setTitle(allBooks.get(i));
                book.setAuthors(allBooks.get(i+1));
                book.setYearOfRelease(Integer.parseInt(allBooks.get(i+2)));
                book.setFlag(Boolean.parseBoolean(allBooks.get(i+3)));
                Catalog.books.add(book);
                //Catalog.books.add(new Book(all.get(i),all.get(i++),Integer.parseInt(all.get(i+2))));
            }
        }
        scanFile.close();
    }
    void readInFileStory(Scanner scanFile)// считывание с файла истории выдач книг
    {
        String line = null;
        ArrayList<String> allStory = new ArrayList<>();
        while((line = scanFile.nextLine()) != null)
        {
            allStory.add(line);
            if(!scanFile.hasNextLine()) break;
        }
        int countBook = Book.class.getDeclaredFields().length;
        for(int i = 0; i < allStory.size(); i++)
        {
            if(i % (countBook  + 1) == 0)//проходит условие каждые 6 раз, для того, чтобы удобно считать со списка
            {
                Book book = new Book(allStory.get(i+2),allStory.get(i+3), Integer.parseInt(allStory.get(i+4)),Boolean.parseBoolean(allStory.get(i+5)));
                BookHistory storyes = new BookHistory(allStory.get(i),allStory.get(i+1),book);
                story.add(storyes);
                booksInStory.add(book);
            }
        }
    }
}
