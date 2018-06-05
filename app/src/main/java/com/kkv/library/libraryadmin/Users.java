package com.kkv.library.libraryadmin;

public class Users {
    public String uname;
    public BorrowedBooks borrowedBooks;
    public Users(){

    }

    public Users(String uname,BorrowedBooks borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
        this.uname = uname;
    }

    public BorrowedBooks getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(BorrowedBooks borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

}

