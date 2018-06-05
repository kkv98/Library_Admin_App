package com.kkv.library.libraryadmin;


public class BorrowedBooks {
    public UserBooks  userBooks[];
    public BorrowedBooks(){

    }
    public BorrowedBooks(UserBooks[] userBooks) {
        this.userBooks = userBooks;
    }

    public UserBooks[] getUserBooks() {
        return userBooks;
    }

    public void setUserBooks(UserBooks[] userBooks) {
        this.userBooks = userBooks;
    }
}
