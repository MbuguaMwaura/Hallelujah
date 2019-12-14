package com.example.hallelujah.models;

public class BibleVerse {



     String Book;
     String Chapter;
     String VerseFrom;
     String VerseTo;
     String Output;

    public BibleVerse(String book, String chapter, String verseFrom, String verseTo, String output) {
        this.Book = book;
        this.Chapter = chapter;
        this.VerseFrom = verseFrom;
        this.VerseTo = verseTo;
       this.Output = output;
    }

    public String getBook() {
        return Book;
    }

    public void setBook(String Book) {
        this.Book = Book;
    }

    public String getChapter() {
        return Chapter;
    }

    public void setChapter(String Chapter) {
        this.Chapter = Chapter;
    }

    public String getVerseFrom() {
        return VerseFrom;
    }

    public void setVerseFrom(String VerseFrom) {
        this.VerseFrom = VerseFrom;
    }

    public String getVerseTo() {
        return VerseTo;
    }

    public void setVerseTo(String VerseTo) {
        this.VerseTo = VerseTo;
    }

    public String getOutput() {
        return Output;
    }

    public void setOutput(String Output) {
        this.Output = Output;
    }
}
