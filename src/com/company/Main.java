package com.company;

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Catalog catalog = new Catalog();
        Scanner scan = new Scanner(System.in);
        Scanner scanFileBooks = new Scanner(new FileReader("AllBooks.txt"));// для чтения файла
        Scanner scanFileStory = new Scanner(new FileReader("StoryBooks.txt"));
        catalog.readFileInBooks(scanFileBooks);
        catalog.readInFileStory(scanFileStory);
        int keyMain = -1;
        do {
            System.out.println("1) Вывод всего каталога.");
            System.out.println("2) Вывести всю историю выдач книг читателям.");
            System.out.println("3) Взять книгу из каталога.");
            System.out.println("4) Отдать книгу.");
            System.out.println("0) Выход.");
            try {
                keyMain = Integer.parseInt(scan.next());
                switch (keyMain)
                {
                    case 1:
                        Catalog.printAllCatalog();
                        break;
                    case 2:
                        Catalog.printAllStory();
                        break;
                    case 3:
                        Catalog.story.add(catalog.new BookHistory());
                        Catalog.books.remove(Catalog.story.get(Catalog.story.size() - 1).getBook());//достаю таким образом последний объект который я положил в историю, чтобы удалить из списка книг
                        Catalog.booksInStory.get(Catalog.booksInStory.size() - 1).setFlag(false);// устанавливаю ложный флаг, так как книги нет уже в наличии
                        break;
                    case 4:
                        Catalog.books.add(Catalog.returnBook(Catalog.books));
                        Catalog.booksInStory.get(Catalog.booksInStory.indexOf(Catalog.books.get(Catalog.books.size() - 1))).setFlag(true);//устанавливаю правдивый флаг, так как книгу вернули
                        break;
                    case 0:
                        FileWriter fileBooks = new FileWriter("AllBooks.txt");// перезаписываю файлы, чтобы сохранились изменения
                        PrintWriter printBooks = new PrintWriter(fileBooks);
                        for(Catalog.Book i : Catalog.books)
                        {
                            printBooks.println(i.printInFileBooks());
                        }
                        FileWriter fileStory = new FileWriter("StoryBooks.txt");
                        PrintWriter printStory = new PrintWriter(fileStory);
                        for(Catalog.BookHistory i : Catalog.story)
                        {
                            printStory.println(i.printInFileStory());
                        }
                        printBooks.close();
                        printStory.close();
                        break;
                    default:
                        System.out.println("Введите корректные данные");
                }
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }while(keyMain != 0);
    }
}
